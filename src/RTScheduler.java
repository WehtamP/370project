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
		Process lp = getLastProcess();
		if(lp != null){
			if(lp.getPERIOD() == 0)
				violations++;
		}
		for(Process p:readyQueue){
			if(p.getPERIOD() == 0)
				violations++;
		}
	}
	
	//MP: Accessor for number of deadline violations
	public int getNumViolations()
	{
		return violations;
	}
}
