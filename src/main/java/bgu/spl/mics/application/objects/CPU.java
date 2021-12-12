package bgu.spl.mics.application.objects;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {

    private int NumberOfCores;
    private ConcurrentLinkedQueue<DataBatch> dataBatches;
    private Cluster cluster;
    private int total_time_worked;

    public CPU(int cores) {
        NumberOfCores = cores;
    }

    /**
     *
     * @param c cluster we are connected to
     * @pre: cluster created
     * @post: cpu connected to cluster
     * @return true when done
     */
    public boolean setCluster(Cluster c) {
        cluster = c;
        return true;
    }

    /**
     *
     * @param dataBatch the databatch we want to process
     * @pre: batch wasnt processed
     * @post: databatches.size = @pre(databatches.size) + 1
     */
    public void sendBatchToCPU(DataBatch dataBatch) {
        dataBatches.add(dataBatch);
    }

    /**
     * @pre: !databatches.isempty()
     * @post: databatches.size = @pre(databatches.size) - 1
     *        total_time_worked = (currenttimed worked on) + @pre(total_time_worked)
     * @return processed databatch
     */
    public DataBatch processData() {
        DataBatch dataBatch = dataBatches.remove();
        dataBatch.setCPUDone();
        return dataBatch;
    }

    /**
     *
     * @return the time that the cpu have worked on
     */
    public int getTotal_time_worked() {
        return total_time_worked;
    }

    /**
     *
     * @return databatches Q size
     */
    public int getQsize() {
        return dataBatches.size();
    }

    public void SendToGPU(DataBatch dataBatch) {
        cluster.SendBatchtoGPU(dataBatch);
    }



}
