public class FCFS_Scheduler extends Scheduler
{

	public FCFS_Scheduler( Process[] arr ) 
	{
		super( arr );
	}
	
	protected Process chooseNext() //MP: Function that determines which process should execute on the CPU.
	{
		for( Process p: processes )
		{
			if( p.getSTATE() == PROCESS_STATE.ACTIVE_CPU )
				return p;
		}
		
		for( Process p: processes )
		{
			if( p.getSTATE() == PROCESS_STATE.WAITING_CPU )
				return p;
		}
				
		return null;
	}
}
