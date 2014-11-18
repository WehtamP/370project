import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.LinkedList;

public class StaticStuff 
{
	public static PrintWriter snapshotWriter;
	private static int snapshotDensity;
	
	//MP: Creates the files to be written to, empties them if they already exist.
	public static void initFiles()
	{
		File snapshots = new File( "snapshot.dat" );
		File finalReport = new File( "FinalReport.dat" );
		
		//MP: If snapshot.dat already exists, delete it.
		if( snapshots.exists() )
			snapshots.delete();
		
		//MP: If FinalReport.dat already exists, delete it.
		if( finalReport.exists() )
			finalReport.delete();
		
		//MP: Create both files.
		try
		{
			snapshots.createNewFile();
			finalReport.createNewFile();
		}
		
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	//MP: Function prints out all details of process provided by text file
	public static void printProcessInfo( Process process1 )
	{
		System.out.println( "P_ID =\t\t" + process1.getP_ID() );
		System.out.println( "CPU_BURST =\t" + process1.getCPU_BURST() );
		System.out.println( "I\\O_BURST =\t" + process1.getIO_BURST() );
		System.out.println( "Priority =\t" + process1.getPRIORITY() );
		System.out.println( "Period =\t" + process1.getPERIOD() );
		System.out.println( "State =\t" + process1.getSTATE().toString() );
		System.out.println( "\n" );
	}
	
	//MP: Function to swap 2 elements in a linked list
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void swap( LinkedList a, int src1, int src2 )
	{	
		a.addLast( a.get( src2 ) );
		a.addLast( a.get( src1 ) );
		
		a.set( src2,  a.removeLast() );
		a.set( src1, a.removeLast() );
	}
	
	//MP: Initializes the snapshot writer
	public void initWriter()
	{
		try
		{
			snapshotWriter = new PrintWriter( "snapshot.dat", "UTF-8" );
		}
		catch( Exception e )
		{
			System.out.println( "An error has occurred" );
			e.printStackTrace();
		}
	}
	
	//MP: Mutator for snapshot density
	public static void setSnapshotDensity( int a )
	{
		snapshotDensity = a;
	}
	
	public static int getSnapshotDensity()
	{
		return snapshotDensity;
	}
}