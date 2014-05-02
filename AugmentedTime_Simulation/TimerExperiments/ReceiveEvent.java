
public class ReceiveEvent extends Event {
	
	int mSenderProcessorId, mSenderProcessorLogicalClock, mSenderProcessorCount;
	
	/**
	 * Create a new Receive Event
	 */
	ReceiveEvent(int id, int parentProcessorId, int executeTime, int senderProcessorId, int senderProcessorLogicalClock, int senderProcessorCount) {
        mId = id;
		mParentProcessorId = parentProcessorId;
		mExecuteTime = executeTime;
		mSenderProcessorId = senderProcessorId;
		mSenderProcessorLogicalClock = senderProcessorLogicalClock;
		mSenderProcessorCount = senderProcessorCount;
    }
	
	/**
	 * Get this event's logical clock
	 */
	int getLogicalClock() {
		return mSenderProcessorLogicalClock;
	}
	
	/**
	 * Get this event's count
	 */
	int getCount() {
		return mSenderProcessorCount;
	}
	
	/**
	 * Execute a receive
	 */
    void execute(Simulator s) {
    	// Do nothing
    }
    
}
