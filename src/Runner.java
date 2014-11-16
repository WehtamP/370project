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
		
		Process[] processes = InputProcessing.readFile( "processList.dat" ); //MP: Gets array of processes from the text file
		
		//MP: Setup each individual Scheduler
		//FCFS_Scheduler FCFS = new FCFS_Scheduler( processes );
		RR_Scheduler RR = new RR_Scheduler( processes, InputProcessing.getQuantum() );
		
		//MP: Setup each individual simulation
		//Simulation FCFS_Sim = new Simulation( FCFS );
		Simulation RR_Sim = new Simulation( RR );
		
		//MP: Run the simulations;
		//FCFS_Sim.run();
		RR_Sim.run();
		
		//MP: Generate the logs
		//FCFS_Sim.generateLog();
		RR_Sim.generateLog();
	}
}
