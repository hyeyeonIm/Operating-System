// 2. Create Algorithm_EDF.java

import java.util.*;

public class Algorithm_EDF implements Scheduler{

    public Vector<Job> comp = Comparators.EDFComparator(t);
    Collections.sort(readyQueue, comp);

    return readyQueue;
}
