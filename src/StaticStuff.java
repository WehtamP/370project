import java.util.LinkedList;

public class StaticStuff 
{
	private static int snapshotDensity;
	
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