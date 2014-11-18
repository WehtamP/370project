import java.util.LinkedList;

public class Simulation 
{
	private Scheduler SCHEDULER;
	private double THROUGHPUT;
	private int TURNAROUND_TIME;
	private int CLOCK;
	private double AVERAGE_WAIT_TIME;
	private double CPU_UTILIZATION;
	private LinkedList<Process> EXECUTION_LIST;
	
	//MP: Constructor for Simulation
	public Simulation( Scheduler s )
	{
		SCHEDULER = s;
		CLOCK = -1;
		EXECUTION_LIST = new LinkedList< Process >();
		EXECUTION_LIST.add( null ); //MP: Add null to the linked list so no errors occur when trying to remove element before any exist.
	}
	
	public void run() //Start Simulation
	{	
		while( !SCHEDULER.isFinished() ) //MP: Run simulation until all processes are finished
		{
			CLOCK++;
			SCHEDULER.act( CLOCK );
			Process p = SCHEDULER.getLastProcess();
			
			if( EXECUTION_LIST.getLast() != p) //MP: If the process on the CPU is not the same as the last process that was on the CPU, add to list.
				EXECUTION_LIST.add( p );	
		}
		
		calcValues();
	}
	
	private void calcValues() //MP: Calculates statistics after running, should be self explanatory
	{
		int totalCycles = CLOCK + 1;
		TURNAROUND_TIME = totalCycles;
		THROUGHPUT = SCHEDULER.getNumProcesses() / ( double )( totalCycles );
		AVERAGE_WAIT_TIME = Math.round( SCHEDULER.getAverageWaitTime() * 100 ) / (double )100;
		CPU_UTILIZATION = ( totalCycles - SCHEDULER.getUnutilizedCycles()) / ( double )( totalCycles );
		
	}
	
	public void generateLog() //MP: Prints out all of the data. NEEDS TO BE CHANGED TO TEXT FILE OUTPUT
	{
		System.out.println( "======================================================" );
		System.out.println( "Final Report for " + SCHEDULER.getName() );
		System.out.println( "CPU execution order for " + SCHEDULER.getName() );
		printExecutionOrder();
		System.out.println( "Throughput for " + SCHEDULER.getName() + " = " + THROUGHPUT);
		System.out.println( "Total Turn-around Time for " + SCHEDULER.getName() + " = " + TURNAROUND_TIME );
		System.out.println( "Average Wait Time for " + SCHEDULER.getName() + " = " + AVERAGE_WAIT_TIME );
		System.out.println( "CPU Utilization for " + SCHEDULER.getName() + " = " + CPU_UTILIZATION * 100 + "%" );
		System.out.println( "======================================================" );
	}
	
	//MP: Prints out the execution order of the processes. Used in generateLog()
	public void printExecutionOrder()
	{
		int i = 0;
		
		//MP: Rather complicated text processing to make the output look like the sample output.
		System.out.print( "\t" );
		
		for( Process p: EXECUTION_LIST ) //MP: Execution list is linked list of processes executed in order.
		{
			if( p != null )
			{
				System.out.print( "PID " + p.getP_ID() + " >> " );
				i++;
				if( ( i %= 6 ) == 0 )
					System.out.print( "\n\t" );
			}
		}	
		
		System.out.print( "Done" );
		
		if( i != 2 )
			System.out.println();
	}
	
	public double getAVERAGE_WAIT_TIME() //MP: Accessor for AVERAGE_WAIT_TIME
	{
		return AVERAGE_WAIT_TIME;
	}
	
	public double getTHROUGHPUT() //MP: Accessor for THROUGHPUT
	{
		return THROUGHPUT;
	}
	
	public double getCPU_UTILIZATION() //MP: Accessor for CPU_UTILIZATION
	{
		return CPU_UTILIZATION;
	}
		
	public int getTURNAROUND_TIME() //MP: Accessor for TURNAROUND TIME
	{
		return TURNAROUND_TIME;
	}
	
	//MP: Method to return the name of the scheduler being simulator
	public String getSchedulerName()
	{
		return SCHEDULER.getName();
	}
	
	//MP: Method that returns whether a simulation is realtime or normal
	public Sim_Type getSimulationType()
	{
		if( SCHEDULER.getName().equals( "PRM" ) || SCHEDULER.getName().equals( "EDF" ) )
			return Sim_Type.Realtime;
		
		return Sim_Type.Normal;
	}
}