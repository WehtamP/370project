import java.io.IOException;
import java.util.LinkedList;

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
		LinkedList< Simulation > simulations = new LinkedList< Simulation >();
		
		//MP: Setup each individual Scheduler
		FCFS_Scheduler FCFSsch = new FCFS_Scheduler( processes );
		SJF_Scheduler SJFsch = new SJF_Scheduler( processes );
		RR_Scheduler RRsch = new RR_Scheduler( processes, InputProcessing.getQuantum() );
		
		//MP: Setup each individual simulation
		simulations.add( new Simulation( FCFSsch ) );
		simulations.add( new Simulation( SJFsch ) );
		simulations.add( new Simulation( RRsch ) );
		
		//MP: Run the simulations;
		for( Simulation s: simulations )
			s.run();
		
		System.out.println( "HERE" );
		//MP: Generate the logs
		for( Simulation s: simulations )
			s.generateLog();
		
		//MP: Generate the rankings
		RankingGenerator ranking = new RankingGenerator( simulations );
		ranking.print();
	}
}
