//import java.util.Random;

public class SendEvent extends Event {

	int mRecicpientProcessorId, mParentProcessorLogicalClock, mParentProcessorCount;
	
	/**
	 * Create a new Send Event
	 */
	SendEvent(int id, int parentProcessorId, int executeTime, int recipientProcessorId, int parentProcessorLogicalClock, int parentProcessorCount) {
        mId = id;
		mParentProcessorId = parentProcessorId;
		mExecuteTime = executeTime;
		mRecicpientProcessorId = recipientProcessorId;
		mParentProcessorLogicalClock = parentProcessorLogicalClock;
		mParentProcessorCount = parentProcessorCount;
    }
	
	/**
	 * Create a corresponding receive event
	 */
    void execute(Simulator simulator) {        
    	for (int index = 0; index < simulator.getNumProcessors(); index++) {
    		Processor processor = simulator.getProcessor(index);
        	if (processor.getId() == mRecicpientProcessorId) {
        		
        		// Handle straggler/rusher differently
        		//if (processor.getId() == (simulator.getNumProcessors() - 1)) {
        			
        		//} else {
        			int executeTime =  processor.getPhysicalClock() + 1;//(1 + new Random().nextInt(simulator.getReceiveLag()));
        			ReceiveEvent receiveEvent = new ReceiveEvent(mId, processor.getId(), executeTime, mParentProcessorId, mParentProcessorLogicalClock, mParentProcessorCount);
        			processor.addToInbox(receiveEvent);
        			break;
        		//}
        	}
        }
    }
    
}
