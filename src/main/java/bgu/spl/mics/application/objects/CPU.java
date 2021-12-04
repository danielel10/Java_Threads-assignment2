package bgu.spl.mics.application.objects;

import java.util.Collection;

/**
 * Passive object representing a single CPU.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class CPU {

    private int NumberOfCores;
    private Collection<DataBatch> dataBatches;
    private Cluster cluster;

    public CPU(int cores) {
        NumberOfCores = cores;
    }


    public boolean setCluster(Cluster c) {
        cluster = c;
        return true;
    }



}
