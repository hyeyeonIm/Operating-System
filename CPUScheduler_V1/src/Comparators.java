import java.util.*;

public class Comparators {  // task형 vector를 받아서 알고리즘을 만듦
	
	public static final double error = 0.0000001;
	
	public static Comparator<Job> RMComparator(final int t, final Vector<Task> taskset) {
		return new Comparator<Job>() {

			public int compare(Job j1, Job j2) {  // job의 vector를 받은 상황
				// 두 job을 받음
				int index1 = j1.TaskIndex;
				int index2 = j2.TaskIndex;
				
				Task task1 = taskset.get(index1);
				Task task2 = taskset.get(index2);							
				
				double period1 = task1.Period;
				double period2 = task2.Period;													 

				// Sorting
				// task period를 비교해서 바꿀지 말지 정하는 거
				// -1이면 안 바꿈 / 1이면 바꿈
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

	
	// hw1 Create EDF Comparator
	// EDFComparator는 작업의 마감 시간을 기준으로 정렬
	// DFComparator라는 이름의 static 메서드 선언. t라는 정수형 파라미터를 입력으로 받음, Comparator<Job> 타입의 객체를 반환함
	public static Comparator<Job> EDFComparator(final int t) {
	    return new Comparator<Job>() { // 새로운 익명 Comparator<Job> 클래스의 인스턴스를 반환함. Job 객체를 비교하는 데 사용됨
	        public int compare(Job j1, Job j2) { // compare 메서드를 선언. Job 객체 두 개를 입력으로 받고, 정수값 반환. 
	        	// 반환 값은 첫 번째 Job 객체와 두 번째 Job 객체의 비교 결과 나타냄
	        	
                int index1 = j1.TaskIndex; // 첫 번째 Job 객체의 TaskIndex 값을 가져와 index1 변수에 저장
                int index2 = j2.TaskIndex; // 두 번째 Job 객체의 TaskIndex 값을 가져와 index2 변수에 저장
                
	            double deadline1 = j1.Deadline; // 첫 번째 Job 객체의 Deadline 값을 가져와 deadline1 변수에 저장
	            double deadline2 = j2.Deadline; // 두 번째 Job 객체의 Deadline 값을 가져와 deadline2 변수에 저장

	            if (deadline1 < deadline2) { // 첫 번째 Job 객체 우선순위가 두 번째 Job 객체 우선순위보다 높은 경우
	                return -1; // deadline1이 deadline2보다 작을 때 -1을 반환 
	            } else if (deadline1 > deadline2) { // 두 번째 Job 객체 우선순위가 첫 번째 Job 객체우선순위보다 높은 경우
	                return 1; // deadline1이 deadline2보다 클 때 1을 반환
	            } else { // deadline1과 deadline2가 동일한 경우
	                return (index1 < index2 ? -1 : 1); // 두 Job 객체의 deadline 값이 동일한 경우, TaskIndex 값을 비교
	                // 첫 번째 Job 객체의 TaskIndex 값이 더 작으면 -1 반환, 아니면 1 반환
	                // 즉, TaskIndex 값이 작은 Job이 먼저 오도록 함
	            }
	        }
	    };
	}}