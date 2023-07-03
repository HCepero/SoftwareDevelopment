package parallel_arrays;

import java.util.concurrent.ForkJoinPool;
import java.util.Random;

/**
 * Computes the sum of a large array using both single-threaded and multi-threaded methods.
 * 
 * @version 1.0
 */
public class ArraySumMain {
    // Size of the array to sum
    static final int SIZE = 200000000;

    /**
     * Main method. Generates a large array of random numbers, calculates its sum using 
     * both single-threaded and multi-threaded methods, and prints the sum, computation time, 
     * and number of threads used (for multi-threaded method).
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        // Random number generator
        Random rand = new Random();

        // Generate the array of random numbers
        long[] array = new long[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = 1 + rand.nextInt(10);
        }

        // Start time for parallel computation
        long startTime = System.currentTimeMillis();

        // Perform the parallel computation
        ForkJoinPool pool = new ForkJoinPool();
        long parallelSum = pool.invoke(new ArraySum(array, 0, array.length));

        // End time for parallel computation
        long endTime = System.currentTimeMillis();

        // Print the results for the parallel computation
        System.out.println("Parallel sum: " + parallelSum);
        System.out.println("Parallel sum time: " + (endTime - startTime) + "ms");
        System.out.println("Number of threads: " + pool.getPoolSize());

        // Start time for single-threaded computation
        startTime = System.currentTimeMillis();

        // Perform the single-threaded computation
        long singleThreadSum = 0;
        for (long i : array) {
            singleThreadSum += i;
        }

        // End time for single-threaded computation
        endTime = System.currentTimeMillis();

        // Print the results for the single-threaded computation
        System.out.println("Single-threaded sum: " + singleThreadSum);
        System.out.println("Single-threaded sum time: " + (endTime - startTime) + "ms");
    }
}
