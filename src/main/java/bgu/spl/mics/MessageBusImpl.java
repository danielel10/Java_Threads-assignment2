package bgu.spl.mics;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private Map<Event,Future> futureEventMap;
	private Map<Class<? extends Event>,LinkedList<MicroService>> EventsMap;
	private Map<Class<? extends Broadcast>,Vector<MicroService>> BroadcastMap;
	private Map<MicroService, BlockingQueue<Message>> MicroserivesQ;
	private Object lock;
	private Object locksubevent;
	private Object locksubbroadcast;


	private static MessageBusImpl instance = null;

	//singelton creation
	private <T> MessageBusImpl() {
		lock = new Object();
		locksubevent = new Object();
		locksubbroadcast = new Object();
		futureEventMap = new ConcurrentHashMap<Event, Future>();
		EventsMap = new ConcurrentHashMap<>();
		BroadcastMap = new ConcurrentHashMap<>();
		MicroserivesQ = new ConcurrentHashMap<>();
	}

	public static synchronized MessageBusImpl getInstance() {
		if(instance == null)
			instance = new MessageBusImpl();
		return instance;
	}



	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
		synchronized (locksubevent) {
			LinkedList<MicroService> vector = EventsMap.get(type);
			if(vector == null) {
				LinkedList<MicroService> nvector = new LinkedList<MicroService>();
				nvector.add(m);
				EventsMap.put(type,nvector);
			}
			else {
				vector.add(m);
			}
		}
	}

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
		synchronized (locksubbroadcast) {
			Vector<MicroService> vector = BroadcastMap.get(type);
			if(vector == null) {
				Vector<MicroService> nvector = new Vector<MicroService>();
				nvector.add(m);
				BroadcastMap.put(type,nvector);
			}
			else {
				vector.add(m);
			}
		}

	}

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future<T> givenFuture = futureEventMap.get(e);
		givenFuture.resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if (BroadcastMap.get(b.getClass()) != null) {
			for (MicroService m: BroadcastMap.get(b.getClass())) {
				if(MicroserivesQ.get(m) != null) {
					MicroserivesQ.get(m).add(b);
				}
			}
		}
	}

	
	@Override
	public synchronized  <T> Future<T> sendEvent(Event<T> e) {
		Future<T> tosend = new Future<T>();
		if (EventsMap.get(e.getClass()) != null) {

			futureEventMap.put(e,tosend);
			LinkedList<MicroService> list = EventsMap.get(e.getClass());
			MicroService m = list.getFirst();
			if (m != null) {
				MicroserivesQ.get(list.getFirst()).add(e);
				list.addLast(list.getFirst());
				list.removeFirst();
				return tosend;
			}
			else {
				tosend.resolve(null);
				return null;
			}


		}
		else {
			tosend.resolve(null);
			return null;		}

	}

	@Override
	public void register(MicroService m) {
		MicroserivesQ.put(m, new LinkedBlockingQueue<>());
	}

	@Override
	public void unregister(MicroService m) {
		BlockingQueue<Message> messages = MicroserivesQ.remove(m);
		for (Message message: messages) {
			if (message instanceof Event) {
				Future future = futureEventMap.remove(message);
				future.resolve(0);
			}
			LinkedList<MicroService> microServices = EventsMap.get(message);
			if (microServices != null) {
				microServices.remove(m);
			}
		}
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		if(MicroserivesQ.get(m) == null) {
			throw new IllegalStateException();
		}
		return MicroserivesQ.get(m).take();
	}


	/**
	 * our function
	 */

	@Override
	public boolean IsEventSub(Event type, MicroService m) {
		if (EventsMap.get(type) != null ) {
			return EventsMap.get(type).contains(m);
		}
		else {
			return false;
		}
	}

	@Override
	public boolean IsBroadcastSub(Broadcast type, MicroService m) {
		if (BroadcastMap.get(type) != null ) {
			return BroadcastMap.get(type).contains(m);
		}
		else {
			return false;
		}
	}

	@Override
	public <T> boolean IsFutureAdded(Future<T> f) {
		return futureEventMap.containsValue(f);
	}

	@Override
	public boolean IsBroadcastRecived(Broadcast type) {
		return BroadcastMap.containsValue(type);
	}

	@Override
	public boolean IsFuturecomleted(Event e) {
		return futureEventMap.get(e).isDone();
	}

	@Override
	public boolean IsRegisterd(MicroService m) {
		return MicroserivesQ.containsKey(m);
	}

	@Override
	public boolean IsUnregistered(MicroService m) {
		return !MicroserivesQ.containsKey(m);
	}

	@Override
	public boolean HaveRecivedMessage(MicroService m) {
		boolean result = false;
		for (Map.Entry<Class<? extends Event>,LinkedList<MicroService>> e: EventsMap.entrySet()) {
			LinkedList list = e.getValue();
			result = list.contains(m);
			if (result) {
				return result;
			}
		}
		return result;
	}
}
