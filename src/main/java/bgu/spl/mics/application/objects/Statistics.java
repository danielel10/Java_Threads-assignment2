package bgu.spl.mics.application.objects;

import java.util.Vector;

public class Statistics {
    private Vector<Model> trainedModel;
    private int totalDataBatchProcessedByCPU;
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

    public void addTotalDataBatchProcessedByCPU() {
        totalDataBatchProcessedByCPU++;
    }

    public void addTotalcputicks() {
        totalcputicks++;
    }

    public void addTotalgputicks() {
        totalgputicks++;
    }

}
