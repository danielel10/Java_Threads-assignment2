package bgu.spl.mics.application.services;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.messages.PublishConferenceBroadcast;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.Model;

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
    //not intended/////////////////
    private Future<Data> t;
    //////////////////////////////
    public ConferenceService(String name) {
        super("Change_This_Name");
    }

    @Override
    protected void initialize() {
        //not intended//////////////////////////
        //not intended///////////////////
        Data data = new Data("Text",1000);
        Model model = null;
        TrainModelEvent trainModelEvent = new TrainModelEvent(data,model);
        t = sendEvent(trainModelEvent);
        sendBroadcast(new TickBroadcast());
        sendBroadcast(new TickBroadcast());
        sendBroadcast(new TickBroadcast());
        sendBroadcast(new TickBroadcast());
        sendBroadcast(new TickBroadcast());
        //////////////////////////////////////
        Data data1 = t.get();
        System.out.println("I GOT IT!!!! CONFERENCEEE");
        /////////////////////////////////////////

    }
}
