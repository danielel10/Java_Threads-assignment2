package bgu.spl.mics;

import java.util.concurrent.TimeUnit;

/**
 * A Future object represents a promised result - an object that will
 * eventually be resolved to hold a result of some operation. The class allows
 * Retrieving the result once it is available.
 * 
 * Only private methods may be added to this class.
 * No public constructor is allowed except for the empty constructor.
 */
public class Future<T> {

	private boolean isdone;
	private T result;
	
	/**
	 * This should be the the only public constructor in this class.
	 */
	public Future() {
		result = null;
		isdone = false;
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved.
     * This is a blocking method! It waits for the computation in case it has
     * not been completed.
     * <p>
	 * @pre: future isn't null
	 * @post: if future has been resolve so result different from null
	 * 		  else we are blocked
     * @return return the result of type T if it is available, if not wait until it is available.
     *
     */
	public synchronized T get() {
		while (!isDone()) {
			try {
				System.out.println("Student is waiting");
				wait();
			} catch (Exception e) {
			}
		}
		System.out.println("Student is not waiting");
		return result;
	}
	
	/**
     * Resolves the result of this Future object.
	 * @pre: result is null
	 * @post: result isn't null
     */
	public synchronized void resolve (T result) {
		System.out.println("GPU resolved");
		this.result = result;
		isdone = true;
		notifyAll();

	}
	
	/**
     * @return true if this object has been resolved, false otherwise
	 *
     */
	public boolean isDone() {
		return isdone;
	}
	
	/**
     * retrieves the result the Future object holds if it has been resolved,
     * This method is non-blocking, it has a limited amount of time determined
     * by {@code timeout}
     * <p>
     * @param timeout 	the maximal amount of time units to wait for the result.
     * @param unit		the {@link TimeUnit} time units to wait.
     * @return return the result of type T if it is available, if not, 
     * 	       wait for {@code timeout} TimeUnits {@code unit}. If time has
     *         elapsed, return null.
	 * @pre: future isn't null
	 * @post: if future has been resolve so result different from null
	 * 		  else we are blocked for a specific timeout, and we get null
     */
	public T get(long timeout, TimeUnit unit) {
		if (result == null) {
			switch (unit) {
				case DAYS:
					try {
						wait(unit.DAYS.toMillis(timeout));
						return result;
					} catch (Exception e) {
					}
				case HOURS:
					try {
						wait(unit.HOURS.toMillis(timeout));
						return result;
					} catch (Exception e) {
					}
				case MINUTES:
					try {
						wait(unit.MINUTES.toMillis(timeout));
						return result;
					} catch (Exception e) {
					}
				case SECONDS:
					try {
						wait(unit.SECONDS.toMillis(timeout));
						return result;
					} catch (Exception e) {
					}
				case MILLISECONDS:
					try {
						wait(timeout);
						return result;
					} catch (Exception e) {
					}
				case NANOSECONDS:
					try {
						wait(TimeUnit.NANOSECONDS.toMillis(timeout));
						return result;
					} catch (Exception e) {
					}
				case MICROSECONDS:
					try {
						wait(TimeUnit.MICROSECONDS.toMillis(timeout));
						return result;
					} catch (Exception e) {
					}

			}
		}
		return result;

	}

}
