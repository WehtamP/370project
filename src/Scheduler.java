import java.util.Collection;

public abstract class Scheduler {
	
	protected Process[] originalProcessList;
	protected Collection<Process> processes;
	protected boolean finished;
	protected int unutilizedCycles;
	
	
	//MG: Constructs based on an empty collection and an array of processes.
	//MG: This allows individual schedulers to use different types of collections.
	public Scheduler( Collection<Process> eColl, Process procs[] )
	{
		unutilizedCycles = 0;
		originalProcessList = procs;
		processes = eColl;
		
		for(Process p: procs){
			processes.add(p);
		}
		finished = false;
	}
	
	
	public void act( int clock )
	{
		clean();
		checkProcessor();
		for( Process p: processes )
			p.act();

		
		Process pr = getCurrentProcess();
		if( pr == null )
		{
			unutilizedCycles++;
		}
		
		setFinished();
	}
	
	//MP: Checks to see if any processes are finished, sets their state to finished
	protected void setFinished()
	{
		for( Process p: processes )
		{
			if( p != null )
			{
				if( p.getCPU_BURST() == 0 )
					p.setSTATE( PROCESS_STATE.FINISHED );

			}
		}
	}
	
	protected void clean()
	{
		for( Process p: processes )
		{
			if( p.getSTATE() == PROCESS_STATE.ACTIVE_IO && p.getIO_BURST() == 0 )
				p.setSTATE( PROCESS_STATE.WAITING_CPU );
			
			if( p != null )
			{
				if( p.getCPU_BURST() == 0 )
					p.setSTATE( PROCESS_STATE.FINISHED );
				
				if( p.getIO_START() == 0 )
					p.setSTATE( PROCESS_STATE.ACTIVE_IO );
			}
		}
		
		
	}
	
	protected void checkProcessor()
	{
		Process p = getCurrentProcess();
		
		if( p != null )
		{
			if( p.getCPU_BURST() == 0 )
				p.setSTATE( PROCESS_STATE.FINISHED );
			
			if( p.getIO_START() == 0 )
				p.setSTATE( PROCESS_STATE.ACTIVE_IO );
		}
		
		Process currentProcess = getCurrentProcess();
		Process nextProcess = chooseNext();

		if( currentProcess == nextProcess )
			return;
		
		else if( currentProcess != null )
			currentProcess.setSTATE( PROCESS_STATE.WAITING_CPU );
		
		nextProcess.setSTATE( PROCESS_STATE.ACTIVE_CPU );
	}
	
	protected Process getCurrentProcess()
	{
		for( Process p: processes )
		{
			if( p.getSTATE() == PROCESS_STATE.ACTIVE_CPU )
				return p;
		}
		
		return null;
	}
	
	public void printReadyQueue()
	{
		for( Process p: processes )
		{
			Debugging.printProcessInfo( p );
		}
	}
	
	public boolean isFinished()
	{
		finished = true;
		
		for( Process p: processes )
		{
			if( p.getSTATE() != PROCESS_STATE.FINISHED )
				finished = false;
		}
		
		return finished;
	}
	
	public float getAverageWaitTime()
	{
		float waitTime = 0;
		
		for( Process p: originalProcessList )
			waitTime += p.getWAIT_TIME() / ( float )this.getNumProcesses();
		
		return waitTime;
	}
	
	public int getNumProcesses()
	{
		return originalProcessList.length;
	}
	
	public int getUnutilizedCycles()
	{
		return unutilizedCycles;
	}
	
	protected abstract Process chooseNext();
	protected abstract String getName();
}
