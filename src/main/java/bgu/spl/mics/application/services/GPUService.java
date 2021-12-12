package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Event;
import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.DataBatch;
import bgu.spl.mics.application.objects.GPU;

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
    //total tick
    private Queue<DataBatch> BatchesWaitingToBeingSentToCluster;
    private Queue<TrainModelEvent> trainModelEvents;
    private Callback<TestModelEvent> TestModelEventCallback;
    private Callback<TrainModelEvent> TrainModelEventCallback;
    private Callback<TickBroadcast> TickBroadcastCallback;


    public GPUService(String name,GPU gpu) {
        super(name + " Service");
        this.gpu = gpu;
        currtick = 0;
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
            currtick =+ 1;
            if (currtick == gpu.getTick()) {
                if(!gpu.DataBatchesRecivedFromCPU.isEmpty()) {
                    gpu.train(gpu.DataBatchesRecivedFromCPU.remove());
                    if(!BatchesWaitingToBeingSentToCluster.isEmpty()) {
                        gpu.sendTocluster(BatchesWaitingToBeingSentToCluster.remove());
                    }
                    if(gpu.getCurrenData().getSize() == gpu.getCurrenData().HowManyProcessed()) {
                        complete(trainModelEvents.remove(), gpu.getCurrenData());
                        gpu.SetData(null,null);
                    }
                }

            }
            trainModelEventComputing(trainModelEvents.peek());

            /**
             * if x ticks have passed than get check gpu q, if not empty train a batch, when finished check if there are batches avilable to send and
             * if yes send them to cpu
             * process data when q is not empty
             * cpu.SendToGPU(databatch)
             */
        };

    }

    @Override
    protected void initialize() {
        subscribeEvent(TrainModelEvent.class, TrainModelEventCallback);
        subscribeEvent(TestModelEvent.class, TestModelEventCallback);
        subscribeBroadcast(TickBroadcast.class, TickBroadcastCallback);


    }

    public void trainModelEventComputing(TrainModelEvent message) {
        if(gpu.getCurrenData() == null) {
            gpu.SetData(message.getData(), message.getModel());
            Data data = message.getData();
            int index = 0;
            for (int i = 0; i < data.getSize()/1000; i++) {
                BatchesWaitingToBeingSentToCluster.add(new DataBatch(data,index));
                index =+ 1000;
            }
            while (gpu.VramCapacity != 0) {
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
