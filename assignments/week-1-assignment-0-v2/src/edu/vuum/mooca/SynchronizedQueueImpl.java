package edu.vuum.mooca;

import java.io.IOException;

/**
 * @class SynchronizedQueueImpl
 *
 * @brief This is where you put your implementation code to to (1)
 *        create, (2) start, (3) interrupt, and (4) wait for the
 *        completion of multiple Java Threads.  This class plays the
 *        role of the "Concrete Class" in the Template Method pattern
 *        and isolates the code written by students from the
 *        underlying SynchronizedQueue test infrastructure.
 *
 *        Make sure to keep all the "TODO" comments in the code below
 *        to make it easy for peer reviewers to find them.
 */
public class SynchronizedQueueImpl extends SynchronizedQueue {
    // TODO - change this to true if you want to see diagnostic
    // output on the console as the test runs.
    static {
        diagnosticsEnabled = true;
    }
	
    protected void createThreads() {
        // TODO - replace the "null" assignments below to create two
        // Java Threads, one that's passed the mProducerRunnable and
        // the other that's passed the mConsumerRunnable.
        mConsumer = new Thread(new ProducerRunnable());
        mProducer = new Thread(new ConsumerRunnable());
    }
    
    protected void startThreads() {
        // TODO - you fill in here to start the threads. More
        // interesting results will occur if you start the
        // consumer first.
    	mProducer.start();
    	mConsumer.start();
    }

    protected void interruptThreads() {
        // TODO - you fill in here to interrupt the threads.
    	if(mConsumer != null)
    		mConsumer.interrupt();
    	
    	if(mProducer != null)
    		mProducer.interrupt();
    }

    protected void joinThreads() throws InterruptedException {
        // TODO - you fill in here to wait for the threads to
        // exit.
    	if(mConsumer != null)
    		mConsumer.join();
    	
    	if(mProducer != null)
    		mProducer.join();
    }
    
    public class ProducerRunnable implements Runnable {

        public void run() {
        	
        	/* I implemented interruption handling through Thread.currentThread().isInterrupted() but below are other options too
        	 * Option 2: I could have also implemented this through boolean 'volatile' properties and checking its value in loop
        	 * Option 3: If ProducerRunnable was implemented by extending from the 'Thread' class, it was sufficient to call isInterrupted instead of Thread.currentThread().isInterrupted() 
        	 */
        	while (!Thread.currentThread().isInterrupted()) {
                try {
                	System.out.println("Hello from a Producer!");                	
                } catch (Exception ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
    
    public class ConsumerRunnable implements Runnable {

        public void run() {
        	/* I implemented interruption handling through Thread.currentThread().isInterrupted() but below are other options too
        	 * Option 2: I could have also implemented this through boolean 'volatile' properties and checking its value in loop
        	 * Option 3: If ProducerRunnable was implemented by extending from the 'Thread' class, it was sufficient to call isInterrupted instead of Thread.currentThread().isInterrupted() 
        	 */
        	while (!Thread.currentThread().isInterrupted()) {
                try {
                	System.out.println("Hello from a Consumer!");                	
                } catch (Exception ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}            
