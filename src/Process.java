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
	private int initP;
	
	
	//MP: Constructor: arr = array with the 5 values in order:
	//MP: P_ID, CPU_BURST, IO_BURST, PRIORITY, PERIOD
	public Process( int[] arr )
	{
		//MP: Set Process values to corresponding array values
		P_ID = arr[ 0 ];
		CPU_BURST = arr[ 1 ];
		IO_BURST = arr[ 2 ];
		PRIORITY = arr[ 3 ];
		PERIOD = arr[ 4 ];
		initP = PERIOD;
		IO_START = CPU_BURST / 2; //MP: IO bursts start after 1/2 CPU burst done.
		
		if( IO_BURST == 0 ) //MP: If no IO_BURST, set IO_START to -1
			IO_START = -1;
		
		WAIT_TIME = 0;
		STATE = PROCESS_STATE.WAITING_CPU;
	}
	
	//MP: Process constructor for cloning
	public Process ( Process p ) 
	{
		P_ID = p.getP_ID();
		CPU_BURST = p.getCPU_BURST();
		IO_BURST = p.getIO_BURST();
		PRIORITY = p.getPRIORITY();
		PERIOD = p.getPERIOD();
		initP = PERIOD;
		
		IO_START = CPU_BURST / 2; //MP: IO bursts start after 1/2 CPU burst done.
		
		if( IO_BURST == 0 ) //MP: If no IO_BURST, set IO_START to -1
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
	
	
	public int getIO_START() //MP: Accessor for IO_START
	{
		return IO_START;
	}
	
	public int getDL()       //MG: Returns number of cycles before it must start processing in order to meet its deadline
	{
		if(IO_START >= 1)
			return PERIOD - IO_START;
		
		return PERIOD - CPU_BURST;
	}
	
	public void setSTATE( PROCESS_STATE state1 ) //MP: Mutator for STATE
	{
		STATE = state1;
	}
	
	public void act() //MP: Updates process values (WAIT_TIME, IO_BURST, IO_START, CPU_BURST) depending on the state of process when this method is called
	{
		if( IO_START == 0 ) //MP: If the process has started IO Burst, set IO_START to be > 0 so that it doesn't start IO Bursting again.
		{
			IO_START--;
		}
		
		if( STATE == PROCESS_STATE.ACTIVE_CPU ) //MP: If Process is active in CPU, the CPU burst time decreases and the time until starting IO Burst decreases
		{
			CPU_BURST--;
			IO_START--;
			PERIOD--;
		}
		
		if( STATE == PROCESS_STATE.ACTIVE_IO ) //MP: If Process is active in IO, the IO burst time decreases.
		{
			IO_BURST--;
			System.out.println("initP = " + initP);
			PERIOD = initP;
		}
		
		if( STATE == PROCESS_STATE.WAITING_CPU ) //MP: If the process is waiting for the CPU, its total wait time increases.
			{
			WAIT_TIME++;
			PERIOD--;
			}

	}	
}
