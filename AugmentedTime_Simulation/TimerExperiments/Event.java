
abstract class Event implements ComparableObject {
	
	int mId, mParentProcessorId, mExecuteTime;
	
	/**
	 * If my timer is less than the timer of the given event return true, otherwise return false
	 */
    public boolean isLessThan(ComparableObject comparableObject) {
    	Event givenEvent = (Event) comparableObject;
        
    	return (mExecuteTime < givenEvent.getExecuteTime());
    }
    
	abstract void execute(Simulator simulator);
	
	/**
	 * Get the time that the event must execute
	 */
	public int getExecuteTime() {
		return mExecuteTime;
	}
		
}
