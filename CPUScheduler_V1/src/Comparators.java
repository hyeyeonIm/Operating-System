import java.util.*;

public class Comparators {
	
	public static final double error = 0.0000001;
	
	public static Comparator<Job> RMComparator(final int t, final Vector<Task> taskset) {
		return new Comparator<Job>() {

			public int compare(Job j1, Job j2) {
				int index1 = j1.TaskIndex;
				int index2 = j2.TaskIndex;
				
				Task task1 = taskset.get(index1);
				Task task2 = taskset.get(index2);							
				
				double period1 = task1.Period;
				double period2 = task2.Period;													 

				return (period1<period2 ? -1 : (period1>period2 ? 1:(index1 < index2 ? -1: 1)));			
				}
		};
	}
	
	public static Comparator<Job> FIFOComparator() {
		return new Comparator<Job>() {

			public int compare(Job j1, Job j2) {				

				return -1;			
				}
		};
	}
}
