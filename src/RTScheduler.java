import java.util.Collection;


public abstract class RTScheduler extends Scheduler {

	protected int violations;

	//MG: Subclass of scheduler for any commonalities between PRM and EDF
	RTScheduler(Collection<Process> eColl, Process procs[]){
		super(eColl, procs);
		violations = 0;
	}

	
	//MG: Violation checker. NOTE: MAKE SURE TO RUN THIS AFTER ACTING BUT BEFORE REMOVING FINISHED PROCESSES
	protected void updateViolations(){
		for(Process p:processes){
			if(p.getPERIOD() == -1) //Hits -1 exactly one time per violation
				violations++;
			
		}
		if(processor.getPERIOD() == -1)
			violations++;
	}

}
