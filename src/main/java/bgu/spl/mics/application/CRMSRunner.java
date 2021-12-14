package bgu.spl.mics.application;

import bgu.spl.mics.Future;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.CPUService;
import bgu.spl.mics.application.services.ConferenceService;
import bgu.spl.mics.application.services.GPUService;
import bgu.spl.mics.example.services.ExampleMessageSenderService;

import java.util.LinkedList;
import java.util.Vector;

/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) {
        GPU gpu = new GPU("RTX3090","test");
        CPU cpu = new CPU(32,"name");
        LinkedList<GPU> gpuLinkedList = new LinkedList<>();
        gpuLinkedList.add(gpu);
        Vector<CPU> cpuVector = new Vector<>();
        cpuVector.add(cpu);
        Cluster cluster = Cluster.getInstance(gpuLinkedList,cpuVector);
        gpu.setCluster(cluster);
        cpu.setCluster(cluster);
        System.out.println(cpu.getClass().getName());
        Statistics statistics = new Statistics();
        GPUService gpuService = new GPUService("test",gpu,statistics);
        Thread gput = new Thread(gpuService);
        gput.start();
        CPUService cpuService = new CPUService("t",cpu,statistics);
        Thread cput = new Thread(cpuService);
        cput.start();
//        ConferenceService conferenceService = new ConferenceService("cc",0);
//        Thread conf = new Thread(conferenceService);
//        conf.start();
    }
}
