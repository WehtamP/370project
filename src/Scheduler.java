import java.util.Collection;
import java.util.LinkedList;

public abstract class Scheduler {
	
	protected Process[] originalProcessList;
	protected Collection<Process> processes;
	protected int unutilizedCycles;
	protected Process lastExecutedProcess;
	protected LinkedList< Process > IO;
	
	
	//MG: Constructs based on an empty collection and an array of processes.
	//MG: This allows individual schedulers to use different types of collections.
	public Scheduler( Collection<Process> eColl, Process procs[] )
	{
		unutilizedCycles = 0;
		IO = new LinkedList< Process >();
		originalProcessList = new Process[ procs.length ];
		
		//MP: Clone the processes in procs so that every other scheduler has separate copies.
		for( int i = 0; i < procs.length; i++ )
			originalProcessList[ i ] = new Process( procs[ i ] );
		
		processes = eColl;
		
		for(Process p: originalProcessList ){ //MP: Loop adds processes in procs to the collection processes
			processes.add(p);
		}
	}
	
	
	//MP: Method that causes all of the processes to act
	public void act( int clock )
	{
		//MP: Prepare for next cycle
		cleanIO();
		cleanProcessor(); 
				
		Process temp = getCurrentProcess();
		if(temp != null)
			lastExecutedProcess = temp;
		for( Process p: processes ){
			p.act();
		}
		for( Process p: IO ){
			p.act();
		}

		if( lastExecutedProcess == null ) //MP: If no process executed, unutilized cycle counter incremented
			unutilizedCycles++;
		
		setFinished(); //MP: Check to see if any processes are finished
	}
	
	//MP: Checks to see if any processes are finished, sets their state to finished
	protected void setFinished()
	{
		Process finishedProcess = null; //MP: Container for finished process
		
		for( Process p: processes )
		{
			if( p != null )
			{
				if( p.getCPU_BURST() == 0 ) //MP: If found finished process, set variables accordingly.
				{
					p.setSTATE( PROCESS_STATE.FINISHED );
					finishedProcess = p;
				}
			}
		}
		
		if( finishedProcess != null ) //MP: Remove finishedprocess from the list of active processes.
			processes.remove( finishedProcess );
	}
	
	//MP: Function that cleans all processes that are not on the CPU by correcting their states.
	protected void cleanIO()
	{
		for( Process p: IO )
		{
			if( p.getIO_BURST() <= 0 ) //MP: If done with IO Burst, start waiting for CPU
			{
				p.setSTATE( PROCESS_STATE.WAITING_CPU );
				IO.remove( p );
				processes.add( p );
			}
		}
	}
	
	//MP: Function that cleans the processor, mostly by putting a new process on if necessary
	protected void cleanProcessor()
	{
		Process p = getCurrentProcess();
		
		if( p != null )
		{
			if( p.getIO_START() == 0 ) //MP: If the process is ready for IO Burst, send to IO.
			{
				p.setSTATE( PROCESS_STATE.ACTIVE_IO );
				processes.remove( p );
				IO.add( p );
			}
		}
		
		Process currentProcess = getCurrentProcess();
		Process nextProcess = chooseNext(); //MP: chooseNext() is abstract method that varies by type of scheduler

		if( currentProcess == nextProcess ) //MP: If chooseNext() says process shouldn't change, do nothing
			return;
		
		else if( currentProcess != null ) //MP: If a different process is chosen, make the current process wait.
			currentProcess.setSTATE( PROCESS_STATE.WAITING_CPU );
		
		nextProcess.setSTATE( PROCESS_STATE.ACTIVE_CPU ); //MP: Put the next process on the CPU
	}
	
	//MP: Returns the current process on the CPU
	protected Process getCurrentProcess()
	{
		for( Process p: processes )
		{
			if( p.getSTATE() == PROCESS_STATE.ACTIVE_CPU )
				return p;
		}
		
		return null;
	}
	
	//MP: Prints the ready queue
	public void printReadyQueue()
	{
		for( Process p: processes )
		{
			Methods.printProcessInfo( p );
		}
	}
	
	//MP: Checks to see if all processes have finished executing, if yes, finished = true
	public boolean isFinished()
	{		
		for( Process p: processes )
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
	
	public void printSnapshot( int clock )
	{
		System.out.println( "==================================================" );
		System.out.println( this.getName() + " Snapshot at Cycle " + clock );
		
	}
	
	//MP: Returns the average wait time
	public float getAverageWaitTime()
	{
		float waitTime = 0;
		
		for( Process p: originalProcessList )
			waitTime += p.getWAIT_TIME() / ( float )this.getNumProcesses(); //MP: Self-explanatory calculation
		
		return waitTime;
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
