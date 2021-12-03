package bgu.spl.mics;

import java.util.*;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private  Map<Future,Event> futureEventMap;
	private LinkedList<LinkedList<Event>> EventsList;
	private Vector<Queue> microservicesQ;

	private static MessageBusImpl instance = null;

	//singelton creation
	private MessageBusImpl() {
		//TODO - add constractor
	}

	public static synchronized MessageBusImpl getInstance() {
		if(instance == null)
			instance = new MessageBusImpl();
		return instance;
	}



	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendBroadcast(Broadcast b) {
		// TODO Auto-generated method stub

	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void register(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unregister(MicroService m) {
		// TODO Auto-generated method stub

	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * our function
	 */

	@Override
	public boolean IsEventSub(Event type, MicroService m) {
		return true;
	}

	@Override
	public boolean IsBroadcastSub(Broadcast type, MicroService m) {
		return true;
	}

	@Override
	public <T> boolean IsFutureAdded(Future<T> f) {
		//TODO - we will search with the id of object
		return false;
	}

	@Override
	public boolean IsBroadcastRecived(Broadcast type) {
		return false;
	}

	@Override
	public boolean IsFuturecomleted(Event e) {
		return false;
	}

	@Override
	public boolean IsRegisterd(MicroService m) {
		return false;
	}

	@Override
	public boolean IsUnregistered(MicroService m) {
		return false;
	}

	@Override
	public boolean HaveRecivedMessage(MicroService m) {
		return false;
	}
}
