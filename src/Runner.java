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
		else
		{	
			try
			{
				StaticStuff.setSnapshotDensity( Integer.parseInt( args[ 1 ] ) );
			}
			
			catch( Exception e )
			{
				System.out.println( "Your second argument is invalid, defaulting to 10 for snapshot density" );
				StaticStuff.setSnapshotDensity( 10 );
			}
		}
			
		StaticStuff.setSnapshotDensity( 1 );
		StaticStuff.initFiles();
		
		Process[] processes = InputProcessing.readFile( args[ 0 ] ); //MP: Gets array of processes from the text file
		LinkedList< Simulation > simulations = new LinkedList< Simulation >();
		
		//MP: Setup each individual Scheduler
		FCFS_Scheduler FCFSsch = new FCFS_Scheduler( processes );
		SJF_Scheduler SJFsch = new SJF_Scheduler( processes );
		RR_Scheduler RRsch = new RR_Scheduler( processes, InputProcessing.getQuantum() );
		SJR_Scheduler SJRsch = new SJR_Scheduler( processes );
		Priority_Scheduler PrioritySch = new Priority_Scheduler( processes );
		PRM_Scheduler PRMsch = new PRM_Scheduler( processes );
		EDF_Scheduler EDFsch = new EDF_Scheduler( processes );
		
		//MP: Setup each individual simulation
		simulations.add( new Simulation( FCFSsch ) );
		simulations.add( new Simulation( SJFsch ) );
		simulations.add( new Simulation( RRsch ) );
		simulations.add( new Simulation( SJRsch ) );
		simulations.add( new Simulation( PrioritySch ) );
		simulations.add( new Simulation( PRMsch ) );
		simulations.add( new Simulation( EDFsch ) );
		
		//MP: Run the simulations;
		for( Simulation s: simulations )
			s.run();
		
		
		//MP: Generate the logs
		for( Simulation s: simulations )
			s.generateLog();
		
		//MP: Generate the rankings
		RankingGenerator ranking = new RankingGenerator( simulations );
		ranking.print();
	}
}
