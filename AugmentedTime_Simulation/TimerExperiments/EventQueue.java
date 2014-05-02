import java.util.ArrayList;
import java.util.List;

public class EventQueue extends OrderedSet {
	
	List<ComparableObject> mEvents = new ArrayList<ComparableObject>();
	
	/**
	 * Add the given event into the list in order of timers
	 */
	void insert(ComparableObject comparableObject) {
		int index = 0;
		
		while ((index < mEvents.size()) && (mEvents.get(index).isLessThan(comparableObject))) {
			index++;
		}
		
		mEvents.add(index, comparableObject);
	}
	
	/**
	 * Remove the head event in the list and return it
	 */
	ComparableObject removeFirst() {
		if (mEvents.size() == 0) return null;
		
		ComparableObject comparableObject = mEvents.get(0);
		mEvents.remove(0);
		
		return comparableObject;
	}
	
	/**
	 * Return the number of events in the list
	 */
	int getSize() {
		return mEvents.size();
	}
	
	/**
	 * Return the head event in the list without removing it
	 */
	ComparableObject seeFirst() {
		return mEvents.get(0);
	}
	
}
