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

		//MG: generating rankings
		generateNormalRankings();
		generateRealtimeRankings();
		
		//MG: outputting rankings
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
		for(int j = 0; j < nSimulations.size(); j++)
		{
			//MP: Bubble sort by compareNormal()
			for( int i = 1; i < nSimulations.size(); i++ )
			{

				if( compareNormal( nSimulations.get( i - 1 ), nSimulations.get( i ) ) )
				{
					Static_Stuff.swap( nSimulations, i - 1, i );
				}
			}
		}
	}

	//MP: method that returns false if s1 is better than s2, true otherwise
	private boolean compareNormal( Simulation s1, Simulation s2 )
	{
		if(s1 == null)
			return true;
		
		if(s1.getCPU_UTILIZATION() > s2.getCPU_UTILIZATION())
		{
			return false;
		}
		else
		{
			if(s1.getCPU_UTILIZATION() < s2.getCPU_UTILIZATION())
			{
				return true;
			}
		}
		if(s1.getAVERAGE_WAIT_TIME() < s2.getAVERAGE_WAIT_TIME())
		{
			return false;
		}
		else 
		{
			if(s1.getAVERAGE_WAIT_TIME() > s2.getAVERAGE_WAIT_TIME())
			{
				return true;
			}
		}
		if(s1.getTHROUGHPUT() > s2.getTHROUGHPUT())
		{
			return false;
		}
		else
		{
			if(s1.getTHROUGHPUT() < s2.getTHROUGHPUT())
			{
				return true;
			}
		}
		if(s1.getTURNAROUND_TIME() < s2.getTURNAROUND_TIME())
		{
			return false;
		}
		else
		{
			if(s1.getTURNAROUND_TIME() > s2.getTURNAROUND_TIME())
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	
	//MP: Method to order the nSimulations into
	private void generateRealtimeRankings()
	{
		for(int j = 0; j < rSimulations.size(); j++)
		{
			//MP: Bubble sort by compareNormal()
			for( int i = 1; i < rSimulations.size(); i++ )
			{

				if( compareRealtime( rSimulations.get( i - 1 ), rSimulations.get( i ) ) )
				{
					System.out.println(i + " " + (i-1));
					Static_Stuff.swap( rSimulations, i - 1, i );
				}
			}
		}
	}
	
	private boolean compareRealtime(Simulation s1, Simulation s2)
	{
		if(s1.getVIOLATIONS() < s2.getVIOLATIONS())
		{
			return false;
		}
		else {
			if(s1.getVIOLATIONS() > s2.getVIOLATIONS())
			{
				return true;
			}
		}
		return compareNormal(s1, s2);
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
