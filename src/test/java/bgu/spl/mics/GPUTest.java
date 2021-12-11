package bgu.spl.mics;

import bgu.spl.mics.application.objects.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;

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
        double high = 0.6;
        double low = 0.8;
        assertTrue(gpu.TestData() == high | gpu.TestData() == low );
    }

    @Test
    public void testgetTotalTime() {
        long start_time = System.currentTimeMillis();
        testtrain();
        long finish_time = System.currentTimeMillis();
        int total = gpu.getTick();
        assertTrue(finish_time - start_time >=total);

    }




}
