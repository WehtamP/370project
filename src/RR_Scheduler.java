import java.util.concurrent.ConcurrentLinkedQueue;

public class RR_Scheduler extends Scheduler
{
	private int quantum;
	private int qctr;
	private ConcurrentLinkedQueue<Process> q;
	public RR_Scheduler( Process[] arr, int quant ) 
	{
		super(new ConcurrentLinkedQueue<Process>(), arr );
		q = (ConcurrentLinkedQueue<Process>)processes; //MG: To avoid having to cast 499 times
		quantum = quant; //MG: Counts down time steps in quantum
		qctr = quantum -1;
	}

	//MG: Same as FCFS_Scheduler, except if a process is on the CPU too long it gets kicked to the bottom of the list.
	protected Process chooseNext() {
		//MG: If this process has been on the CPU for [quantum] timesteps, kick it to the end of the line.
		if(qctr <= 0 && q.peek().getSTATE() == PROCESS_STATE.ACTIVE_CPU){
			q.peek().setSTATE(PROCESS_STATE.WAITING_CPU);
			q.add(q.remove());
			qctr = quantum;
			qctr--;
		}
		//MG: Use whatever's at the top of the process queue. If it's the same, decrement the timer, else reset the timer.
		if(q.peek().getSTATE() == PROCESS_STATE.ACTIVE_CPU || q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU){
			if(q.peek().getSTATE() == PROCESS_STATE.ACTIVE_CPU){
				qctr = qctr - 1;
			}
			else{
				qctr = quantum;
				qctr--;
			}return q.peek();
		}
		else{
			//MG: performs the same changes as FCFS, but also resets the counter.
			qctr = quantum;
			if(q.peek().getSTATE() == PROCESS_STATE.FINISHED){
				q.remove();
			}
			if(q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU){
				qctr--;
				return q.peek();
			}
			for(int i = 0; i < q.size(); i++){
				q.add(q.remove());
				if(q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU)					
					return q.peek();
			}
		}
		return null;
	}

	@Override
	protected Process getCurrentProcess(){
		if(q.peek().getSTATE() == PROCESS_STATE.ACTIVE_CPU)
			return q.peek();
		return null;
	}

	protected String getName()
	{
		return "RR";
	}

}
