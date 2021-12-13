package bgu.spl.mics;

import bgu.spl.mics.application.objects.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import java.util.Vector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CPUTest {

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

        gpu = new GPU(GPUs, "name");
        v1.add(gpu);
        cpu = new CPU(4, "name");
        v2.add(cpu);
        cluster = Cluster.getInstance(v1,v2);
        gpu.setCluster(cluster);
        data = new Data(Datas,1000);
        batch = new DataBatch(data,0);
        student = new Student("name","somedepart", "MSc");
        model = new Model(student,data,"Test Model");
    }

    @Test
    public void testsetCluster() {
        assertTrue(cpu.setCluster(cluster));
        assertEquals(cpu,cluster.getCPU());
    }

    @Test
    public void testsendBatchToCPU() {
        int before = cpu.getQsize();
        cpu.sendBatchToCPU(batch);
        assertTrue(before + 1 == cpu.getQsize());
    }

    public void testprocessData() {
        long start_time = System.currentTimeMillis();
        DataBatch d = cpu.processData();
        long estimated_time = System.currentTimeMillis() - start_time;
        assertTrue(estimated_time >= cpu.getTotal_time_worked());
        assertTrue(d.isCPUDone());
    }

}
