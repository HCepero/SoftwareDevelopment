package cop2805;

import java.io.IOException;
import java.util.List;
import java.net.*;
import java.io.*;


public class Server{
    public static void main(String[] args) throws IOException {
        WordSearch searcher = new WordSearch(new File(""));
        ServerSocket socket = new ServerSocket(1236);
        while (true){
            Socket s = socket.accept();
            DataOutputStream dataOut=new DataOutputStream(s.getOutputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
            String word = br.readLine();
            List<Integer> list = searcher.search(word);
            for(int n : list){dataOut.write(n);}
            socket.close();
        }
    }
}