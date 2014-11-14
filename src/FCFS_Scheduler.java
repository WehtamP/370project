import java.util.concurrent.ConcurrentLinkedQueue;

public class FCFS_Scheduler extends Scheduler
{

	private ConcurrentLinkedQueue<Process> q;
	public FCFS_Scheduler( Process[] arr ) 
	{
		super(new ConcurrentLinkedQueue<Process>(), arr );
		q = (ConcurrentLinkedQueue<Process>)processes; //MG: To avoid having to cast 500 times
	}

	protected Process chooseNext() {

		if(q.peek().getSTATE() == PROCESS_STATE.ACTIVE_CPU || q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU){
			return q.peek();
		}
		else{
			if(q.peek().getSTATE() == PROCESS_STATE.FINISHED){
				q.remove();
			}
			if(q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU){
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
		return "FCFS";
	}
}