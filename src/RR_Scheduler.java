import java.util.concurrent.ConcurrentLinkedQueue;

public class RR_Scheduler extends Scheduler
{
	private int quantum;
	private int qctr;
	private ConcurrentLinkedQueue<Process> q;
	public RR_Scheduler( Process[] arr, int quant ) 
	{
		super(new ConcurrentLinkedQueue<Process>(), arr );
		q = (ConcurrentLinkedQueue<Process>)readyQueue; //MG: To avoid having to cast 499 times
		quantum = quant; //MG: Counts down time steps in quantum
		qctr = quantum;
	}

	//MG: Same as FCFS_Scheduler, except if a process is on the CPU too long it gets kicked to the bottom of the list.
	protected Process chooseNext() {

		//MG: If the current process hasn't gone over its time limit, let it stay
		if(processor != null && qctr > 0){
			qctr--;
			return processor;
		}

		//MG: Otherwise, loop through the queue until a waiting process is found.
		else{
			qctr = quantum;
			for(int i = 0; i < q.size(); i++){
				if(q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU){
					qctr--;
					return q.peek();
				}
				else
					q.add(q.remove());
			}
		}
		
		if(q.isEmpty()){ //
			qctr = quantum;
			qctr--;
			return processor;
		}
		
		return null;
	}


	protected String getName()
	{
		return "RR";
	}

}
