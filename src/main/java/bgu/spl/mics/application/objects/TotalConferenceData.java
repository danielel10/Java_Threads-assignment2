package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.services.ConferenceService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TotalConferenceData {
    private ConcurrentLinkedQueue<ConfrenceInformation> confrenceInformationConcurrentLinkedQueue;

    public TotalConferenceData() {
        confrenceInformationConcurrentLinkedQueue = new ConcurrentLinkedQueue<>();
    }

    public void addconference(ConfrenceInformation c) {
        confrenceInformationConcurrentLinkedQueue.add(c);
    }

    public ConfrenceInformation getConfe() {
        return confrenceInformationConcurrentLinkedQueue.remove();
    }
}
