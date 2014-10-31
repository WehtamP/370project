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
		
		FCFS_Scheduler sch = new FCFS_Scheduler( processes );
		Simulation sim = new Simulation( sch );
		sim.run();
	}
}
