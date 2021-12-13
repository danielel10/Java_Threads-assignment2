package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class PublishConferenceBroadcast implements Broadcast {
    public int totalmodels;

    public PublishConferenceBroadcast(int t){
        totalmodels = t;
    }
}
