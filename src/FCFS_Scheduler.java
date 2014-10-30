public class FCFS_Scheduler extends Scheduler
{

	public FCFS_Scheduler( Process[] ar ) 
	{
		super( ar );
	}
	
	public int chooseNext() //MP: Function that determines which process should execute on the CPU.
	{
		for( Process p: procs )
		{
			if( p.getSTATE() == PROCESS_STATE.ACTIVE_CPU )
				return p.getP_ID();
		}
		
		for( Process p: procs )
		{
			if( p.getSTATE() == PROCESS_STATE.WAITING_CPU )
				return p.getP_ID();
		}
		
		return -1;
	}
}
