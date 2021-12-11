package bgu.spl.mics.application;

import bgu.spl.mics.Future;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.GPU;
import bgu.spl.mics.application.services.GPUService;
import bgu.spl.mics.example.services.ExampleMessageSenderService;

/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) {
        GPU gpu = new GPU("test");
        GPUService gpuService = new GPUService("test",gpu);
        Future<Integer> = gpuService
        Thread a = new Thread(gpuService);
        a.start();
    }
}
