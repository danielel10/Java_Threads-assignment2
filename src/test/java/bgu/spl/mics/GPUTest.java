package bgu.spl.mics;

import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.example.messages.ExampleEvent;
import bgu.spl.mics.example.services.ExampleEventHandlerService;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

public class GPUTest {
    String GPUs = "RTX3090";
    String Datas = "Images";
    private static GPU gpu;
    private static CPU cpu;
    private static Data data;
    private static DataBatch batch;
    private static Cluster cluster;
    private static Model model;
    private static Student student;
    private static Vector<GPU> v1;
    private static Vector<CPU> v2;

    @Before
    public void setup() {

        gpu = new GPU(GPUs);
        v1.add(gpu);
        cpu = new CPU(4);
        v2.add(cpu);
        cluster = Cluster.getInstance(v1,v2);
        cpu.setCluster(cluster);
        data = new Data(Datas,1000);
        batch = new DataBatch(data,0);
        student = new Student("name","somedepart", "MSc");
        model = new Model(student,data,"Test Model");
    }

    @Test
    public void testsetCluster() {
        assertTrue(gpu.setCluster(cluster));
        assertEquals(gpu,cluster.getGPU());
    }

    @Test
    public void testsendTocluster() {
        boolean result = gpu.sendTocluster(batch);
        assertTrue(result);
    }

    @Test
    public void testtrain(){
        gpu.sendTocluster(batch);
        long startTime = System.currentTimeMillis();
        gpu.train(batch);
        long estimatedTime = System.currentTimeMillis() - startTime;
        //from our example jason file
        assertTrue(estimatedTime >= 50);
        assertEquals(data.HowManyProcessed(),1000);
        assertTrue(data.IsProcessed());
    }

    @Test
    public void testsetData(){
        gpu.SetData(data,model);
        assertTrue(true);
    }

    @Test
    public void testTestData(){
        double high = 0.9;
        double low = 0.1;
        assertTrue(gpu.TestData(student.getStatus()) == high | gpu.TestData(student.getStatus()) == low );
    }

    @Test
    public void testgetTotalTime() {
        long start_time = System.currentTimeMillis();
        testtrain();
        long finish_time = System.currentTimeMillis();
        int total = gpu.getTotalTime();
        assertTrue(finish_time - start_time >=total);

    }




}
