import java.util.concurrent.ConcurrentLinkedQueue;

public class FCFS_Scheduler extends Scheduler
{

	private ConcurrentLinkedQueue<Process> q;
	public FCFS_Scheduler( Process[] arr ) 
	{
		super(new ConcurrentLinkedQueue<Process>(), arr );
		q = (ConcurrentLinkedQueue<Process>)processes; //MG: To avoid having to cast 500 times
	}

	//MG: Chooses the next process
	protected Process chooseNext() {

		//MG: If the process at the top of the queue is on the CPU/waiting for the CPU, use it next
		if(q.peek().getSTATE() == PROCESS_STATE.ACTIVE_CPU || q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU){
			return q.peek();
		}
		else{
			if(q.peek().getSTATE() == PROCESS_STATE.FINISHED){
				q.remove(); //MG: Removes finished processes from the queue
			}
			if(q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU){
				return q.peek(); //MG: Use the next process if it's waiting
			}

			//MG: Iterates through the process queue, putting things that aren't waiting at the end.
			for(int i = 0; i < q.size(); i++){
				q.add(q.remove());
				if(q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU)					
					return q.peek();
			}
		}
		//MG: If I've looped through the queue and nothing is ready to use, then I have nothing to return.
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
		return "FCFS";
	}

	//MG: Modifies clean() so that it moves elements that are on IO to the end, avoiding the need for separate storage
	@Override
	protected void cleanIO(){
		super.cleanIO();
		ConcurrentLinkedQueue<Process> temp = new ConcurrentLinkedQueue<Process>();
		for(Process p:q){
			if(p.getSTATE() == PROCESS_STATE.ACTIVE_IO){
				q.remove(p);
				temp.add(p);
			}
		}
		for(Process p:temp){
			temp.remove(p);
			q.add(p);
		}
	}

}