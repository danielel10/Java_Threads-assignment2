//package bgu.spl.mics;
//
//import bgu.spl.mics.example.messages.ExampleBroadcast;
//import bgu.spl.mics.example.messages.ExampleEvent;
//import bgu.spl.mics.example.services.ExampleBroadcastListenerService;
//import bgu.spl.mics.example.services.ExampleEventHandlerService;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.*;
//
//public class FutureTest {
//    private static ExampleEvent event;
//    private static Future<String> stringFuture;
//    private static MessageBus messageBus;
//    private static ExampleEventHandlerService eventHandlerService;
//
//    @Before
//    public void setup() throws  Exception {
//        stringFuture = new Future<String>();
//        eventHandlerService = new ExampleEventHandlerService("testserviceE",new String[1]);
//        messageBus = MessageBusImpl.getInstance();
//        ExampleEvent event = new ExampleEvent("testE");
//    }
//
//    @Test
//    public void testget() {
//        //pre
//        Thread eventHandleThread = new Thread(eventHandlerService);
//        eventHandleThread.start();
//        stringFuture = messageBus.sendEvent(event);
//        assertEquals(stringFuture,null);
//        assertEquals(Thread.State.WAITING, eventHandleThread.getState());
//        eventHandleThread.interrupt();
//        messageBus.subscribeEvent(event.getClass(),eventHandlerService);
//        stringFuture = messageBus.sendEvent(event);
//        assertNotEquals(stringFuture,null);
//        //post
//        stringFuture.resolve("result");
//        String result = stringFuture.get();
//        assertEquals(result,"result");
//    }
//
//    @Test
//    public void testresolve() {
//        messageBus.subscribeEvent(event.getClass(),eventHandlerService);
//        stringFuture = messageBus.sendEvent(event);
//        stringFuture.resolve("ans");
//        String resultPost = stringFuture.get();
//        assertEquals(resultPost,"ans");
//    }
//
//    @Test
//    public void testIsDone() {
//        stringFuture.resolve("resolved!");
//        boolean result = stringFuture.isDone();
//        assertTrue(result);
//    }
//
//    @Test
//    public void testgetTimer() {
//        //pre
//        messageBus.subscribeEvent(event.getClass(),eventHandlerService);
//        stringFuture = messageBus.sendEvent(event);
//        assertNotEquals(stringFuture,null);
//        //post
//        long startTime = System.currentTimeMillis();
//        stringFuture.get(500, TimeUnit.MILLISECONDS);
//        if(!stringFuture.isDone()){
//            long estimatedTime = System.currentTimeMillis() - startTime;//checking that at list 500 miliseconds have passed
//            assertTrue(estimatedTime >= 500);
//        }
//        else{
//            String result = stringFuture.get();
//            assertNotEquals(result,null);
//        }
//
//    }
//
//
//}