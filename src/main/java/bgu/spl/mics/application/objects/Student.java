package bgu.spl.mics.application.objects;

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
    private Vector<Model> myModels;
    private int sentModelsToConference;
    private int publications;
    private int papersRead;

    public Student(String name, String depar, String status) {
        this.name = name;
        department = depar;
        switch (status) {
            case "MSc" :
                this.status = Degree.MSc;
            case "PhD":
                this.status = Degree.PhD;
        }
        sentModelsToConference = 0;
        publications = 0;
        papersRead = 0;
    }

    public Degree getStatus() {
        return status;
    }

    public void addModel(Model m) {
        myModels.add(m);
    }

}
