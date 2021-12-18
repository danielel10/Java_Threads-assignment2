package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.messages.TickBroadcast;

/**
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class CPUService extends MicroService {

    private CPU cpu;
    private int curr_tick_image;
    private int curr_tick_text;
    private int curr_tick_tabular;
    private int total_batches_processed;
    private int total_tick;
    private Statistics statistics;
    private Callback<TerminateBroadcast> terminateBroadcastCallback;
    private Callback<TickBroadcast> TickBroadcastCallback;



    public CPUService(String name, CPU cpu, Statistics statistics) {
        super(name + " Service" );
        this.cpu = cpu;
        this.statistics = statistics;
        curr_tick_image = 1;
        curr_tick_tabular = 1;
        curr_tick_text = 1;
        total_tick = 0;
        total_batches_processed = 0;

        TickBroadcastCallback = Tickbroadcast -> {
            DataBatch batch = cpu.getBatch();
            if(batch != null) {
                total_tick ++;
                switch (batch.getTypeToSring()) {
                    case ("Images") :
                        if (curr_tick_image == cpu.getTotal_time_worked() * 4) {
                            cpu.SendToGPU(cpu.processData());
                            total_batches_processed++;
                            curr_tick_image = 1;
                        }
                        else {
                            curr_tick_image ++;
                        }
                        break;
                    case ("Text") :
                        if (curr_tick_text == cpu.getTotal_time_worked() * 2) {
                            cpu.SendToGPU(cpu.processData());
                            total_batches_processed++;
                            curr_tick_text = 1;
                        }
                        else {
                            curr_tick_text ++;
                        }
                        break;
                    case ("Tabular") :
                        if (curr_tick_tabular == cpu.getTotal_time_worked()) {
                            cpu.SendToGPU(cpu.processData());
                            total_batches_processed++;
                            curr_tick_tabular = 1;
                        }
                        else {
                            curr_tick_tabular ++;
                        }
                        break;
                }
            }
            /**
             * check cpu q
             * process data when q is not empty
             * cpu.SendToGPU(databatch)
             */
        };
        terminateBroadcastCallback = c -> {
            statistics.addTotalcputicks(total_tick);
            statistics.addTotalDataBatchProcessedByCPU(total_batches_processed);
            terminate();
        };
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TickBroadcast.class, TickBroadcastCallback);
        subscribeBroadcast(TerminateBroadcast.class, terminateBroadcastCallback);


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
