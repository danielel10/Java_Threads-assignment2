package bgu.spl.mics.application;

import bgu.spl.mics.Future;
import bgu.spl.mics.application.messages.TrainModelEvent;
import bgu.spl.mics.application.objects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;

import bgu.spl.mics.application.objects.Student;

import java.io.FileReader;
import java.util.*;


/** This is the Main class of Compute Resources Management System application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output a text file.
 */
public class CRMSRunner {
    public static void main(String[] args) {
        File input = new File("/home/daniel/IdeaProjects/assignment2/example_input.json");
        List<Student> Students =  new ArrayList<>();
        List<Model> Models =  new ArrayList<>();
        LinkedList<GPU> gpus =  new LinkedList<>();
        Vector<CPU> cpus = new Vector<>();
        List<ConfrenceInformation> confrenceInformations =  new ArrayList<>();
        try {
            JsonElement fileEle = JsonParser.parseReader(new FileReader(input));
            JsonObject fileObj = fileEle.getAsJsonObject();

            //extract tick and duration
            long tick = fileObj.get("TickTime").getAsLong();
            long duration = fileObj.get("Duration").getAsLong();

            //process all Students
            JsonArray JsonArrayOfStudent = fileObj.get("Students").getAsJsonArray();
            for (JsonElement StudentElement : JsonArrayOfStudent) {
                //get the Json Object
                JsonObject StudentJsonObject = StudentElement.getAsJsonObject();
                JsonArray JsonArrayOfModels = StudentJsonObject.get("models").getAsJsonArray();
                //extract data
                String name = StudentJsonObject.get("name").getAsString();
                String department = StudentJsonObject.get("department").getAsString();
                String status = StudentJsonObject.get("status").getAsString();
                Student student = new Student(name, department, status);
                Students.add(student);
                for (JsonElement ModelsElement : JsonArrayOfModels) {
                    JsonObject ModelsJsonObjects = ModelsElement.getAsJsonObject();
                    //extract data
                    String ModelName = ModelsJsonObjects.get("name").getAsString();
                    String ModelType = ModelsJsonObjects.get("type").getAsString();
                    int ModelSize = ModelsJsonObjects.get("size").getAsInt();
                    Data data = new Data(ModelType, ModelSize, null);
                    Model model = new Model(student, data, ModelName);
                    data.setModel(model);
                    Models.add(model);
                    student.addModel(model);
                }

            }
            int nam = 1;
            //get GPUS
            JsonArray JsonArrayOfGpu = fileObj.get("GPUS").getAsJsonArray();
            for (JsonElement GpuElement : JsonArrayOfGpu) {
                String type = GpuElement.getAsString();
                GPU gpu = new GPU(type,"gpu"+nam);
                gpus.add(gpu);
                nam++;
            }
            nam = 1;
            //get CPUS
            JsonArray JsonArrayOfCpu = fileObj.get("CPUS").getAsJsonArray();
            for (JsonElement CpuElement : JsonArrayOfCpu) {
                int NumOfCpus = CpuElement.getAsInt();
                CPU cpu = new CPU(NumOfCpus,"cpu" + nam);
                cpus.add(cpu);
                nam++;
            }
            nam = 1;
            //get CONFERENCES
            JsonArray JsonArrayOfConferance = fileObj.get("Conferences").getAsJsonArray();
            for (JsonElement ConferanceElement : JsonArrayOfConferance) {
                JsonObject ConferanceJsonObject = ConferanceElement.getAsJsonObject();
                String name = ConferanceJsonObject.get("name").getAsString();
                int date = ConferanceJsonObject.get("date").getAsInt();
                ConfrenceInformation confrenceInformation = new ConfrenceInformation(name,date);
                confrenceInformations.add(confrenceInformation);
            }
            Cluster cluster = Cluster.getInstance(gpus,cpus);
            Statistics statistics = new Statistics();
            nam = 1;
            LinkedList<Thread> threads = new LinkedList<>();
            for (GPU gpu : gpus) {
                gpu.setCluster(cluster);
                GPUService gpuService = new GPUService("gpu"+ nam,gpu,statistics);
                nam++;
                Thread gpuT = new Thread(gpuService);
                gpuT.start();
                threads.add(gpuT);
            }
            nam = 1;
            for (CPU cpu : cpus) {
                cpu.setCluster(cluster);
                CPUService cpuService = new CPUService("cpu"+ nam,cpu,statistics);
                nam++;
                Thread cpuT = new Thread(cpuService);
                cpuT.start();
                threads.add(cpuT);
            }
            TotalConferenceData total = new TotalConferenceData();
            for (ConfrenceInformation confrenceInformation: confrenceInformations) {
                ConferenceService conferenceService = new ConferenceService(confrenceInformation.getName(),confrenceInformation.getDate(),confrenceInformation,total);
                Thread conf = new Thread(conferenceService);
                conf.start();
                threads.add(conf);
            }
            for (Student student: Students) {
                StudentService studentService = new StudentService(student);
                Thread studentT = new Thread(studentService);
                studentT.start();
                threads.add(studentT);
            }
            TimeService timeService = new TimeService(duration,tick);
            Thread timeserviceT = new Thread(timeService);
            timeserviceT.start();

            timeserviceT.join();

            for (Thread t: threads) {
                t.join();
            }


            JsonArray studentsObj = new JsonArray();

            for (Student s: Students) {

                JsonObject sObj = new JsonObject();
                sObj.addProperty("name",s.getName());
                sObj.addProperty("department",s.getDepartment());
                sObj.addProperty("status",s.getStatus());
                sObj.addProperty("publications",s.getPublications());
                sObj.addProperty("papersRead",s.getPapersRead());


                JsonArray modelsObj = new JsonArray();
                for (Model m: s.getModelsforjson()) {
                    if (m.getResult() == "Good" | m.getResult() == "Bad") {
                        JsonObject mObj = new JsonObject();
                        mObj.addProperty("name",m.getName());
                        //data
                        Data data = m.getData();
                        JsonObject dObj = new JsonObject();
                        dObj.addProperty("type",data.getType());
                        dObj.addProperty("size",data.getSize());
                        //
                        mObj.add("data",dObj);
                        mObj.addProperty("status",m.getStatus());
                        mObj.addProperty("results", m.getResult());
                        modelsObj.add(mObj);
                    }
                }
                sObj.add("trainedModels", modelsObj);
                studentsObj.add(sObj);
            }
            JsonObject allstudents = new JsonObject();
            allstudents.add("Students",studentsObj);

            try {
                FileWriter file = new FileWriter("test.json");
                file.write(allstudents.toString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


            for (ConfrenceInformation c: confrenceInformations) {
                c.getName();
                c.getDate();
                c.getModelVector();
                for (Model m: c.getModelVector()) {
                    m.getName();
                    //
                    Data data = m.getData();
                    data.getType();
                    data.getSize();
                    //
                    m.getStatus();
                    m.getResult();
                }

            }

            System.out.println("cpu ticks " + statistics.getTotalcputicks());
            System.out.println("gpu ticks " + statistics.getTotalgputicks());
            System.out.println("batches cpu processed "+ statistics.getTotalDataBatchProcessedByCPU());



        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }



//
    }
    }

