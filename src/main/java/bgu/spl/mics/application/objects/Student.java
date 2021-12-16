package bgu.spl.mics.application.objects;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

/**
 * Passive object representing single student.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Student {
    /**
     * Enum representing the Degree the student is studying for.
     */
    enum Degree {
        MSc, PhD
    }


    private String name;
    private String department;
    private Degree status;
    private LinkedList<Model> myModels;
    private LinkedList<Model> models;
    private int publications;
    private int papersRead;

    public Student(String name, String depar, String status) {
        this.name = name;
        department = depar;
        myModels = new LinkedList<>();
        models = new LinkedList<>();
        switch (status) {
            case "MSc" :
                this.status = Degree.MSc;
                break;
            case "PhD":
                this.status = Degree.PhD;
                break;
        }
        publications = 0;
        papersRead = 0;
    }

    public String getStatus() {
        return status.toString();
    }

    public void addModel(Model m) {
        myModels.add(m);
        models.add(m);
    }

    public LinkedList<Model> getModelsforjson() {
        return models;
    }

    public String getName() {
        return name;
    }

    public void addPublication() {
        publications++;
    }

    public void addReads(int reads) {
        papersRead = papersRead + reads;
    }

    public LinkedList<Model> getMyModels() {
        return myModels;
    }

    public int getPublications() {
        return publications;
    }

    public String getDepartment() {
        return department;
    }

    public int getPapersRead() {
        return papersRead;
    }
}
