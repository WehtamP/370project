public abstract class Scheduler {
	protected Process procs[];
	protected boolean finished;
	
	public Scheduler(Process arr[]){
		procs = arr;
		finished = false;
	}

	protected void update( int clock ){   //MG: Does updates not related to moving things to/from the CPU/IO.
		for(Process p:procs){
			p.act(clock);
		}
	}
	
	protected abstract int chooseNext();  //MG: Responsible for deciding which process should go on the CPU
	
	protected int getCurrentProc(){
		for(Process p:procs){
			if(p.getSTATE() == PROCESS_STATE.ACTIVE_CPU){
				return p.getP_ID();
			}
		}
		return -1;
	}
	
	protected void clean(){         //MG: Removes processes from CPU and IO as needed
		for(Process p:procs){
			switch(p.getSTATE()){
			case ACTIVE_CPU:
				updateCurrentProcess(p.getP_ID());
				break;
			case ACTIVE_IO:
				if(p.getIO_BURST() <= 0)
					p.setSTATE(PROCESS_STATE.WAITING_CPU);
				break;
			default:
				break;
			}
		}
	}
	
	protected void updateCurrentProcess(int pid){  //MG: If the current process needs to move, move it appropriately
		if(procs[pid].getCPU_BURST() == 0){//MG: If the current process is done, set it to finished
			procs[pid].setSTATE(PROCESS_STATE.FINISHED);
		}
		else if(procs[pid].getSTATE() == PROCESS_STATE.WAITING_IO && procs[pid].getIO_BURST() > 0){ //MG: If the current proc. is ready for IO, move it to IO
			procs[pid].setSTATE(PROCESS_STATE.ACTIVE_IO);
		}
	}
	
	protected void CPU_Update(){ //MG: if the current process leaves or is kicked, put on the new one and perform kicking.
		int cur = getCurrentProc();
		int next = chooseNext();
		if(cur != next)
		{
			procs[next].setSTATE(PROCESS_STATE.ACTIVE_CPU); //MG: emplace new process
			if(cur != -1)
				procs[cur].setSTATE(PROCESS_STATE.WAITING_CPU); //MG: kick old process if necessary
		}
		
		else if( next == -1 ) //FIX, THIS IS STUPID
			finished = true;
	}
	
	public boolean isFinished() //Returns true if all processes are done, false otherwise (Accessor for finished)
	{
		return finished;
	}
	
	public void step( int clock ){  //MG: Performs all operations within the timestep
		clean();
		CPU_Update();
		clean();
		update( clock );
		//TODO: send state info to log, which will figure out the ready queue
	}
	
	public void printReadyQueue()
	{
		for( Process p: procs )
		{
			Debugging.printProcessInfo( p );
		}
	}
	
	
}
