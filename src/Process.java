public class Process 
{
	private PROCESS_STATE STATE;
	private int P_ID;
	private int CPU_BURST;
	private int IO_BURST;
	private int PRIORITY;
	private int PERIOD;
	private int WAIT_TIME;
	
	//Constructor: arr = array with the 5 values in order:
	//P_ID, CPU_BURST, IO_BURST, PRIORITY, PERIOD
	public Process( int[] arr )
	{
		P_ID = arr[ 0 ];
		CPU_BURST = arr[ 1 ];
		IO_BURST = arr[ 2 ];
		PRIORITY = arr[ 3 ];
		PERIOD = arr[ 4 ];
		
		WAIT_TIME = 0;
		STATE = PROCESS_STATE.WAITING_CPU;
	}
	
	public int getP_ID() //Accessor for P_ID
	{
		return P_ID;
	}
	
	public int getCPU_BURST() //Accessor for CPU_BURST
	{
		return CPU_BURST;
	}
	
	public int getIO_BURST() //Accessor for IO_BURST
	{
		return IO_BURST;
	}
	
	public int getPRIORITY() //Accessor for PRIORITY
	{
		return PRIORITY;
	}
	
	public int getPERIOD() //Accessor for PERIOD
	{
		return PERIOD;
	}
	
	public int getWAIT_TIME() //Accessor for WAIT_TIME
	{
		return WAIT_TIME;
	}
	
	public void setSTATE( PROCESS_STATE state1 ) //Mutator for STATE
	{
		STATE = state1;
	}
	
	public void act()
	{
		if( STATE == PROCESS_STATE.ACTIVE_CPU )
			CPU_BURST--;
		else if( STATE == PROCESS_STATE.ACTIVE_IO )
			IO_BURST--;
		else if( STATE == PROCESS_STATE.WAITING_CPU )
			WAIT_TIME++;
	}
}
