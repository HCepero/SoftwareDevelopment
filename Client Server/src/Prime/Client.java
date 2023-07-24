package Prime;

import java.io.*;
import java.net.*;

/**
 * This is the client part of the client/server application.
 * It connects to the server on localhost and port 4444.
 * It takes a number from the user, sends this number to the server, 
 * waits for the response from the server, prints the response, and then closes the connection.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 4444);

        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter a number:");
        String numberStr = stdIn.readLine();
        writer.println(numberStr);

        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        String response = reader.readLine();
        System.out.println("Is the number prime? " + response);

        socket.close();
    }
}
