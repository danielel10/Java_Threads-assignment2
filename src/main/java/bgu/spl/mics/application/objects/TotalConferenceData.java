package bgu.spl.mics.application.objects;

import bgu.spl.mics.application.services.ConferenceService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TotalConferenceData {
    private Map<ConferenceService,ConcurrentLinkedQueue<Model>> map_of_conferences;

    public TotalConferenceData() {
        map_of_conferences = new ConcurrentHashMap<>();
    }

    public void addConferenceMap(ConferenceService c,ConcurrentLinkedQueue<Model> models) {
        map_of_conferences.put(c,models);
    }

    public Map<ConferenceService, ConcurrentLinkedQueue<Model>> getMap_of_conferences() {
        return map_of_conferences;
    }
}
