package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;
import bgu.spl.mics.application.objects.Model;

import java.util.LinkedList;

public class PublishConferenceBroadcast implements Broadcast {
    public LinkedList<Model> totalmodels;

    public PublishConferenceBroadcast(LinkedList<Model> m){
        totalmodels = m;
    }
}
