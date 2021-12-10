package bgu.spl.mics;

import java.util.*;


/**
 * The {@link MessageBusImpl class is the implementation of the MessageBus interface.
 * Write your implementation here!
 * Only private fields and methods can be added to this class.
 */
public class MessageBusImpl implements MessageBus {

	private Map<Event,Future> futureEventMap;
	private Map<Class<? extends Event>,LinkedList<MicroService>> EventsMap;
	private Map<Class<? extends Broadcast>,Vector<MicroService>> BroadcastMap;
	private Map<MicroService,Queue<Message>> MicroserivesQ;
	Object lock = new Object();


	private static MessageBusImpl instance = null;

	//singelton creation
	private <T> MessageBusImpl() {
		futureEventMap = new HashMap<Event,Future>();
		EventsMap = new HashMap<Class<? extends Event>,LinkedList<MicroService>>();
		BroadcastMap = new HashMap<Class<? extends Broadcast>,Vector<MicroService>>();
		MicroserivesQ = new HashMap<MicroService ,Queue<Message>>();
	}

	public static synchronized MessageBusImpl getInstance() {
		if(instance == null)
			instance = new MessageBusImpl();
		return instance;
	}



	@Override
	public <T> void subscribeEvent(Class<? extends Event<T>> type, MicroService m) {
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

	@Override
	public void subscribeBroadcast(Class<? extends Broadcast> type, MicroService m) {
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

	@Override
	public <T> void complete(Event<T> e, T result) {
		Future<T> givenFuture = futureEventMap.get(e);
		givenFuture.resolve(result);
	}

	@Override
	public void sendBroadcast(Broadcast b) {
		if (BroadcastMap.get(b) != null) {
			for (MicroService m: BroadcastMap.get(b)) {
				if(MicroserivesQ.get(m) != null) {
					MicroserivesQ.get(m).add(b);
				}
			}
		}
	}

	
	@Override
	public <T> Future<T> sendEvent(Event<T> e) {
		Future<T> tosend = new Future<T>();
		if (EventsMap.get(e) != null) {
			futureEventMap.put(e,tosend);
			synchronized (lock) {
				//here we check is the Q for the given event is empty or not
				while(MicroserivesQ.get(EventsMap.get(e).getFirst()) == null & !EventsMap.get(e).isEmpty()) {
					EventsMap.get(e).removeFirst();
				}
				LinkedList<MicroService> list = EventsMap.get(e);
				MicroserivesQ.get(list.getFirst()).add(e);
				list.addLast(list.getFirst());
				list.removeFirst();
				notifyAll();
				return tosend;
			}
		}
		else {
			return null;
		}

	}

	@Override
	public void register(MicroService m) {
		MicroserivesQ.put(m, new LinkedList<Message>());
	}

	@Override
	public void unregister(MicroService m) {
		MicroserivesQ.remove(m);
	}

	@Override
	public Message awaitMessage(MicroService m) throws InterruptedException {
		if(MicroserivesQ.get(m) == null) {
			throw new IllegalStateException();
		}
		while (MicroserivesQ.get(m).isEmpty()) {
			wait();
		}
		return MicroserivesQ.get(m).remove();
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
