package bgu.spl.mics.application.objects;

import java.util.Queue;

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
    private Queue<DataBatch> DataBatchesRecivedFromCPU;
    private Type type; // for constructor
    private Model model;
    private Cluster cluster; //the computer
    public int VramCapacity; //free capacity to use in VRAM - when 0 we wait that we have free
    private Data currenData; //Total data we are working on, need to recive from event
    private int tick; //for ticks, every time timeservice wants it he can have it

    public GPU(String type) { //constructor
        switch (type) {
            case "RTX3090":
                this.type = Type.RTX3090;
                VramCapacity = 32;
                tick = 1;
            case "RTX2080":
                this.type = Type.RTX2080;
                VramCapacity = 16;
                tick = 2;
            case "GTX1080":
                this.type = Type.GTX1080;
                VramCapacity = 8;
                tick = 4;
        }
        tick = 0;

    }

    /**
     *
     * @param dataBatch the databatch we want to process
     * @pre: databatch not processed
     * @post: cluster recived batch to handle
     * @return true after sent
     */
    public boolean sendTocluster(DataBatch dataBatch){
       while (VramCapacity == 0)
           try {
               //notifyall will be in train
               wait();
           }
       catch (Exception e) {}
        cluster.SendBatchCpu(dataBatch);
        VramCapacity =- VramCapacity;
       return true;
    }

    /**
     *
     * @param dataBatch that we want to train
     * @pre: processed data from cpu
     * @post: databatch is processed and processed amount added to data
     *         if we finshed we check if all the data is processed
     *        total_time_worked = (currenttimed worked on) + @pre(total_time_worked)
     */
    public void train(DataBatch dataBatch){
        switch (type) {
            case RTX3090:
                currenData.addProcessed(1000);
                VramCapacity =+ 1;
                notifyAll();
            case RTX2080:
                currenData.addProcessed(1000);
                VramCapacity =+ 1;
                notifyAll();
            case GTX1080:
                currenData.addProcessed(1000);
                VramCapacity =+ 1;
                notifyAll();
        }
            //trains the proccessed data, if the vram capacity is 0 we need to notify all to wake the service up
            // if the total data is equal to proccessed data we notify all to wake the proccess to finish the event
            //for each procces we update total time spent
    }

    /**
     *
     * @param data data need to procces
     * @pre: gpu wasent proccesing
     * @post: gpu start to procces
     * return: true indication
     */
    public void SetData(Data data,Model model){
        currenData = data;
        this.model = model;
    }

    /**
     *
     * @param degree testing model
     * @pre: none result for the test model
     * @post: getting result to the test model
     *        by probability depend on the student degree
     */
    public int TestData(Student.Degree degree) {
        return  (int)(Math.random()*10);
    }

    /**
     *
     * @return the time that the cpu have worked on
     */
    public int getTick(){
        return tick;
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

    public void reciveFromCPU(DataBatch dataBatch) {
        DataBatchesRecivedFromCPU.add(dataBatch);
    }

}



