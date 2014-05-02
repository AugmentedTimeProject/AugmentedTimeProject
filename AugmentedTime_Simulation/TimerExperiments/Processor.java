import java.util.Random;

class Processor {
	
	int mId, mPhysicalClock, mLogicalClock, mCount;
	double mRandom1, mRandom2;
	OrderedSet mInbox, mOutbox;
	
	/**
	 * Initialize a new processor
	 */
	Processor(int id) {
		mInbox = new EventQueue();
		mOutbox = new EventQueue();
		mId = id;
		mPhysicalClock = 0;
		mLogicalClock = 0;
		mCount = 0;
	}
	
	/**
	 * Get this processor's ID
	 */
	int getId() {
		return mId;
	}
	
	/**
	 * Get this processor's physical clock
	 */
	int getPhysicalClock() {
		return mPhysicalClock;
	}
	
	/**
	 * Set this processor's physical clock
	 */
	void setPhysicalClock(int givenPhysicalClock) {
		mPhysicalClock = givenPhysicalClock;
	}
	
	/**
	 * Get this processor's logical clock
	 */
	int getLogicalClock() {
		return mLogicalClock;
	}
	
	/**
	 * Get this processor's count
	 */
	int getCount() {
		return mCount;
	}
	
	/**
	 * Check to see if the processor has any events to receive or send
	 */
	boolean isInactive() {
		if ((mInbox.getSize() == 0) && (mOutbox.getSize() == 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Add a Send Event to the processor's outbox
	 */
	void addToOutbox(SendEvent sendEvent) {
		mOutbox.insert(sendEvent);
	}
	
	/**
	 * Create a new Send Event
	 */
	int createMessage(Simulator simulator, int numProcessors, int eventCount) {
    	// Create a new Send event to send out randomly
    	int executeTime = mPhysicalClock + 1;//(1 + new Random().nextInt(simulator.getSendLag()));
    	int receipientProcessorId = new Random().nextInt(numProcessors);
    	addToOutbox(new SendEvent(eventCount, mId, executeTime, receipientProcessorId, mLogicalClock, mCount));
    	eventCount++;
		
		return eventCount;
	}
	
	/**
	 * Try to execute Receive and Send events
	 */
	void receiveSendMessage(Simulator simulator) {
		/*if (mId == (simulator.getNumProcessors() - 1)) {
			// Handle straggler/rusher differently (force count increment for # of receives)
			int numReceive = 0;
			int msgLogicalClock = 0;
			int msgCount = 0;
			
			while ((mInbox.getSize() > 0) && (mPhysicalClock == ((ReceiveEvent) mInbox.seeFirst()).getExecuteTime())) {
				ReceiveEvent receiveEvent = (ReceiveEvent) mInbox.removeFirst(); 
				receiveEvent.execute(simulator);
				msgLogicalClock = receiveEvent.getLogicalClock();
				msgCount = receiveEvent.getCount();
				numReceive++;
			}
			
			if (numReceive == 1) {
				if (simulator.getUpdate1()) {
					updateAfterReceive1(msgLogicalClock);
				} else {
					updateAfterReceive2(msgLogicalClock, msgCount);
				}
			} else {
				mCount = mCount + numReceive;
			}
		} else {*/
			// If there is a pending receive event and its time is now, receive it
			while ((mInbox.getSize() > 0) && (mPhysicalClock == ((ReceiveEvent) mInbox.seeFirst()).getExecuteTime())) {
				ReceiveEvent receiveEvent = (ReceiveEvent) mInbox.removeFirst(); 
				receiveEvent.execute(simulator);
				if (simulator.getUpdate1()) {
					updateAfterReceive1(receiveEvent.getLogicalClock());
				} else {
					updateAfterReceive2(receiveEvent.getLogicalClock(), receiveEvent.getCount());
				}
			}
				
			// If there is a pending send event and its time is now, send it
			while ((mOutbox.getSize() > 0) && (mPhysicalClock == ((SendEvent) mOutbox.seeFirst()).getExecuteTime())) {
				SendEvent sendEvent = (SendEvent) mOutbox.removeFirst();
				sendEvent.execute(simulator);
				if (simulator.getUpdate1()) {
					updateAfterSend1();
				} else {
					updateAfterSend2();
				}
			}
		//}
		
		mPhysicalClock++;
	}
	
	/**
	 * Increment processor logical clock when sending (algorithm #1)
	 */
	void updateAfterSend1() {
		mLogicalClock = Math.max((mLogicalClock + 1), mPhysicalClock);
	}
	
	/**
	 * Increment processor logical clock when sending (algorithm #2)
	 */
	void updateAfterSend2() {
		int previousLogicalClock = mLogicalClock;
		mLogicalClock = Math.max(previousLogicalClock, mPhysicalClock);
		
		if (mLogicalClock == previousLogicalClock) {
			mCount++;
		} else {
			mCount = 0;
		}
	}
	
	/**
	 * Add a Receive Event to the processor's inbox
	 */
	void addToInbox(ReceiveEvent receiveEvent) {
		mInbox.insert(receiveEvent);
	}
	
	/**
	 * Increment processor logical clock when receiving (algorithm #1)
	 */
	void updateAfterReceive1(int sentMessageClock) {
		mLogicalClock = Math.max(Math.max((mLogicalClock + 1), (sentMessageClock + 1)), mPhysicalClock);
	}
	
	/**
	 * Increment processor logical clock when receiving (algorithm #2)
	 */
	void updateAfterReceive2(int sentMessageClock, int sentMessageCount) {
		int previousLogicalClock = mLogicalClock;
		mLogicalClock = Math.max(Math.max(previousLogicalClock, sentMessageClock), mPhysicalClock);
		
		if ((mLogicalClock == previousLogicalClock) && (mLogicalClock == sentMessageClock)) {
			mCount = Math.max(mCount, sentMessageCount) + 1;
		} else if (mLogicalClock == previousLogicalClock) {
			mCount++;
		} else if (mLogicalClock == sentMessageClock) {
			mCount = sentMessageCount + 1;
		} else {
			mCount = 0;
		}
	}
    
	/**
	 * Process the pending inbox events, pending outbox events, and/or the clock
	 */
    int iterate(Simulator simulator, int numProcessors, int eventCount) {    	
    	// Do bound checking
    	int decision = checkBounds(simulator, numProcessors);
    	if (decision == 1) {
    		// Must send/receive
    		receiveSendMessage(simulator);
    	} else if (decision == 2) {
    		// Not allowed to send/receive
    	} else {
    		// Arbitrarily choose to send/receive
    	    mRandom1 = Math.random();
    		if (mRandom1 < simulator.getSendProbability()) {
    	    	receiveSendMessage(simulator);
    	    	
    	    	// Arbitrarily choose to create a new message
    	    	mRandom2 = Math.random();
    	    	if (mRandom2 < simulator.getCreateProbability()) {
    	    		eventCount = createMessage(simulator, numProcessors, eventCount);
    	    	} else {
    	    		// Do nothing
    	    	}
    	    } else {
    	    	// Do nothing
    	    }
    	}
    	    	
    	return eventCount;
    }
    
    /**
     * Check to see if physical clock is within epsilon
     */
    int checkBounds(Simulator simulator, int numProcessors) {
    	// Check my clock against other processors' clocks to see if I'm within
    	// simulation bounds
    	int decision; // 0 = chance, 1 = force, 2 = block
    	
    	if (mId == (numProcessors - 1)) {
    		// Handle straggler/rusher process
    		decision = 2;
    		int straggling = 0;
    		
    		for (int i = 0; i < (numProcessors - 1); i++) {
    			Processor p = simulator.getProcessor(i);
    			
    			// Straggler
    			/*if ((mPhysicalClock < p.getPhysicalClock()) && 
    					(mPhysicalClock < (p.getPhysicalClock() - (simulator.getEpsilon() * 5)))) {
    				straggling++;
    			}*/
    			
    			// Rusher
    			if ((mPhysicalClock > p.getPhysicalClock()) && 
    					(mPhysicalClock < (p.getPhysicalClock() + (simulator.getEpsilon() * 5)))) {
    				straggling++;
    			}
    		}
    		// Execute only if I'm epsilon (or more) behind every other process
    		if (straggling == (numProcessors - 1)) {
    			decision = 1;
    		}
    	} else {
    		// Handle normal process
    		decision = 0;
    		
    		for (int i = 0; i < (numProcessors - 1); i++) {
    		//for (int i = 0; i < numProcessors; i++) {
    			if (mId == i) {
    				// Skip: don't need to check myself
    			} else {
    				Processor p = simulator.getProcessor(i);
    				if ((mPhysicalClock < p.getPhysicalClock()) && 
    						(mPhysicalClock <= (p.getPhysicalClock() - simulator.getEpsilon()))) {
    					// I'm epsilon (or more) behind another processor => Force send/receive!
    					decision = 1;
    					break;
    				} else if ((mPhysicalClock > p.getPhysicalClock()) && 
    						(mPhysicalClock <= (p.getPhysicalClock() - simulator.getEpsilon()))) {
    					// I'm epsilon (or more) ahead of another processor => Block send/receive!
    					decision = 2;
    					break;
    				} else {
    					// I'm within epsilon bound so leave to chance!
    				}
    			}
    		}
    	}
    	
    	return decision;
    }
    
}
