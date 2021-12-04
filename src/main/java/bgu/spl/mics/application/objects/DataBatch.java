package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */

public class DataBatch {
    private Data data;
    private int start_index;
    private boolean CPUDone;

    public DataBatch(Data d,int start){
        data = d;
        start_index = start;
    }
    
}
