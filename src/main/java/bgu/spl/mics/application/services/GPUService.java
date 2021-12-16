package bgu.spl.mics.application.services;

import bgu.spl.mics.*;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.*;

import java.util.LinkedList;
import java.util.Queue;

/**
 * GPU service is responsible for handling the
 * {@link TrainModelEvent} and {@link TestModelEvent},
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class GPUService extends MicroService {
    private GPU gpu;
    private int currtick; //+1 +1 +1 +1 =4 0
    private int totaltick;
    private Queue<DataBatch> BatchesWaitingToBeingSentToCluster;
    private Queue<TrainModelEvent> trainModelEvents;
    private Callback<TestModelEvent> TestModelEventCallback;
    private Callback<TrainModelEvent> TrainModelEventCallback;
    private Callback<TickBroadcast> TickBroadcastCallback;
    private Callback<TerminateBroadcast> terminateBroadcastCallback;
    private Statistics statistics;


    public GPUService(String name,GPU gpu, Statistics statistics) {
        super(name + " Service");
        this.statistics = statistics;
        this.gpu = gpu;
        currtick = 1;
        totaltick = 0;
        BatchesWaitingToBeingSentToCluster = new LinkedList<>();
        trainModelEvents = new LinkedList<>();
        TestModelEventCallback = message -> complete(message,gpu.TestData());

        TrainModelEventCallback = message -> {
            trainModelEvents.add(message);
            trainModelEventComputing(trainModelEvents.peek());
            /**divide the data by 1000
             * and create x data batches and saves in q
             * while(vram != 0)
             * gpu.sendTocluster(databatch)
             * train(gpu.getq)
             */

        };
        TickBroadcastCallback = Tickbroadcast -> {
            if(!gpu.DataBatchesRecivedFromCPU.isEmpty()) {
                totaltick ++;
                if (currtick == gpu.getTick()) {
                    System.out.println("GPU processing");
                    statistics.addTotalgpubatches(1);
                    gpu.train(gpu.DataBatchesRecivedFromCPU.remove());
                    currtick = 1;
                    if(!BatchesWaitingToBeingSentToCluster.isEmpty()) {
                        gpu.sendTocluster(BatchesWaitingToBeingSentToCluster.remove());
                    }
                    if(gpu.getCurrenData().getSize() == gpu.getCurrenData().HowManyProcessed()) {
                        gpu.getCurrenData().setmodel_trained();
                        System.out.println("GPU complete " + name);
                        complete(trainModelEvents.remove(), gpu.getCurrenData());
                        gpu.SetData(null,null);
                        if(!trainModelEvents.isEmpty())
                            trainModelEventComputing(trainModelEvents.peek());
                    }

                }
                else {
                    currtick ++;
                }
            }

            /**
             * if x ticks have passed than get check gpu q, if not empty train a batch, when finished check if there are batches avilable to send and
             * if yes send them to cpu
             * process data when q is not empty
             * cpu.SendToGPU(databatch)
             */
        };
        terminateBroadcastCallback = ter -> {
          statistics.addTotalgputicks(totaltick);
          System.out.println(getName() + " is terminating");
          while(!trainModelEvents.isEmpty()) {
              Data nulldata = new Data("Images",0,null);
              complete(trainModelEvents.remove(), nulldata);
          }
          terminate();
        };

    }

    @Override
    protected void initialize() {
        subscribeEvent(TrainModelEvent.class, TrainModelEventCallback);
        subscribeEvent(TestModelEvent.class, TestModelEventCallback);
        subscribeBroadcast(TickBroadcast.class, TickBroadcastCallback);
        subscribeBroadcast(TerminateBroadcast.class, terminateBroadcastCallback);


    }

    public void trainModelEventComputing(TrainModelEvent message) {
        if(gpu.getCurrenData() == null) {
            gpu.SetData(message.getData(), message.getModel());
            Data data = message.getData();
            data.setmodel_training();
            int index = 0;
            for (int i = 0; i < data.getSize()/1000; i++) {
                BatchesWaitingToBeingSentToCluster.add(new DataBatch(data,index));
                index = index + 1000;
            }
            while (gpu.VramCapacity != 0 & !BatchesWaitingToBeingSentToCluster.isEmpty()) {
                gpu.sendTocluster(BatchesWaitingToBeingSentToCluster.remove());
            }
        }

    }
/**
    GPU.train
    for(i = 0; i< GPU.total; i++) {
        sendBroadcast(tickbroadcast);
    }


    //first thing when run is initiated need  to add to the GPU the Data and the model.
    //recive all relevant data from event
    //devide by 1000 and set the number of batches we need to train
    //while(GPU.VramCapacity != 0) send batches, else wait for the GPU to notify we have more place
    recives an index of the databatch that finished and start processing it.
    //while(total data is not proccessed) wait for GPU to notify and after that complete event.
 */
}
