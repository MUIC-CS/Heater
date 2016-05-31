import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
public class Heater {
	public static void main(String[] args){
		ConcurrentHashMap<Long, Long> myresult = new ConcurrentHashMap<Long, Long>();
		List<ParallelHeater> jobs = new ArrayList<ParallelHeater>();
		long step = (long)1e10;
		for(long i=1; i<1e11; i+=step){
			ParallelHeater myjob = new ParallelHeater(i, i+step, myresult);
			myjob.start();
			System.out.println("Job "+i+" started");
			jobs.add(myjob);
		}
		
		for(ParallelHeater job: jobs){
			try {
				System.out.println("Job "+job.start+" has stopped");
				job.join();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		long realresult = 0;
		for(Long res: myresult.values()){
			realresult += res;
		}
		System.out.println(realresult);
	}
}

class ParallelHeater extends Thread{
	public long start;
	private long stop;
	private ConcurrentHashMap<Long, Long> result;
	
	ParallelHeater(long start, long stop, ConcurrentHashMap<Long, Long> result){
		this.start = start;
		this.stop = stop;
		this.result = result;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long sum = 0;
		for(long i=this.start; i<=this.stop; i++){
			sum += i;
		}
		this.result.put(this.start, sum);
	}
	
}