package bgu.spl.mics.application.objects;

import java.util.LinkedList;
import java.util.Vector;

/**
 * Passive object representing information on a conference.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class ConfrenceInformation {

    private String name;
    private int date;
    private LinkedList<Model> models;

    public ConfrenceInformation(String name, int date ) {
        this.name = name;
        this.date = date;
        models = new LinkedList<>();
    }

    public void addModel(Model m) {
        models.add(m);
    }

    public LinkedList<Model> getModelVector() {
        return models;
    }

    public int getDate(){
        return date;
    }
    public String getName(){
        return name;
    }


}
