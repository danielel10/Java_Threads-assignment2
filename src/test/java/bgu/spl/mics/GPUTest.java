package bgu.spl.mics;

import bgu.spl.mics.application.objects.Cluster;
import bgu.spl.mics.application.objects.Data;
import bgu.spl.mics.application.objects.DataBatch;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.example.messages.ExampleEvent;
import bgu.spl.mics.example.services.ExampleEventHandlerService;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class GPUTest {
    String GPUs = "RTX3090";
    String Datas = "Images";
    private static GPU gpu;
    private static Data data;
    private static DataBatch batch;

    private static Cluster cluster;

    @Before
    public void setup() {
        gpu = new GPU(GPUs, cluster);
        data = new Data(Datas,1000);
        batch = new DataBatch(data,0);
    }

    @Test
    public void testsendToCpu() {
        boolean result = gpu.sendToCpu(batch);
        assertTrue(result);
    }

    @Test
    public void testtrain(){
        gpu.sendToCpu(batch);
        long startTime = System.currentTimeMillis();
        gpu.train(batch);
        long estimatedTime = System.currentTimeMillis() - startTime;
        //from our example jason file
        assertTrue(estimatedTime >= 50);
        assertEquals(data.HowManyProcessed(),1);
        assertTrue(data.IsProcessed());
    }

    @Test


}
