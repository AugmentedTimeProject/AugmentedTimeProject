TimerExperiments can be imported into Eclipse (JRE7) or simply compiled/run from command line (explained below). 
There are 9 java source files. The main file is LogicalTime.java which initializes the simulator (Simulator.java)
and pokes the processors (Processor.java) to iterate in a loop. There are parameters you can set in LogicalTime.java
to tune the simulation, and results are printed out into a file.

Command line:
-------------
To compile source code execute 'javac LogicalTime.java'
To run the code execute 'java LogicalTime'

Simulation parameters to change (in LogicalTime.java):
------------------------------------------------------
epsilon - 10, 20, 50, 100, etc.
numProcessors - 4, 8, 16, etc.
sendProbability - anywhere from 0.0 to 1.0, this represents the probability that a processor 
	will send/receive messages if left to chance (not forced or blocked)
createProbability - anywhere from 0.0 to 1.0, this represents the probability that a processor 
	will create a new send message if left to chance (not forced or blocked)
sendLag/receiveLag - 1, etc., this is the number of cycles to wait before sending/receiving messages
update1 - true/false, this chooses which LC update algorithm to use
	TRUE means to use algorithm #1 (without count)
	FALSE means to use algorithm #2 (with count)
filedir - path to the directory where you want the resulting data file to be created
