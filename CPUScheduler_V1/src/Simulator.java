import java.io.*;
import java.util.*;


// 한 줄씩 디버깅 하면서 단계 확인하기!
public class Simulator{
	public static final double error = 0.0000001;
	public PlatformInfo pInfo;
	
	public Simulator(){ }

	public String run(PlatformInfo p) 
	{
		pInfo = p;
		
		switch(pInfo.scheduler){		
		case 10: pInfo.algorithm = new Algorithm_RM();	break;
		case 11: pInfo.algorithm = new Algorithm_EDF();	break;
		}
		Vector<Double> periods = new Vector<Double>();

		for(int i=0;i<pInfo.numTask;i++){			
			pInfo.readyQueue.add(new Job(pInfo.tasks.get(i).execTime,0,pInfo.tasks.get(i).Deadline,i));				
			pInfo.latestReleaseTime.add(0.0);
			periods.add(pInfo.tasks.get(i).Period);  // Task 각각의 period를 넣음
		}

		pInfo.endTime = LCM.generateLCM(periods)+1;    // 0부터 LCM까지 돌겠다

		return Simulate();
	}

	public String Simulate()
	{
		int t=0;
		int schedulable=1;    // 일단 스케쥴러를 1로 함
		for (t=(int)pInfo.startTime; t<(int)pInfo.endTime; t++)    // t가 0부터 end time까지 ; 각각의 값 디버깅으로 체크하기
		{
			if (CheckDeadline(t)==false) 
			{
				schedulable=0;
				break;
			}
			updateReadyQueue(t);
			Schedule(t);
		}
		
		String s=schedulable+" "+pInfo.numTask;
		return s;
	}

	// Check if some job has missed its deadline at time t. If yes, then stop the scheduling.
	public boolean CheckDeadline(int t){

		for(int i=0;i<pInfo.readyQueue.size();i++){

			Job job = pInfo.readyQueue.get(i);
			if((job.Deadline == t) && (job.execTime > 0)){	   // deadline안에 ~~			
				return false;
			}
		}
		return true;
	}

	// If a new job is released at time t, then update the list of active jobs
	public void updateReadyQueue(int t)    // released job이 있는지 check
	{		
		for(int i=0;i<pInfo.tasks.size();i++)
		{
			Task task = pInfo.tasks.get(i);
			if(pInfo.latestReleaseTime.get(i) + task.Period <= t)
			{				
				pInfo.readyQueue.add(new Job(task.execTime,t,t+task.Deadline,i));
				pInfo.latestReleaseTime.set(i, new Double(t));
			}
		}
	}

	// Calls the appropriate scheduler and updates all variables once scheduling decisions are made
	public void Schedule(int t){				
		pInfo.readyQueue = pInfo.algorithm.schedule(t, pInfo.numProcessor, pInfo.readyQueue, pInfo.tasks);						
		int m=0;
				
		for(int i=0;i<pInfo.readyQueue.size();i++){		
			if (m>=pInfo.numProcessor) {
			} else {
				m = m+1;
				
				Job job = pInfo.readyQueue.get(i);
				
				job.execTime = job.execTime-1;					
				pInfo.readyQueue.set(i, job);
			}
		}		

		if(m > pInfo.numProcessor){
			System.out.println(t);
			System.out.println("ERROR: SCHEDULED MORE THAN M");
			System.exit(0);
		}

		for(int i=0;i<pInfo.readyQueue.size();i++){

			if(pInfo.readyQueue.get(i).execTime== 0){
				pInfo.readyQueue.remove(i);															
				i = i-1;
			}
		}
	}
}
