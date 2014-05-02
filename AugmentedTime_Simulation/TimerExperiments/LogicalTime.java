import java.io.FileWriter;
import java.io.IOException;

public class LogicalTime extends Simulator {
	
	static FileWriter file;
	//final static int epsilon = 100; //10,20,50,100
	//final static int numProcessors = 16; //4,8,16
	final static double sendProbability = 1.0;
	final static double createProbability = 1.0;
	final static int sendLag = 1; // 1 + rand(0-sendLag)
	final static int receiveLag = 1; // 1 + rand(0-receiveLag)
	final static boolean update1 = false;
	final static String filedir = "C:\\Users\\A27Q6OT\\Documents\\EEDP\\MSU\\Spring 2014\\CSE 812\\project\\fixed_rusher5\\";
	
	/**
	 * Main process loop
	 */
	public static void main(String[] args) {
		// Run the implementation
		run(4,10);
		run(4,20);
		run(4,50);
		run(4,100);
		run(8,10);
		run(8,20);
		run(8,50);
		run(8,100);
		run(16,10);
		run(16,20);
		run(16,50);
		run(16,100);
	}
	
	/**
	 * One run of the logical time algorithm with the given number of processors
	 */
	static void run(int numProcessors, int epsilon) {
		// Initialize the file handler before starting
		try {
			file = new FileWriter(filedir + "epsilon"+ epsilon + "_np" + numProcessors + ".csv", true);
			file.write("Global Clock,");
			for (int i = 0; i < numProcessors; i++) {
				file.write("P" + i + ",");
				if (!update1) file.write("P" + i + "c,");
			}
			file.write("\n");
		} catch (IOException exception) {
			exception.printStackTrace();
		}
				
		// Initialize the simulator
        LogicalTime simulator = new LogicalTime();
        simulator.initialize(numProcessors, epsilon, sendProbability, createProbability, sendLag, receiveLag, update1);
        int maxCount = 0;
        
        // Force rusher's clock initially
        simulator.getProcessor(numProcessors - 1).setPhysicalClock(epsilon * 5);
        
        // Run the simulation until all processors are inactive or time is 100,000 (and some events have occurred)
        while ((eventCount < 1) || ((simulator.getGlobalClock() < 100000) && (simulator.getInactiveCount() != numProcessors))) {
        	simulator.resetInactiveCount();
        	String data = "";
    		
        	// Loop through each processor and perform processing
    		for (int index = 0; index < numProcessors; index++) {
    			Processor processor = simulator.getProcessor(index);
    			eventCount = processor.iterate(simulator, numProcessors, eventCount);
    			
    			//Calculate metrics
    			int diff = processor.getLogicalClock() - processor.getPhysicalClock();
    			if (diff < 0) diff = 0;
    			data = data + diff + ",";
    			if (!update1) {
    				data = data + processor.getCount() + ",";
    				maxCount = Math.max(maxCount, processor.getCount());
    			}
    			
    			// Check to see if processor is currently inactive
    			if (processor.isInactive()) {
    				simulator.incrementInactiveCount();
    			}
    		}
    		
			// Print metrics to file
		    try {
				file.write(simulator.getGlobalClock() + "," + data + "\n");
			} catch (IOException exception) {
				exception.printStackTrace();
			}
        	
    		simulator.incrementGlobalClock();
        }
        
        // Print metrics to screen
        //System.out.println("Max Count is " + maxCount);
        //System.out.println("Event Count is " + eventCount);
        //System.out.println("Last processor clock is " + simulator.getProcessor(numProcessors - 1).getPhysicalClock());
        
		// Close the file handler when done
		try {
	    	file.close();
	    } catch (IOException exception) {
	    	exception.printStackTrace();
	    }
    }
    
}
