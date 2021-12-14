package bgu.spl.mics.application;

import bgu.spl.mics.Future;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.CPUService;
import bgu.spl.mics.application.services.ConferenceService;
import bgu.spl.mics.application.services.GPUService;
import bgu.spl.mics.example.services.ExampleMessageSenderService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.io.FileReader;
import bgu.spl.mics.application.objects.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) {
//        GPU gpu = new GPU("RTX3090","test");
//        CPU cpu = new CPU(32,"name");
//        LinkedList<GPU> gpuLinkedList = new LinkedList<>();
//        gpuLinkedList.add(gpu);
//        Vector<CPU> cpuVector = new Vector<>();
//        cpuVector.add(cpu);
//        Cluster cluster = Cluster.getInstance(gpuLinkedList,cpuVector);
//        gpu.setCluster(cluster);
//        cpu.setCluster(cluster);
//        System.out.println(cpu.getClass().getName());
//        Statistics statistics = new Statistics();
//        GPUService gpuService = new GPUService("test",gpu,statistics);
//        Thread gput = new Thread(gpuService);
//        gput.start();
//        CPUService cpuService = new CPUService("t",cpu,statistics);
//        Thread cput = new Thread(cpuService);
//        cput.start();
//        ConferenceService conferenceService = new ConferenceService("cc",0);
//        Thread conf = new Thread(conferenceService);
//        conf.start();

        File input = new File("/home/daniel/IdeaProjects/assignment2/example_input.json");
        List<Student> Students = null;
        try {
            JsonElement fileEle = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObj = fileEle.getAsJsonObject();

            //extract tick and duration
            long tick = fileObj.get("TickTime").getAsLong();
            long duration = fileObj.get("Duration").getAsLong();


            //process all Students
            JsonArray JsonArrayOfStudent = fileObj.get("Students").getAsJsonArray();
            Students = new ArrayList<>();
            for (JsonElement StudentElement : JsonArrayOfStudent) {
                //get the Json Object
                JsonObject StudentJsonObject = StudentElement.getAsJsonObject();
                JsonArray JsonArrayOfModels = StudentJsonObject.get("models").getAsJsonArray();
                //extract data
                String name = StudentJsonObject.get("name").getAsString();
                String department = StudentJsonObject.get("department").getAsString();
                String status = StudentJsonObject.get("status").getAsString();
                Student student = new Student(name, department, status);
                for (JsonElement ModelsElement : JsonArrayOfModels) {
                    JsonObject ModelsJsonObjects = ModelsElement.getAsJsonObject();
                    //extract data
                    String ModelName = ModelsJsonObjects.get("name").getAsString();
                    String ModelType = ModelsJsonObjects.get("type").getAsString();
                    int ModelSize = ModelsJsonObjects.get("size").getAsInt();
                    Data data = new Data(ModelType, ModelSize, null);
                    Model model = new Model(student, data, ModelName);
                    data.setModel(model);
                    Students.add(student);
                }
            }


        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }


    }
    }

