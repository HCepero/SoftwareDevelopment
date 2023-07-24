package Prime;

import java.io.*;
import java.net.*;

/**
 * This is the server part of the client/server application.
 * It listens for connections on port 4444 and starts a new PrimeCheckerThread for each connection.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(4444);
        System.out.println("Server is listening on port 4444");
        while (true) {
            Socket socket = serverSocket.accept();
            new PrimeCheckerThread(socket).start();
        }
    }
}

/**
 * This thread is responsible for checking if a given number is prime or not.
 */
class PrimeCheckerThread extends Thread {
    private Socket socket;

    PrimeCheckerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);

            String numberStr;
            while ((numberStr = reader.readLine()) != null) {
                int number = Integer.parseInt(numberStr);
                boolean isPrime = checkPrime(number);
                writer.println(isPrime ? "Yes" : "No");
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }

    /**
     * Checks if a given number is prime or not.
     * @param n The number to check.
     * @return True if the number is prime, false otherwise.
     */
    boolean checkPrime(int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
