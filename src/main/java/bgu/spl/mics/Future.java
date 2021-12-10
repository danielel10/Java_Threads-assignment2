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
		//TODO: implement this.
		return null;
	}
	
	/**
     * Resolves the result of this Future object.
	 * @pre: result is null
	 * @post: result isn't null
     */
	public void resolve (T result) {
		//TODO: implement this.
	}
	
	/**
     * @return true if this object has been resolved, false otherwise
	 *
     */
	public boolean isDone() {
		//TODO: implement this.
		return false;
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
		//TODO: implement this.
		return null;
	}

}
