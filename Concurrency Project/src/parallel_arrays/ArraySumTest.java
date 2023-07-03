package parallel_arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.ForkJoinPool;

import org.junit.jupiter.api.Test;

public class ArraySumTest {
    @Test
    public void testCompute() {
        // Define a simple array
        long[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Compute the expected sum
        long expectedSum = 55;

        // Use the ArraySum class to compute the sum
        ForkJoinPool pool = new ForkJoinPool();
        long computedSum = pool.invoke(new ArraySum(array, 0, array.length));

        // Assert that the computed sum is correct
        assertEquals(expectedSum, computedSum);
    }
}