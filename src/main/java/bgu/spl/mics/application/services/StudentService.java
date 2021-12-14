package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Event;
import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Student is responsible for sending the {@link TrainModelEvent},
 * {@link TestModelEvent} and {@link PublishResultsEvent}.
 * In addition, it must sign up for the conference publication broadcasts.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class StudentService extends MicroService {
    private Student student;
    private Queue<Model> models;
    private Callback<TerminateBroadcast> terminateBroadcastCallback;
    private Callback<TickBroadcast> tickBroadcastCallback;
    private Callback<PublishConferenceBroadcast> publishConferenceBroadcastCallback;

    public StudentService(Student student) {
        super(student.getName() + " Service");
        this.student = student;
        models = student.getMyModels();
        terminateBroadcastCallback = message -> {

        };
        tickBroadcastCallback = message -> {
            if (!models.isEmpty()) {
                Model model = models.remove();
                Data students_data = model.getData();
                Future<Data> future = sendEvent(new TrainModelEvent(students_data,model));
                future.get();
                Future<Integer> testresult = sendEvent(new TestModelEvent());
                testresult.get();
                if(student.getStatus() == "Msc" ) {
                    Integer num = 6;
                    if (testresult.get() <= num) {
                        model.setResult("g");
                        model.setmodel_Tested();
                        student.addPublication();
                        sendEvent(new PublishResultsEvent(model));
                    }
                    else {
                        model.setResult("b");
                        model.setmodel_Tested();
                    }

                }
                else {
                    Integer num = 7;
                    if (testresult.get() <= num) {
                        model.setResult("g");
                        model.setmodel_Tested();
                        student.addPublication();
                        sendEvent(new PublishResultsEvent(model));
                    }
                    else {
                        model.setResult("b");
                        model.setmodel_Tested();
                    }

                }
            }
        };

        publishConferenceBroadcastCallback = message -> {
            student.addReads(message.totalmodels - student.getPublications());
        };

    }

    @Override
    protected void initialize() {
        subscribeBroadcast(PublishConferenceBroadcast.class,publishConferenceBroadcastCallback);
        subscribeBroadcast(TickBroadcast.class,tickBroadcastCallback);
        subscribeBroadcast(TerminateBroadcast.class,terminateBroadcastCallback);

    }
}
