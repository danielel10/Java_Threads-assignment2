package bgu.spl.mics;

import static org.junit.Assert.*;

import bgu.spl.mics.example.messages.ExampleBroadcast;
import bgu.spl.mics.example.messages.ExampleEvent;
import bgu.spl.mics.example.services.ExampleBroadcastListenerService;
import bgu.spl.mics.example.services.ExampleEventHandlerService;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class MessageBusTest {
    private static MessageBus messageBus;
    private static ExampleEventHandlerService eventHandlerService;
    private static ExampleBroadcastListenerService broadcastListenerService;
    private static ExampleEvent event;
    private static ExampleBroadcast broadcast;
    private static Future<String> stringFuture;

    @Before
    public void setup() throws  Exception {
        messageBus = MessageBusImpl.getInstance();
        eventHandlerService = new ExampleEventHandlerService("testserviceE",new String[1]);
        broadcastListenerService = new ExampleBroadcastListenerService("testseriveB",new String[1]);
        event = new ExampleEvent("testE");
        broadcast = new ExampleBroadcast("testB");

    }

    @Test
    public void testSubscribeEvent() {
        messageBus.subscribeEvent(event.getClass(),eventHandlerService);
        boolean result = messageBus.IsEventSub(event,eventHandlerService);
        assertTrue(result);

    }

    @Test
    public void testSubscribeBroadcast() {
        messageBus.subscribeBroadcast(broadcast.getClass(),broadcastListenerService);
        boolean result = messageBus.IsBroadcastSub(broadcast,broadcastListenerService);
        assertTrue(result);
    }

    @Test
    public void testSendEvent() {
        messageBus.subscribeEvent(event.getClass(),eventHandlerService);
        stringFuture = messageBus.sendEvent(event);
        boolean result = messageBus.IsFutureAdded(stringFuture);
        if(stringFuture != null)
            assertTrue(result);
        else
            assertFalse(result);
    }

    @Test
    public void testSendBroadcast() {
        messageBus.subscribeBroadcast(broadcast.getClass(),broadcastListenerService);
        messageBus.sendBroadcast(broadcast);
        boolean result = messageBus.IsBroadcastRecived(broadcast);
        assertTrue(result);

    }

    @Test
    public void testComplete() {
        messageBus.subscribeEvent(event.getClass(),eventHandlerService);
        messageBus.sendEvent(event);
        messageBus.complete(event,"test string result");
        boolean result = messageBus.IsFuturecomleted(event);
        assertTrue(result);

    }

    @Test
    public void testRegister() {
        messageBus.register(eventHandlerService);
        assertTrue(messageBus.IsRegisterd(eventHandlerService));
    }

    @Test
    public void testUnregister() {
        messageBus.unregister(eventHandlerService);
        assertTrue(messageBus.IsUnregistered(eventHandlerService));
    }

    @Test
    public void testAwaitMessage() {
        if (messageBus.IsRegisterd(eventHandlerService))
            assertThrows(IllegalStateException.class,() -> messageBus.awaitMessage(eventHandlerService));
        try {
            messageBus.awaitMessage(eventHandlerService);
        }catch (Exception e) {

        }
        assertTrue(messageBus.HaveRecivedMessage(eventHandlerService));



    }

}
