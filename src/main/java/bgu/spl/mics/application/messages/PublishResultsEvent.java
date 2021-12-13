package bgu.spl.mics.application.messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;

public class PublishResultsEvent implements Event<Boolean> {
    public Model model;

    public PublishResultsEvent(Model m) {
        model = m;
    }


}
