package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.messages.TerminateBroadcast;
import bgu.spl.mics.application.messages.TickBroadcast;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link TickBroadcast}.
 * This class may not hold references for objects which it is not responsible for.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{
	private long duration;
	private long mili;
	private Timer timer;
	private TimerTask timerTask;
	private TimerTask terminatetask;
	private TickBroadcast tickBroadcast;
	private int time;

	public TimeService(long duration, long howmuchmilisecondforatick) {
		super("Timeservice");
		this.duration = duration;
		mili = howmuchmilisecondforatick;
		timer = new Timer();
		tickBroadcast = new TickBroadcast();
		time = 0;
		timerTask = new TimerTask() {
			public void run() {
				System.out.println(time);
				sendBroadcast(tickBroadcast);
				time ++;
			}
		};
		terminatetask = new TimerTask() {
			public void run() {
				sendBroadcast(new TerminateBroadcast());
				System.out.println("terminnating");
				timer.cancel();
			}
		};

	}

	@Override
	protected void initialize() {
		timer.scheduleAtFixedRate(timerTask,0,mili);
		timer.schedule(terminatetask,duration);
		terminate();

	}

}
