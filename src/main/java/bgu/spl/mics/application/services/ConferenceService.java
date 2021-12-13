package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.Model;
import bgu.spl.mics.application.objects.Student;
import bgu.spl.mics.application.objects.TotalConferenceData;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Conference service is in charge of
 * aggregating good results and publishing them via the {@link PublishConferenceBroadcast},
 * after publishing results the conference will unregister from the system.
 * This class may not hold references for objects which it is not responsible for.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class ConferenceService extends MicroService {
    private int ticks_to_terminate;
    private TotalConferenceData totalConferenceData;
    private int curr_tick;
    private ConcurrentLinkedQueue<Model> models_to_publish;
    private Callback<PublishResultsEvent> publishResultsEventCallback;
    private Callback<TickBroadcast> tickBroadcastCallback;
    private PublishConferenceBroadcast publishConferenceBroadcast;
    private Callback<TerminateBroadcast> terminateBroadcastCallback;



    public ConferenceService(String name,int ticks_to_terminate) {
        super(name);
        this.ticks_to_terminate = ticks_to_terminate;
        models_to_publish = new ConcurrentLinkedQueue<>();
        publishResultsEventCallback = message -> {
            models_to_publish.add(message.model);
        };
        tickBroadcastCallback = messege -> {
            curr_tick ++;
            if (curr_tick == ticks_to_terminate) {
                totalConferenceData.addConferenceMap(this,models_to_publish);
                publishConferenceBroadcast.totalmodels = models_to_publish.size();
                sendBroadcast(publishConferenceBroadcast);
                terminate();
            }
        };

        terminateBroadcastCallback = message -> {
            terminate();
        };
    }

    @Override
    protected void initialize() {
        subscribeEvent(PublishResultsEvent.class, publishResultsEventCallback);
        subscribeBroadcast(TickBroadcast.class, tickBroadcastCallback );
        subscribeBroadcast(TerminateBroadcast.class,terminateBroadcastCallback);

    }
}



    //not intended//////////////////////////
    //not intended///////////////////
//    Model model = null;
//    Data data = new Data("Text",1000, model);
    //        TrainModelEvent trainModelEvent = new TrainModelEvent(data,model);
//        t = sendEvent(trainModelEvent);
//    sendBroadcast(new TickBroadcast());
//        sendBroadcast(new TickBroadcast());
//        sendBroadcast(new TickBroadcast());
//        sendBroadcast(new TickBroadcast());
//        sendBroadcast(new TickBroadcast());
//        //////////////////////////////////////
//        Data data1 = t.get();
//        System.out.println("I GOT IT!!!! CONFERENCEEE");
/////////////////////////////////////////
