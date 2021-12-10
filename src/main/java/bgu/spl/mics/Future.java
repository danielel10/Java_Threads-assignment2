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

	private T result;
	
	/**
	 * This should be the the only public constructor in this class.
	 */
	public Future() {
		result = null;
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
	public T get() {
		while (!isDone()) {
			try {
				wait();
			} catch (Exception e) {
			}
		}
		return result;
	}
	
	/**
     * Resolves the result of this Future object.
	 * @pre: result is null
	 * @post: result isn't null
     */
	public void resolve (T result) {
		this.result = result;
		notifyAll();

	}
	
	/**
     * @return true if this object has been resolved, false otherwise
	 *
     */
	public boolean isDone() {
		return result != null;
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
		if (result != null) {
			return result;
		}
		else {
			switch (unit) {
				case DAYS:
					try {
						Thread.sleep(TimeUnit.DAYS.toMillis(timeout));
						return result;
					}
					catch (Exception e) {}
				case HOURS:
					try {
						Thread.sleep(TimeUnit.HOURS.toMillis(timeout));
						return result;
					}
					catch (Exception e) {}
				case MINUTES:
					try {
						Thread.sleep(TimeUnit.MINUTES.toMillis(timeout));
						return result;
					}
					catch (Exception e) {}
				case SECONDS:
					try {
						Thread.sleep(TimeUnit.SECONDS.toMillis(timeout));
						return result;
					}
					catch (Exception e) {}
				case MILLISECONDS:
					try {
						Thread.sleep(timeout);
						return result;
					}
					catch (Exception e) {}
				case NANOSECONDS:
					try {
						Thread.sleep(TimeUnit.NANOSECONDS.toMillis(timeout));
						return result;
					}
					catch (Exception e) {}
				case MICROSECONDS:
					try {
						Thread.sleep(TimeUnit.MICROSECONDS.toMillis(timeout));
						return result;
					}
					catch (Exception e) {}

			}
			return result;
		}

	}

}
