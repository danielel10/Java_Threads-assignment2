package bgu.spl.mics.application.objects;

import java.util.Vector;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {

    private String name;
    private int date;
    private Vector<Model> modelVector;

    public ConfrenceInformation(String name, int date ) {
        this.name = name;
        this.date = date;
    }

    public void addModel(Model m) {
        modelVector.add(m);
    }

    public Vector<Model> getModelVector() {
        return modelVector;
    }


}
