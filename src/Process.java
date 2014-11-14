public class Process 
{
	private PROCESS_STATE STATE;
	private int P_ID;
	private int CPU_BURST;
	private int IO_BURST;
	private int PRIORITY;
	private int PERIOD;
	private int WAIT_TIME;
	private int IO_START;
	
	
	//MP: Constructor: arr = array with the 5 values in order:
	//MP: P_ID, CPU_BURST, IO_BURST, PRIORITY, PERIOD
	public Process( int[] arr )
	{
		P_ID = arr[ 0 ];
		CPU_BURST = arr[ 1 ];
		IO_BURST = arr[ 2 ];
		PRIORITY = arr[ 3 ];
		PERIOD = arr[ 4 ];
		IO_START = CPU_BURST / 2;
		
		if( IO_BURST == 0 )
			IO_START = -1;
		
		WAIT_TIME = 0;
		STATE = PROCESS_STATE.WAITING_CPU;
	}
	
	public int getP_ID() //MP: Accessor for P_ID
	{
		return P_ID;
	}
	
	public int getCPU_BURST() //MP: Accessor for CPU_BURST
	{
		return CPU_BURST;
	}
	
	public int getIO_BURST() //MP: Accessor for IO_BURST
	{
		return IO_BURST;
	}
	
	public int getPRIORITY() //MP: Accessor for PRIORITY
	{
		return PRIORITY;
	}
	
	public int getPERIOD() //MP: Accessor for PERIOD
	{
		return PERIOD;
	}
	
	public int getWAIT_TIME() //MP: Accessor for WAIT_TIME
	{
		return WAIT_TIME;
	}
	
	public PROCESS_STATE getSTATE() //MG: Accessor for STATE
	{
		return STATE;
	}
	
	
	public int getIO_START()
	{
		return IO_START;
	}
	
	public void setSTATE( PROCESS_STATE state1 ) //MP: Mutator for STATE
	{
		STATE = state1;
	}
	
	public void act()
	{
		if( IO_START == 0 )
		{
			IO_START--;
		}
		
		if( STATE == PROCESS_STATE.ACTIVE_CPU )
		{
			CPU_BURST--;
			IO_START--;
		}
		
		if( STATE == PROCESS_STATE.ACTIVE_IO )
		{
			IO_START--;
			IO_BURST--;
		}
		
		if( STATE == PROCESS_STATE.WAITING_CPU )
			WAIT_TIME++;

	}	
}
