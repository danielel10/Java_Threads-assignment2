package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private long duration;

    public TickBroadcast(long d) {
        duration = d;
    }

    public long getDuration() {
        return duration;
    }
}
