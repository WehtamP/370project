import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//MP: Class that deals with the text file or strings
public class InputProcessing 
{
	//MP: Function that returns an array containing all of the processes when passed the data file's path
	public static Process[] readFile( String filepath ) throws IOException
	{
		int numProcesses, i;
		File text = new File( filepath );
		
		try //MP: Open file in BufferedReader, if fail to find file catch
		{
			BufferedReader buf = new BufferedReader( new FileReader( text ) ); 
			
			if( ( numProcesses = getNumberOnLine( buf.readLine() ) ) == -1 )//MP: Get the number of processes by feeding first line to function, test for failure
			{
				System.out.println( "There was an error parsing the text file" );
				return null;
			}
			buf.readLine(); //MP: Skip the second line, quantum not important
			buf.readLine(); //MP: Skip the third line, headers not important
			
			Process[] processes = new Process[ numProcesses ];
			for( i = 0; i < numProcesses; i++ ) //MP: Read in data
			{
				processes[ i ] = createProcess( buf.readLine() );
			}
			
			buf.close();
			return processes;
		} 
		catch ( FileNotFoundException e ) //MP: If no file present, print error message + stack trace
		{
			System.out.println( "The file does not exist. Printing stack trace." );
			e.printStackTrace();
		}
		
		return null;
	}
	
	//MP: Function to retrieve the quantum from the file
	//MP: Returns -1 on failure
	public static int getQuantum( String filename )
	{
		int quantum = -1;
		File text = new File( filename );
		
		try //MP: Open file in BufferedReader, if fail to find file catch
		{
			BufferedReader buf = new BufferedReader( new FileReader( text ) ); 
			
			buf.readLine(); //MP: Skip first line, not important
			if( ( quantum = getNumberOnLine( buf.readLine() ) ) == -1 )//MP: Get the number of processes by feeding first line to function, test for failure
			{
				System.out.println( "There was an error parsing the text file" );
				buf.close();
				return quantum;
			}
			
			buf.close();
			return quantum;
		} 
		
		catch( IOException e )
		{
			System.out.println( "There was an error reading the file. Printing stack trace" );
			e.printStackTrace();
		}
		
		return quantum;
	}
	
	//MP: Function for use in readFile() and getQuantum()
	//MP: A line of the text file is passed as a string, and the number in that String is returned.
	//MP: Returns -1 if error
	private static int getNumberOnLine( String firstline )
	{
		int i, val = -1;
		String[] arr = firstline.split( " " );
		
		for( i = 0; i < arr.length; i++ )
		{
			try //MP: If arr[ i ] is not an integer, catch.
			{
				val = Integer.parseInt( arr[ i ] );
				break;
			}
			catch( NumberFormatException e ) //MP: If not an integer, continue
			{
				continue;
			}
		}
			return val;
	}
	
	//MP: Returns a newly created process when passed one line from the text file.
	public static Process createProcess( String str )
	{
		int i, j, val;
		String stringArgs[] = str.split( "\t" ); //MP: Tokenize string
		int args[] = new int[ 5 ];
		
		for( i = j = 0; i < stringArgs.length && j < 5; i++ )
		{
			try //MP: If token is not a number, catch
			{
				val = Integer.parseInt( stringArgs[ i ] );
			}
			
			catch( NumberFormatException e )//MP: If token is not a number, continue the loop at the next token
			{
				continue;
			}
			
			args[ j ] = val; //MP: If val is a number, assign val to args[ j ] and increment j
			j++;
		}
		Process process1 = new Process( args );
		return process1;
	}
}
