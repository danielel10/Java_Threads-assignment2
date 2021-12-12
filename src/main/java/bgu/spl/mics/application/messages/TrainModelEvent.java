package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.Model;

public class TrainModelEvent implements Event<Data> {
    private Data data;
    private Model model;

    public TrainModelEvent(Data data,Model model) {
        this.data = data;
        this.model = model;
    }

    public Data getData() {
        return data;
    }

    public Model getModel() {
        return model;
    }
}
