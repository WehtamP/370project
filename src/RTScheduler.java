import java.util.Collection;


public abstract class RTScheduler extends Scheduler {

	//MG: Subclass of scheduler for any commonalities between PRM and EDF
	RTScheduler(Collection<Process> eColl, Process procs[]){
		super(eColl, procs);
	}

	
	
}
