package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {
    private Data data;
    private int start_index;
    private boolean CPUDone;
    private GPU who_sent;

    public DataBatch(Data d,int start){
        data = d;
        start_index = start;
        CPUDone = false;
    }

    public void setCPUDone() {
        CPUDone = true;
    }

    public void setGPU(GPU gpu) {
        who_sent = gpu;
    }

    public boolean isCPUDone() {
        return CPUDone;
    }

    public int getStart_index() {
        return start_index;
    }

    public GPU getWho_sent() {
        return who_sent;
    }

    public String getType() {
        return data.getType();
    }

    public String getTypeToSring() {
        return data.getTypeToString();
    }
}
