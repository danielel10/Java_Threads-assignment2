package bgu.spl.mics.application.objects;

import java.util.*;
import bgu.spl.mics.MessageBusImpl;

/**
 * Passive object representing the cluster.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * Add all the fields described in the assignment as private fields.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Cluster {

	private Vector<GPU> GPUvector;
	private Vector<CPU> CPUvector;
	//Statistics!

	/**
     * Retrieves the single instance of this class.
     */
	private static Cluster instance = null;

	private Cluster(Vector<GPU> v1, Vector<CPU> v2) {
		//TODO - add constractor
	}

	public static synchronized Cluster getInstance(Vector<GPU> v1, Vector<CPU> v2) {
		if(instance == null)
			instance = new Cluster(v1, v2);
		return instance;
	}

	public CPU getCPU() {
		return CPUvector.firstElement();
	}

	public GPU getGPU() {
		return GPUvector.firstElement();
	}

	public void SendBatch (DataBatch dataBatch) {

	}


}
