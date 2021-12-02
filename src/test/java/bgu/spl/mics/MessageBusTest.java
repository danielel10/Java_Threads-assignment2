package bgu.spl.mics;

import static org.junit.Assert.*;

import bgu.spl.mics.application.services.ConferenceService;
import bgu.spl.mics.application.services.StudentService;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class MessageBusTest {
    private static MessageBus messageBus;
    private static StudentService microService;
    //event
    //broadcast

    @Before
    public void setup() throws  Exception {
        messageBus = MessageBusImpl.getInstance();
        //TODO - implement new service aka one of the needed in document
//          microService = new MicroService("testservice");
//          broadcast;
//          event;
    }

    @Test
    public void testSubscribeEvent() {
        messageBus.subscribeEvent(eventtype,microService);



    }

}
