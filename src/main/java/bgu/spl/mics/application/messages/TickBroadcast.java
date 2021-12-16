package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TickBroadcast implements Broadcast {

    private boolean sent;

    public TickBroadcast() {
        sent = true;
    }
}
