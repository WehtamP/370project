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
		quantum = quant;
		qctr = quantum;
	}

	protected Process chooseNext() {
		if(qctr <= 0){
			q.add(q.remove());
			qctr = quantum;
		}
		if(q.peek().getSTATE() == PROCESS_STATE.ACTIVE_CPU || q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU){
			qctr = qctr - 1;
			return q.peek();
		}
		else{
			if(q.peek().getSTATE() == PROCESS_STATE.FINISHED){
				q.remove();
				qctr = quantum;
			}
			if(q.peek().getSTATE() == PROCESS_STATE.WAITING_CPU){
				return q.peek();
			}
			for(int i = 0; i < q.size(); i++){
				q.add(q.remove());
				qctr = quantum;
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