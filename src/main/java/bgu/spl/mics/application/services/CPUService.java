package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.objects.CPU;
import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.application.messages.TickBroadcast;

/**
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {
    private CPU cpu;
    private int tick;
    private Callback<TickBroadcast> TickBroadcastCallback;

    public CPUService(String name, CPU cpu) {
        super(name + " Service" );
        this.cpu = cpu;
        tick = 0;
        TickBroadcastCallback = Tickbroadcast -> {
            /**
             * check cpu q
             * process data when q is not empty
             * cpu.SendToGPU(databatch)
             */
        };
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, TickBroadcastCallback);

    }


    /**
     *  CPU.process
     *     for(i = 0; i< GPU.total; i++) {
     *         sendBroadcast(tickbroadcast);
     *     }
     * CPU service is responsible for managing the processing of the batches.
     * first thing when the service runs it checks its Q, if Q is empty, it waits.
     * when we get a new batch from gpu we add it to our q from the cluster and here we tell the cpu to process it.
     * after we finish processing it we send a broadcast with the GPU name and the start index of the batch
     */
}
