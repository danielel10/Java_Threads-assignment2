package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Event;
import bgu.spl.mics.Message;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.GPU;

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
    private Queue<TrainModelEvent> trainModelEvents;
    private Callback<TestModelEvent> TestModelEventCallback;
    private Callback<TrainModelEvent> TrainModelEventCallback;
    public GPUService(String name,GPU gpu) {
        super(name + " Service");
        this.gpu = gpu;
        currtick = 0;
        TestModelEventCallback = c -> complete(c,gpu.TestData());
        TrainModelEventCallback = c -> {
            trainModelEvents.add(c);
        };






    }

    @Override
    protected void initialize() {
        subscribeEvent(TrainModelEvent.class, TrainModelEventCallback);
        subscribeEvent(TestModelEvent.class, TestModelEventCallback);


    }

    public void trainModelEventComputing(Message message) {
        /**
         * insert message to event q
         * pull an event from q
         * process it with cluster
         * while this happens the processing happens in the cpu service
         * we wait for a broadcast message telling us that we can train a new batch
         */

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
