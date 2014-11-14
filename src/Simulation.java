import java.util.LinkedList;

public class Simulation 
{
	private Scheduler SCHEDULER;
	private double THROUGHPUT;
	private int TURNAROUND_TIME;
	private int CLOCK;
	private float AVERAGE_WAIT_TIME;
	private double CPU_UTILIZATION;
	private int UNUTILIZED_CYCLE_COUNT;
	private LinkedList<Process> EXECUTION_LIST;
	
	public Simulation( Scheduler s )
	{
		UNUTILIZED_CYCLE_COUNT = 0;
		SCHEDULER = s;
		CLOCK = 0;
		EXECUTION_LIST = new LinkedList< Process >();
		EXECUTION_LIST.add( null );
	}
	
	public void run() //Start Simulation
	{	
		while( !SCHEDULER.isFinished() ) //MP: Run simulation until all processes are finished
		{
			CLOCK++;
			Process p;
			SCHEDULER.act( CLOCK );
			p = SCHEDULER.getCurrentProcess();
			
			if( p == null )
			{
				UNUTILIZED_CYCLE_COUNT++;
				continue;
			}
			
			if( !p.equals( EXECUTION_LIST.getLast() ) )
				EXECUTION_LIST.add( p );
		}
		
		calcValues();
	}
	
	private void calcValues()
	{

		THROUGHPUT = SCHEDULER.getNumProcesses() / ( double )CLOCK;
		AVERAGE_WAIT_TIME = Math.round( SCHEDULER.getAverageWaitTime() * 100 ) / 100;
		CPU_UTILIZATION = ( CLOCK - SCHEDULER.getUnutilizedCycles() ) / ( float )CLOCK;
		
	}
	
	public void generateLog()
	{
		System.out.println( "======================================================" );
		System.out.println( "Final Report for " + SCHEDULER.getName() );
		System.out.println( "CPU execution order for " + SCHEDULER.getName() );
		printExecutionOrder();
		System.out.println( "Throughput for " + SCHEDULER.getName() + " = " + THROUGHPUT);
		System.out.println( "Total Turn-around Time for " + SCHEDULER.getName() + " = " + CLOCK );
		System.out.println( "Average Wait Time for " + SCHEDULER.getName() + " = " + AVERAGE_WAIT_TIME );
		System.out.println( "CPU Utilization for " + SCHEDULER.getName() + " = " + CPU_UTILIZATION * 100 + "%" );
		System.out.println( "======================================================" );
	}
	
	//MP: Prints out the execution order of the processes. Used in generateLog()
	public void printExecutionOrder()
	{
		int i = 0;
		
		for( Process p: EXECUTION_LIST )
		{
			if( p != null )
			{
				System.out.print( "PID " + p.getP_ID() + " >> " );
				i++;
				if( ( i %= 6 ) == 0 )
					System.out.println();
			}
		}	
		
		System.out.print( "Done" );
		
		if( i != 1 )
			System.out.println();
	}
	
	public float getAVERAGE_WAIT_TIME() //MP: Accessor for AVERAGE_WAIT_TIME
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
	
	public int getUNUTILIZED_CYCLE_COUNT()
	{
		return UNUTILIZED_CYCLE_COUNT;
	}
}