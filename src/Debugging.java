public class Debugging 
{
	//Function prints out all details of process provided by text file
	public static void printProcessInfo( Process process1 )
	{
		System.out.println( "P_ID =\t\t" + process1.getP_ID() );
		System.out.println( "CPU_BURST =\t" + process1.getCPU_BURST() );
		System.out.println( "I\\O_BURST =\t" + process1.getIO_BURST() );
		System.out.println( "Priority =\t" + process1.getPRIORITY() );
		System.out.println( "Period =\t" + process1.getPERIOD() );
	}
}
