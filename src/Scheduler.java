public abstract class Scheduler {
	
	protected Process processes[];
	protected boolean finished;
	
	public Scheduler( Process procs[] )
	{
		processes = procs;
		finished = false;
	}
	
	public void act( int clock )
	{
		checkProcessor();		
		for( Process p: processes )
			p.act();
		
		clean( clock );
	}
	
	public void checkProcessor()
	{
		Process currentProcess = getCurrentProcess();
		Process nextProcess = chooseNext();

		if( currentProcess == nextProcess )
			return;
		
		else if( currentProcess != null )
			currentProcess.setSTATE( PROCESS_STATE.WAITING_CPU );
		
		System.out.println( "NEXT: " + nextProcess.getP_ID() );
		nextProcess.setSTATE( PROCESS_STATE.ACTIVE_CPU );
	}
	
	protected void clean( int clock )
	{
		cleanProcesses( clock );
		cleanProcessor();
	}
	
	protected void cleanProcesses( int clock )
	{
		for( Process p: processes )
		{					
			if( p.getIO_START() == 0 )
				p.setSTATE( PROCESS_STATE.ACTIVE_IO );
			
			if( p.getSTATE() == PROCESS_STATE.ACTIVE_IO && p.getIO_BURST() <= 0 )
				p.setSTATE( PROCESS_STATE.WAITING_CPU );
			
			if( p.getSTATE() == PROCESS_STATE.WAITING_IO )
				p.setSTATE( PROCESS_STATE.ACTIVE_IO );
			
			if( p.getSTATE() == PROCESS_STATE.INACTIVE )
				if( p.getP_ID() == clock )
					p.setSTATE( PROCESS_STATE.WAITING_CPU );
		}
	}
	
	protected void cleanProcessor()
	{
		Process p = getCurrentProcess();
		
		if( p != null )
		{
			if( p.getCPU_BURST() == 0 )
				p.setSTATE( PROCESS_STATE.FINISHED );
		}
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
	
	protected abstract Process chooseNext();
}
