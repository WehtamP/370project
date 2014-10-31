public class Simulation 
{
	private Scheduler sch;
	private int AVERAGE_WAIT_TIME;
	private int THROUGHPUT;
	private int CPU_UTILIZATION;
	private int TURNAROUND_TIME;
	private int CLOCK;
	
	public Simulation( Scheduler s )
	{
		sch = s;
		CLOCK = 0;
	}
	
	public void run() //Start Simulation
	{
		int numSteps = 0;
		
		while( !sch.isFinished() ) //MP: Run simulation until all processes are finished
		{
			sch.act( CLOCK );
			sch.printReadyQueue();
			System.out.println( "//////////////////////\n" );
			CLOCK++;
			numSteps++;
		}
		
		System.out.println( "Total steps: " + numSteps );
	}
	
	public int getAVERAGE_WAIT_TIME() //MP: Accessor for AVERAGE_WAIT_TIME
	{
		return AVERAGE_WAIT_TIME;
	}
	
	public int getTHROUGHPUT() //MP: Accessor for THROUGHPUT
	{
		return THROUGHPUT;
	}
	
	public int getCPU_UTILIZATION() //MP: Accessor for CPU_UTILIZATION
	{
		return CPU_UTILIZATION;
	}
	
	public int getTURNAROUND_TIME() //MP: Accessor for TURNAROUND TIME
	{
		return TURNAROUND_TIME;
	}
}