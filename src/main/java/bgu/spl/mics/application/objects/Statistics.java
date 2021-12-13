package bgu.spl.mics.application.objects;

import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistics {
    private int totalDataBatchProcessedByCPU; //from all cpus
    private int totalcputicks;
    private int totalgputicks;
    private AtomicInteger counterproceesedbatches = new AtomicInteger();
    private AtomicInteger countercputicks = new AtomicInteger();
    private AtomicInteger countergputicks= new AtomicInteger();

    public Statistics () {
        totalcputicks = 0;
        totalgputicks = 0;
        totalDataBatchProcessedByCPU = 0;
    }

    public void addTotalDataBatchProcessedByCPU(int totalDataBatchProcessedByCPU) {
        do {
            this.totalDataBatchProcessedByCPU = counterproceesedbatches.get();
        }while(!counterproceesedbatches.compareAndSet(this.totalDataBatchProcessedByCPU,this.totalDataBatchProcessedByCPU + totalDataBatchProcessedByCPU));
    }

    public void addTotalcputicks(int totalcputicks) {
        do {
            this.totalcputicks = countercputicks.get();
        }while(!countercputicks.compareAndSet(this.totalcputicks,this.totalcputicks + totalcputicks));
    }

    public void addTotalgputicks(int totalgputicks) {
        do {
            this.totalgputicks = countergputicks.get();
        }while(!countergputicks.compareAndSet(this.totalgputicks,this.totalgputicks + totalgputicks));
    }

}
