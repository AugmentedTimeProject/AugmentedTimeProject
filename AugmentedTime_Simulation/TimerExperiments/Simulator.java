import java.util.ArrayList;
import java.util.List;

class Simulator {
	
	List<Processor> mProcessors;
	int mGlobalClock, mEpsilon, mNumProcessors, mProcessorsInactive, mSendLag, mReceiveLag;
	double mSendProbability, mCreateProbability;
	boolean mUpdate1;
	public static int eventCount;
	
	/**
	 * Get access to a particular processor
	 */
	Processor getProcessor(int index) {
		return mProcessors.get(index);
	}
	
	/**
	 * Initialize the simulator by setting counts and creating processors
	 */
	void initialize (int givenNumProcessors , int givenEpsilon, double givenSendProbability, double givenCreateProbability, 
			int givenSendLag, int givenReceiveLag, boolean givenUpdate1) {
		mProcessors = new ArrayList<Processor>();
		mProcessorsInactive = 0;
		mEpsilon = givenEpsilon;
		mGlobalClock = 0;
        eventCount = 0;
        mSendProbability = givenSendProbability;
        mCreateProbability = givenCreateProbability;
        mSendLag = givenSendLag;
        mReceiveLag = givenReceiveLag;
        mUpdate1 = givenUpdate1;
        mNumProcessors = givenNumProcessors;
        
        for (int index = 0; index < mNumProcessors; index++) {
        	mProcessors.add(new Processor(index));
        }
    }
	
	/**
	 * Get the simulator's current global clock value
	 */
	int getGlobalClock() {
		return mGlobalClock;
	}
	
	/**
	 * Update the simulator's global clock by 1
	 */
	void incrementGlobalClock() {
		mGlobalClock++;
	}
	
	/**
	 * Get the simulator's epsilon value
	 * @return
	 */
	int getEpsilon() {
		return mEpsilon;
	}
	
	/**
	 * Get the number of processors that are currently inactive
	 */
	int getInactiveCount() {
		return mProcessorsInactive;
	}
	
	/**
	 * Reset the number of inactive processors to zero
	 */
	void resetInactiveCount() {
		mProcessorsInactive = 0;
	}
	
	/**
	 * Update the number of inactive processors by 1 
	 */
	void incrementInactiveCount() {
		mProcessorsInactive++;
	}
	
	/**
	 * Get the simulator's probability for sending/receiving an event
	 */
	double getSendProbability() {
		return mSendProbability;
	}
    
	/**
	 * Get the simulator's probability for creating an event
	 */
	double getCreateProbability() {
		return mCreateProbability;
	}
	
	/**
	 * Get the simulator's transmission lag for sending
	 */
	int getSendLag() {
		return mSendLag;
	}
	
	/**
	 * Get the simulator's transmission lag for receiving
	 */
	int getReceiveLag() {
		return mReceiveLag;
	}
	
	/**
	 * Get whether to execute update algorithm 1 or 2
	 */
	boolean getUpdate1() {
		return mUpdate1;
	}
	
	/**
	 * Get the simulator's number of processors
	 */
	int getNumProcessors() {
		return mNumProcessors;
	}
}
