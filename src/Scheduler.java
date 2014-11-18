import java.util.Collection;
import java.util.LinkedList;

public abstract class Scheduler {

	protected Process[] originalProcessList;
	protected Collection<Process> readyQueue;
	protected Process processor; //MP: Process that stores the process on the processor
	protected int unutilizedCycles;
	protected Process lastExecutedProcess;
	protected LinkedList< Process > IO;


	//MG: Constructs based on an empty collection and an array of processes.
	//MG: This allows individual schedulers to use different types of collections.
	public Scheduler( Collection<Process> eColl, Process procs[] )
	{
		unutilizedCycles = 0;
		processor = null;
		IO = new LinkedList< Process >();
		originalProcessList = new Process[ procs.length ];

		//MP: Clone the processes in procs so that every other scheduler has separate copies.
		for( int i = 0; i < procs.length; i++ )
			originalProcessList[ i ] = new Process( procs[ i ] );

		readyQueue = eColl;

		for(Process p: originalProcessList ){ //MP: Loop adds processes in procs to the collection processes
			readyQueue.add(p);
		}
	}


	//MP: Method that causes all of the processes to act
	public void act()
	{
		//MP: Prepare for next cycle
		cleanIO();
		cleanProcessor(); 

		lastExecutedProcess = processor;

		if( processor != null )
			processor.act();
		else //MP: If no process executed, unutilized cycle counter incremented
			unutilizedCycles++;

		for( Process p: readyQueue )
			p.act();

		for( Process p: IO )
			p.act();
		
		setFinished(); //MP: Check to see if any processes are finished
	}

	//MP: Checks to see if any the processor process is finished.
	protected void setFinished()
	{
		if( processor != null )
			if( processor.getCPU_BURST() == 0 )
			{
				processor.setSTATE( PROCESS_STATE.FINISHED );
				processor = null;
			}
	}

	//MP: Function that cleans all processes that are not on the CPU by correcting their states.
	protected void cleanIO()
	{
		LinkedList< Process > list = new LinkedList< Process >();
		for( Process p: IO )
		{
			if( p.getIO_BURST() <= 0 ) //MP: If done with IO Burst, start waiting for CPU
			{
				p.setSTATE( PROCESS_STATE.WAITING_CPU );
				list.add( p ); //MP: Add to temp list to prevent concurrent list modification errors
			}
		}

		for( Process p: list ) //MP: Properly remove from IO and add to process list processes that finished their IO Burst
		{
			IO.remove( p );
			readyQueue.add( p );
		}
	}

	//MP: Function that cleans the processor, mostly by putting a new process on if necessary
	protected void cleanProcessor()
	{
		if( processor != null )
		{
			if( processor.getIO_START() == 0 ) //MP: If the process is ready for IO Burst, send to IO.
			{
				processor.setSTATE( PROCESS_STATE.ACTIVE_IO );
				IO.add( processor );
				processor = null;
			}
		}

		Process nextProcess = chooseNext(); //MP: chooseNext() is abstract method that varies by type of scheduler

		if( processor == nextProcess ) //MP: If chooseNext() says process shouldn't change, do nothing
			return;

		else if( processor != null ) //MP: If a different process is chosen, make the current process wait and add to ready queue
		{
			processor.setSTATE( PROCESS_STATE.WAITING_CPU );
			readyQueue.add( processor );
		}

		if(nextProcess != null)
		{
			nextProcess.setSTATE( PROCESS_STATE.ACTIVE_CPU ); //MP: Put the next process on the CPU
			readyQueue.remove( nextProcess ); //MP: Remove new process from ready queue
			processor = nextProcess;
		}
		else {
			processor = null;
		}
	}

	//MP: Prints the ready queue
	public void printReadyQueue()
	{
		for( Process p: readyQueue )
		{
			Methods.printProcessInfo( p );
		}
	}

	//MP: Checks to see if all processes have finished executing, if yes, finished = true
	public boolean isFinished()
	{		
		if( processor != null )
			return false;

		for( Process p: readyQueue )
		{
			if( p.getSTATE() != PROCESS_STATE.FINISHED )
				return false;
		}

		for( Process p: IO )
		{
			if( p.getSTATE() == PROCESS_STATE.ACTIVE_IO )
				return false;
		}

		return true;
	}

	//MP: Prints a snapshot of what's currently in the CPU
	public void printSnapshot( int clock )
	{	
		boolean exists = false; //MP: Boolean used to determine when to print out none.
		System.out.println( "==================================================" );
		System.out.println( this.getName() + " Snapshot at Cycle " + clock );

		if( processor != null )
			System.out.println( "\nProcess Currently Processing: " + processor.getP_ID() );
		else
			System.out.println( "\nProcess Currently Processing: " + "None" );

		System.out.println( "\nProcesses in Ready Queue");

		for( Process pr: readyQueue )
		{
			System.out.println( ">" + pr.getP_ID() );
			exists = true;
		}
		if( !exists )
			System.out.println( "None" );
		
		System.out.println( "\nProcesses in IO" );
		
		exists = false;
		for( Process pr: IO )
		{
			System.out.println( ">" + pr.getP_ID() );
			exists = true;
		}
		
		if( !exists )
			System.out.println( "None" );
	}

	//MP: Returns the average wait time
	public float getAverageWaitTime()
	{
		float waitTime = 0;

		for( Process p: originalProcessList ) //MP: Sum the wait times
			waitTime += p.getWAIT_TIME();

		return waitTime / ( float )this.getNumProcesses(); //MP: Return the average
	}

	//MP: returns the original number of processes
	public int getNumProcesses()
	{
		return originalProcessList.length;
	}

	//MP: returns the number of unutilized cycles
	public int getUnutilizedCycles()
	{
		return unutilizedCycles;
	}

	//MP: Returns the last executed process
	public Process getLastProcess()
	{
		return lastExecutedProcess;
	}

	protected abstract Process chooseNext(); //MP: Function to return the process that should be selected next
	protected abstract String getName(); //MP: Function to return the name of the scheduler
}
