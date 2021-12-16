package bgu.spl.mics.application.objects;

/**
 * Passive object representing a Deep Learning model.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Model {
    enum Status {PreTrained, Training, Trained, Tested}
    enum Result {None, Good, Bad}
    private Status status;
    private Result result;
    private String name;
    private Data data;
    private Student student;

    public Model(Student s,Data d, String n) {
        status = Status.PreTrained;
        result = Result.None;
        name = n;
        data = d;
        student = s;
    }

    public void setmodel_training() {
        status = Status.Training;
    }

    public void setmodel_Trained() {
        status = Status.Trained;
    }

    public void setmodel_Tested() {
        status = Status.Tested;
    }

    public Data getData() {
        return data;
    }

    public void setResult(String s) {
        if(s == "g")
            result = Result.Good;
        else
            result = Result.Bad;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result.toString();
    }

    public String getStatus() {
        return status.toString();
    }
}
