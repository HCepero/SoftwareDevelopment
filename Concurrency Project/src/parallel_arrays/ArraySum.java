package parallel_arrays;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *The ArraySum class extends RecursiveTask to compute the sum of an array segment.
 * This class uses a fork/join framework to provide thread-level parallelism.
 * 
 * @version 1.0
 */
public class ArraySum extends RecursiveTask<Long> {
    // Threshold of array size above which task will be split into smaller tasks
    static final int THRESHOLD = 5000000;
    final long[] array;
    final int start;
    final int end;

    // Static variable to keep track of the number of threads spawned.
    static final AtomicInteger threadCount = new AtomicInteger(0);

    /**
     * Creates an ArraySum task that will compute the sum of the array elements between start and end, inclusive.
     * @param array the array to sum.
     * @param start the starting index of the section to sum.
     * @param end the end index of the section to sum.
     */
    ArraySum(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    /**
     * Computes the sum of the portion of the array between start and end (inclusive).
     * If the portion is large, this task is split into two smaller tasks.
     * @return the sum of the portion of the array handled by this task.
     */
    @Override
    protected Long compute() {
        if (end - start < THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            threadCount.incrementAndGet(); // Increment the count as new thread spawned.
            int mid = start + (end - start) / 2;
            ArraySum left = new ArraySum(array, start, mid);
            ArraySum right = new ArraySum(array, mid, end);
            left.fork();
            return right.compute() + left.join();
        }
    }
}

