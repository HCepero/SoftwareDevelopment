package cop2805;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordSearch{
    File file;
    List<String> list;
    public WordSearch(File file) throws IOException {
        this.file = file;
        this.list = Files.readAllLines(Paths.get(file.toURI()), StandardCharsets.UTF_8);
        this.list.replaceAll(String::toUpperCase);
    }
    public List<Integer> search(String word){
        List<Integer> intList = new ArrayList<>();
        for (int n=0; n<list.size(); n++){
            String str = list.get(n);
            if(str.contains(word)) intList.add(n);
        }
        return intList;
    }
}