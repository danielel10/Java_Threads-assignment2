package bgu.spl.mics;

import static org.junit.Assert.*;

import bgu.spl.mics.application.messages.TestModelEvent;
import bgu.spl.mics.application.messages.TickBroadcast;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.objects.Statistics;
import bgu.spl.mics.application.objects.Student;
import bgu.spl.mics.application.services.GPUService;
import bgu.spl.mics.application.services.StudentService;
import org.junit.Before;
import org.junit.Test;

public class MessageBusTest {
    private static MessageBus messageBus;
    private static GPUService gpuService;
    private static StudentService studentService;
    private static TestModelEvent testModelEvent;
    private static TickBroadcast tickBroadcast;
    private static Future<Integer> IntegerFuture;

    @Before
    public void setup() throws  Exception {
        messageBus = MessageBusImpl.getInstance();
        gpuService = new GPUService("testservice",new GPU("GTX1080","test"), new Statistics());
        studentService = new StudentService(new Student("test","Computer Scince","MSc"));
        testModelEvent = new TestModelEvent();
        tickBroadcast = new TickBroadcast(1);

    }

    @Test
    public void testSubscribeEvent() {
        messageBus.subscribeEvent(testModelEvent.getClass(),gpuService);
        boolean result = messageBus.IsEventSub(testModelEvent,gpuService);
        assertTrue(result);

    }

    @Test
    public void testSubscribeBroadcast() {
        messageBus.subscribeBroadcast(tickBroadcast.getClass(),studentService);
        boolean result = messageBus.IsBroadcastSub(tickBroadcast,studentService);
        assertTrue(result);
    }

    @Test
    public void testSendEvent() {
        messageBus.subscribeEvent(testModelEvent.getClass(),gpuService);
        IntegerFuture = messageBus.sendEvent(testModelEvent);
        boolean result = messageBus.IsFutureAdded(IntegerFuture);
        if(IntegerFuture != null)
            assertTrue(result);
        else
            assertFalse(result);
    }

    @Test
    public void testSendBroadcast() {
        messageBus.subscribeBroadcast(tickBroadcast.getClass(),studentService);
        messageBus.sendBroadcast(tickBroadcast);
        boolean result = messageBus.IsBroadcastRecived(tickBroadcast);
        assertTrue(result);

    }

    @Test
    public void testComplete() {
        messageBus.subscribeEvent(testModelEvent.getClass(),gpuService);
        messageBus.sendEvent(testModelEvent);
        messageBus.complete(testModelEvent,0);
        boolean result = messageBus.IsFuturecomleted(testModelEvent);
        assertTrue(result);

    }

    @Test
    public void testRegister() {
        messageBus.register(gpuService);
        assertTrue(messageBus.IsRegisterd(gpuService));
    }

    @Test
    public void testUnregister() {
        messageBus.unregister(gpuService);
        assertTrue(messageBus.IsUnregistered(gpuService));
    }

    @Test
    public void testAwaitMessage() {
        if (!messageBus.IsRegisterd(gpuService))
            assertThrows(IllegalStateException.class,() -> messageBus.awaitMessage(gpuService));
        try {
            messageBus.awaitMessage(gpuService);
        }catch (Exception e) {

        }
        assertTrue(messageBus.HaveRecivedMessage(gpuService));



    }

}
