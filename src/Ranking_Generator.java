import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Ranking_Generator
{
	private LinkedList< Simulation > nSimulations; //MP: normal simulations
	private LinkedList< Simulation > rSimulations; //MP: realtime simulations
	
	//MP: Constructor, takes linked list of all simulations
	public Ranking_Generator( LinkedList< Simulation > sims )
	{
		nSimulations = new LinkedList< Simulation >();
		rSimulations = new LinkedList< Simulation >();
		for( Simulation s: sims )
		{
			if( s.getSimulationType() == Sim_Type.Realtime )
				rSimulations.add(s);
			else
				nSimulations.add( s );	
		}
	}
	
	//MP: Method to print all of the rankings
	public void print() throws IOException
	{
		BufferedWriter writer = Static_Stuff.getReportWriter();
		printTitleBar( writer );
		
		generateNormalRankings();
		printNormalRankings( writer );
		
		printRealtimeRankings( writer );
	}
	
	//MP: Method to print the rankings of non-realtime schedulers
	private void printNormalRankings( BufferedWriter writer ) throws IOException
	{	
		writer.write( "Standard Schedulers" );
		
		for( int i = 0; i < nSimulations.size(); i++ )
		{
			writer.write( "\n" );
			writer.write( i + ". " + nSimulations.get( i ).getSchedulerName() );
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
					Static_Stuff.swap( nSimulations, i - 1, i );
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
	private void printRealtimeRankings( BufferedWriter writer ) throws IOException
	{	
		writer.write( "\n\nReal-time Schedulers\n" );
		
		for( int i = 0; i < rSimulations.size(); i++ )
		{
			writer.write( i + ". " + rSimulations.get( i ).getSchedulerName() + "\n" );
		}
	}
	
	//MP: Method to print the title bar
	private void printTitleBar( BufferedWriter writer ) throws IOException
	{
		writer.write( "\n" );
		writer.write( "======================================================\n");
		writer.write( "            Scheduling Algorithm Placement            \n" );
		writer.write( "======================================================\n" );
	}
}
