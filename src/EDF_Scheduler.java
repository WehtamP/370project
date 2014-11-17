import java.util.LinkedList;


public class EDF_Scheduler extends RTScheduler {

	EDF_Scheduler(Process arr[]){
		super(new LinkedList<Process>(), arr);
	}

	//MG: Given the current best process and another process, determine whether the new process
	//is more suited to be the next process to execute.
	Boolean override(Process p1, Process p2){

		//MG: Ensures nothing 0/negative goes through
		if(p2.getCPU_BURST() <= 0){

			p2.setSTATE(PROCESS_STATE.FINISHED);
			return false;
		}

		//MG: If nothing has been selected yet, default to the applicable competitor
		if(p1 == null){

			return true;
		}




		//MG: Blocks anything with a CPU Burst of 0 or lower
		if(p2.getSTATE() == PROCESS_STATE.FINISHED){
			return false;
		}


		//MG: Choose the one with a lower deadline
		if((p1.getPERIOD() - p1.getCPU_BURST()) < (p2.getPERIOD() - p2.getCPU_BURST()) && p1.getCPU_BURST() > 0){

			return false;
		}
		else if((p1.getPERIOD() - p1.getCPU_BURST()) > (p2.getPERIOD() - p2.getCPU_BURST()) && p2.getCPU_BURST() > 0){

			return true;
		}

		//MG: If the current process is active, don't overwrite it.
		if(p1.getSTATE() == PROCESS_STATE.ACTIVE_CPU){

			return false;
		}

		//MG: If p2 is active, choose it.
		if(p2.getSTATE() == PROCESS_STATE.ACTIVE_CPU){

			return true;
		}

		else {

			//MG: Bakery choice

			return (p1.getP_ID() > p2.getP_ID());
		}
	}

	@Override
	protected Process chooseNext() {
		Process cBest = null;
		//MG: Compares elements to find the process with the lowest time,
		//favoring the currently active process and adhering to the bakery algorithm
		for(Process p:processes){
			if(override(cBest, p))
				cBest = p;
		}
		return cBest;
	}

	@Override
	protected String getName() {
		return "EDF";
	}

}
