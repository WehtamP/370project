import java.util.LinkedList;

public class RankingGenerator
{
	private LinkedList< Simulation > nSimulations; //MP: normal simulations
	private LinkedList< Simulation > rSimulations; //MP: realtime simulations
	
	//MP: Constructor, takes linked list of all simulations
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
		
		generateNormalRankings();
		printNormalRankings();
		
		printRealtimeRankings();
	}
	
	//MP: Method to print the rankings of non-realtime schedulers
	private void printNormalRankings()
	{	
		System.out.println( "Standard Schedulers" );
		
		for( int i = 0; i < nSimulations.size(); i++ )
		{
			System.out.println( i + ". " + nSimulations.get( i ).getSchedulerName() );
		}
	}
	
	//MP: Method to order the nSimulations into
	private void generateNormalRankings()
	{
		boolean finished = false;
		
		while( !finished )
		{
			//MP: Bubble sort by compareNormal()
			for( int i = 1; i < nSimulations.size(); i++ )
			{
				finished = true;
				
				if( compareNormal( nSimulations.get( i - 1 ), nSimulations.get( i ) ) )
				{
					finished = false;
					StaticStuff.swap( nSimulations, i - 1, i );
				}
			}
		}
	}
	
	//MP: method that returns false if s1 is better than s2, true otherwise
	private boolean compareNormal( Simulation s1, Simulation s2 )
	{
		if( s1.getCPU_UTILIZATION() > s2.getCPU_UTILIZATION() ) //MP: Compare CPU Utilization
			return false;
		else if( s2.getCPU_UTILIZATION() > s1.getCPU_UTILIZATION() )
			return true;
		else //MP: If not different, compare avg wait time
		{
			if( s1.getAVERAGE_WAIT_TIME() < s2.getAVERAGE_WAIT_TIME() )
				return false;
			else if( s2.getAVERAGE_WAIT_TIME() < s1.getAVERAGE_WAIT_TIME() )
				return true;
			else //MP: if not different, compare throughput
			{
				if( s1.getTHROUGHPUT() > s2.getTHROUGHPUT() )
					return false;
				else if( s2.getTHROUGHPUT() > s1.getTHROUGHPUT() )
					return true;
				else //MP: if not different, compare turnaround times
				{
					if( s1.getTURNAROUND_TIME() < s2.getTURNAROUND_TIME() )
						return false;
					else
						return true;
				}
			}
		}
	}
	
	private void generateRealtimeRankings()
	{
		
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
