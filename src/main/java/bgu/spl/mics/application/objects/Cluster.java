package bgu.spl.mics.application.objects;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

import bgu.spl.mics.MessageBusImpl;
import bgu.spl.mics.application.services.GPUService;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {

	private LinkedList<GPU> GPUvector;
	private Vector<CPU> CPUvector;

	/**
     * Retrieves the single instance of this class.
     */
	private static Cluster instance = null;

	private Cluster(LinkedList<GPU> v1, Vector<CPU> v2) {
		GPUvector = v1;
		CPUvector = v2;
	}

	public static synchronized Cluster getInstance(LinkedList<GPU> v1, Vector<CPU> v2) {
		if(instance == null)
			instance = new Cluster(v1, v2);
		return instance;
	}

	public CPU getCPU() {
		return CPUvector.firstElement();
	}

	public GPU getGPU() {
		return GPUvector.getFirst();
	}

	public void SendBatchCpu (DataBatch dataBatch) {
		int minindex = 0;
		for (int i = 1; i < CPUvector.size(); i++) {
			if(CPUvector.get(i).getQsize() < CPUvector.get(i - 1).getQsize()) {
				minindex = i;
			}
		}
		CPUvector.get(minindex).sendBatchToCPU(dataBatch);

	}

	public void SendBatchtoGPU(DataBatch dataBatch) {
		GPU gpu = dataBatch.getWho_sent();
		gpu.reciveFromCPU(dataBatch);

	}
//	public void addModelToStats(Model m) {
//		statistics.addTrainedModel(m);
//	}
//
//	public void addTotalDataBatchProcessedByCPU() {
//		statistics.addTotalDataBatchProcessedByCPU();
//	}
//
//	public void addTotalcputicks() {
//		statistics.addTotalcputicks();
//	}
//
//	public void addTotalgputicks() {
//		statistics.addTotalgputicks();
//	}




}
