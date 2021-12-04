package bgu.spl.mics.application.objects;

/**
 * Passive object representing a single GPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class GPU {
    /**
     * Enum representing the type of the GPU.
     */
    enum Type {RTX3090, RTX2080, GTX1080}
    private Type type; // for constructor
    private Model model;
    private Cluster cluster; //the computer
    public int VramCapacity; //free capacity to use in VRAM - when 0 we wait that we have free
    private Data currenData; //Total data we are working on, need to recive from event
    private int TotalTime; //for ticks, every time timeservice wants it he can have it

    public GPU(String type,Cluster c) { //constructor
        //TODO
        cluster = c;
    }

    //maybe change to synchronized
    public boolean sendToCpu(DataBatch dataBatch){
        return true;
    }

    public void train(DataBatch dataBatch){
            //trains the proccessed data, if the vram capacity is 0 we need to notify all to wake the service up
            // if the total data is equal to proccessed data we notify all to wake the proccess to finish the event
            //for each procces we update total time spent
    }

    public void SetData(Data data,Model model){
        currenData = data;
        this.model = model;
    }

    public int getTotalTime(){
        return TotalTime;
    }



}



