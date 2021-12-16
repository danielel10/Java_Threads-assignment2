package bgu.spl.mics.application.objects;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    private AtomicInteger counterproceesedbatches = new AtomicInteger();
    private AtomicInteger countercputicks = new AtomicInteger();
    private AtomicInteger countergputicks= new AtomicInteger();

    public Statistics () {



    }

    public void addTotalDataBatchProcessedByCPU(int batchsfromcpu) {
        int totalDataBatchProcessedByCPU;
        do {
            totalDataBatchProcessedByCPU = counterproceesedbatches.get();
        }while(!counterproceesedbatches.compareAndSet(totalDataBatchProcessedByCPU,totalDataBatchProcessedByCPU + batchsfromcpu));
    }

    public void addTotalcputicks(int cputicks) {
        int totalcputicks;
        do {
            totalcputicks = countercputicks.get();
        }while(!countercputicks.compareAndSet(totalcputicks,totalcputicks + cputicks));
    }

    public void addTotalgputicks(int gputicks) {
        int totalgputicks;
        do {
            totalgputicks = countergputicks.get();

        }while(!countergputicks.compareAndSet(totalgputicks,totalgputicks + gputicks));
    }

    public int getTotalcputicks() {
        return countercputicks.get();
    }

    public int getTotalDataBatchProcessedByCPU() {
        return counterproceesedbatches.get();
    }

    public int getTotalgputicks() {
        return countergputicks.get();
    }
}
