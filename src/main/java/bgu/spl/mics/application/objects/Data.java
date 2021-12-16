package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Data {
    /**
     * Enum representing the Data type.
     */
    enum Type {
        Images, Text, Tabular
    }

    private Type type;
    private int processed;
    private int size;
    private Model model;

    public Data(String type,int size, Model model1) {
        switch (type) {
            case "Images":
                this.type = Type.Images;
                break;
            case "Text":
                this.type = Type.Text;
                break;
            case "Tabular":
                this.type = Type.Tabular;
                break;
        }
        this.size = size;
        processed = 0;
        model = model1;
    }

    public int HowManyProcessed(){
        return processed;
    }

    public boolean IsProcessed(){
        return size == processed;
    }

    public void addProcessed(int processed) {
        this.processed = this.processed + processed;
    }

    public String getType() {
        return type.toString();
    }

    public String getTypeToString() {
        return type.toString();
    }


    public int getSize() {
        return size;
    }

    public void setModel(Model model){
        this.model = model;
    }

    public void setmodel_training() {
        model.setmodel_training();
    }

    public void setmodel_trained() {
        model.setmodel_Trained();
    }

    public void setmodel_tested() {
        model.setmodel_Tested();
    }



}
