package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.DataBatch;

import java.util.Map;

/**
 * GPU service is responsible for handling the
 * {@link TrainModelEvent} and {@link TestModelEvent},
 * in addition to sending the {@link DataPreProcessEvent}.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class GPUService extends MicroService {
    private Map<Integer,DataBatch> dataBatchIntegerMap;

    public GPUService(String name) {
        super("Change_This_Name");
        // TODO Implement this
    }

    @Override
    protected void initialize() {
        // TODO Implement this

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
