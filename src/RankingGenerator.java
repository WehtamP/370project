import java.util.LinkedList;

public class RankingGenerator
{
	private LinkedList< Simulation > nSimulations; //MP: normal simulations
	private LinkedList< Simulation > rSimulations; //MP: realtime simulations
	
	public RankingGenerator( LinkedList< Simulation > sims )
	{
		nSimulations = new LinkedList< Simulation >();
		for( Simulation s: sims )
		{
			if( s.getSimulationType() == Sim_Type.Realtime )
				;
			else
				nSimulations.add( s );
				
				
		}
	}
	
	//MP: Method to print all of the rankings
	public void print()
	{
		printTitleBar();
		printNormalRankings();
		printRealtimeRankings();
	}
	
	//MP: Method to print the rankings of non-realtime schedulers
	private void printNormalRankings()
	{
		boolean finished = false;
		
		while( !finished )
		{
			//MP: Bubble sort by CPU_UTILIZATION
			for( int i = 1; i < nSimulations.size(); i++ )
			{
				finished = true;
				
				if( nSimulations.get( i ).getCPU_UTILIZATION() > nSimulations.get( i - 1 ).getCPU_UTILIZATION() )
				{
					finished = false;
					Methods.swap( nSimulations, i - 1, i );
				}
			}
		}
		
		//MP: Printing the rankings
		System.out.println( "Standard Schedulers" );
		
		for( int i = 0; i < nSimulations.size(); i++ )
		{
			System.out.println( i + ". " + nSimulations.get( i ).getSchedulerName() );
		}
	}
	
	//MP: Method to print the realtime schedulers' rankings
	private void printRealtimeRankings()
	{
		
	}
	
	//MP: Method to print the title bar
	private void printTitleBar()
	{
		System.out.println();
		System.out.println( "======================================================");
		System.out.println( "            Scheduling Algorithm Placement            " );
		System.out.println( "======================================================" );
	}
}
