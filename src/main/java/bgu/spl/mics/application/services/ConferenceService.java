package bgu.spl.mics.application.services;

import bgu.spl.mics.Callback;
import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.*;
import bgu.spl.mics.application.objects.*;

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
    private ConfrenceInformation confrenceInformation;
    private int curr_tick;
    private Callback<PublishResultsEvent> publishResultsEventCallback;
    private Callback<TickBroadcast> tickBroadcastCallback;
    private Callback<TerminateBroadcast> terminateBroadcastCallback;



    public ConferenceService(String name,int ticks_to_terminate, ConfrenceInformation confrenceInformation, TotalConferenceData total) {
        super(name);
        this.ticks_to_terminate = ticks_to_terminate;
        this.confrenceInformation = confrenceInformation;
        totalConferenceData = total;

        publishResultsEventCallback = message -> {
            confrenceInformation.addModel(message.model);
        };
        tickBroadcastCallback = messege -> {
            curr_tick ++;
            if (curr_tick == ticks_to_terminate) {
                totalConferenceData.addconference(confrenceInformation);
                sendBroadcast(new PublishConferenceBroadcast(confrenceInformation.getModelVector()));
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


