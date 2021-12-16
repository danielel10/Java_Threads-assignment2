package bgu.spl.mics.application.messages;

import bgu.spl.mics.Broadcast;

public class TerminateBroadcast implements Broadcast {

    private boolean sent;

    public TerminateBroadcast() {
        sent = true;
    }
}
