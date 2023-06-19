package fashion;
/**
 * This class calculates the Fibonacci number for a given index in both a recursive and iterative manner,
 * and measures the time it takes for each method to execute.
 */
public class Main {

    /**
     * Main method that triggers the Fibonacci calculations for each number from 0 to 30.
     *
     * @param args The command line arguments. This program does not use them.
     */
    public static void main(String[] args) {
        for (int i = 0; i <= 30; i++) {
            long startTime = System.nanoTime();
            System.out.println("Recursive Fibonacci for N=" + i + ": " + fibonacciRecursive(i));
            long endTime = System.nanoTime();
            System.out.println("Time taken (ns): " + (endTime - startTime));

            startTime = System.nanoTime();
            System.out.println("Iterative Fibonacci for N=" + i + ": " + fibonacciIterative(i));
            endTime = System.nanoTime();
            System.out.println("Time taken (ns): " + (endTime - startTime));
        }
    }

    /**
     * Recursive calculation of Fibonacci number.
     * 
     * @param n The index in the Fibonacci sequence to calculate.
     * @return The Fibonacci number at index n.
     */
    public static int fibonacciRecursive(int n) {
        if (n <= 1)
            return n;
        else
            return fibonacciRecursive(n-1) + fibonacciRecursive(n-2);
    }

    /**
     * Iterative calculation of Fibonacci number.
     * 
     * @param n The index in the Fibonacci sequence to calculate.
     * @return The Fibonacci number at index n.
     */
    public static int fibonacciIterative(int n) {
        if (n <= 1)
            return n;

        int fib = 1;
        int prevFib = 1;

        for (int i = 2; i < n; i++) {
            int temp = fib;
            fib+= prevFib;
            prevFib = temp;
        }
        return fib;
    }
}