import java.io.IOException;

public class Runner 
{
	public static void main( String[] args ) throws IOException
	{
		if( args.length != 2 ) //MP: Ensure arguments provided are valid
		{
			System.out.println( "Warning, you are using the simulation incorrectly.");
			System.out.println( "Correct usage is \"simulation processfile.dat 10" );
		}
		
		Process[] processes = InputProcessing.readFile( "processList.dat" );
		
		FCFS_Scheduler FCFS = new FCFS_Scheduler( processes );
		RR_Scheduler RR = new RR_Scheduler( processes, InputProcessing.getQuantum() );
		
		Simulation RR_Sim = new Simulation( RR );
		RR_Sim.run();
		RR_Sim.generateLog();
	}
}
