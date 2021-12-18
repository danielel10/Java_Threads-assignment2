package bgu.spl.mics;

import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.objects.Statistics;
import bgu.spl.mics.application.services.GPUService;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class FutureTest {
    private static TestModelEvent event;
    private static Future<Integer> intgFuture;
    private static MessageBus messageBus;
    private static GPUService gpuService;
    private static GPU gpu;

    @Before
    public void setup() throws  Exception {
        intgFuture = new Future<Integer>();
        gpu = new GPU("GTX1080","test");
        gpuService = new GPUService("testserviceE",gpu,new Statistics());
        messageBus = MessageBusImpl.getInstance();
        event = new TestModelEvent();
    }

    @Test
    public void testget() {
        //pre
        Thread t = new Thread(gpuService);
        t.start();
        intgFuture = messageBus.sendEvent(event);
        assertEquals(intgFuture,null);
        assertEquals(Thread.State.WAITING, t.getState());
        t.interrupt();
        messageBus.subscribeEvent(event.getClass(),gpuService);
        intgFuture = messageBus.sendEvent(event);
        assertNotEquals(intgFuture,null);
        //post
        intgFuture.resolve(0);
        Integer result = intgFuture.get();
        assertTrue(result.equals(0));
    }

    @Test
    public void testresolve() {
        messageBus.subscribeEvent(event.getClass(),gpuService);
        intgFuture = messageBus.sendEvent(event);
        intgFuture.resolve(0);
        Integer resultPost = intgFuture.get();
        assertTrue(resultPost.equals(0));
    }

    @Test
    public void testIsDone() {
        intgFuture.resolve(0);
        boolean result = intgFuture.isDone();
        assertTrue(result);
    }

    @Test
    public void testgetTimer() {
        //pre
        messageBus.subscribeEvent(event.getClass(),gpuService);
        intgFuture = messageBus.sendEvent(event);
        assertNotEquals(intgFuture,null);
        //post
        long startTime = System.currentTimeMillis();
        intgFuture.get(500, TimeUnit.MILLISECONDS);
        if(!intgFuture.isDone()){
            long estimatedTime = System.currentTimeMillis() - startTime;//checking that at list 500 miliseconds have passed
            assertTrue(estimatedTime >= 500);
        }
        else{
            Integer result = intgFuture.get();
            assertNotEquals(result,null);
        }

    }


}