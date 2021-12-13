package bgu.spl.mics.application.objects;

import java.util.Vector;

public class Statistics {
    private Vector<Model> trainedModel; //from student
    private int totalDataBatchProcessedByCPU; //from all cpus
    private int totalcputicks;
    private int totalgputicks;

    public Statistics () {
        trainedModel = new Vector<Model>();
        totalcputicks = 0;
        totalgputicks = 0;
        totalDataBatchProcessedByCPU = 0;
    }

    public void addTrainedModel(Model m) {
        trainedModel.add(m);
    }

    public void addTotalDataBatchProcessedByCPU(int totalDataBatchProcessedByCPU) {
        this.totalDataBatchProcessedByCPU = totalDataBatchProcessedByCPU;
    }

    public void addTotalcputicks(int totalcputicks) {
        this.totalcputicks =+ totalcputicks;
    }

    public void addTotalgputicks(int totalgputicks) {
        this.totalgputicks =+ totalgputicks;
    }

}
