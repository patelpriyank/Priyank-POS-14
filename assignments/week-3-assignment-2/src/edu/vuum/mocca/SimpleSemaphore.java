package edu.vuum.mocca;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject (which is accessed via a Condition). It must
 *        implement both "Fair" and "NonFair" semaphore semantics,
 *        just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a Lock to protect the critical section.
     */
    // TODO - you fill in here
	ReentrantLock reEnLock;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
	Condition permitsAvailable, noPermitsAvailable;
	
    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	private int _totalPermitsAvailable = 0;
	private boolean _fair = false;
	private volatile int _permitsInUse = 0;
	
	public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	_totalPermitsAvailable = permits;
    	_fair = fair;
    	_permitsInUse = 0;
    	
    	reEnLock = new ReentrantLock(_fair);
    	permitsAvailable = reEnLock.newCondition();
    	noPermitsAvailable = reEnLock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
	public void acquire() throws InterruptedException {
        // TODO - you fill in here.
    	reEnLock.lockInterruptibly();
    	try
    	{	
    		while(_permitsInUse == _totalPermitsAvailable)
    			permitsAvailable.await();
    		
    		_permitsInUse++;
    		noPermitsAvailable.signal();
    	}
    	finally{
    		reEnLock.unlock();
    	}
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
    	// TODO - you fill in here.
    	reEnLock.lock();
    	try
    	{	
    		while(_permitsInUse == _totalPermitsAvailable)
    			permitsAvailable.awaitUninterruptibly();
    		
    		_permitsInUse++;
    		noPermitsAvailable.signal();
    	}
    	finally{
    		reEnLock.unlock();
    	}
    }

    /**
     * Return one permit to the semaphore.
     */
    public void release() {
        // TODO - you fill in here.
    	reEnLock.lock();
    	try
    	{	
    		while(_permitsInUse == 0)
    			noPermitsAvailable.awaitUninterruptibly();
    		
    		_permitsInUse--;
    		permitsAvailable.notify();
    	}
    	finally{
    		reEnLock.unlock();
    	}
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.
        return (_totalPermitsAvailable - _permitsInUse);
    }
}
