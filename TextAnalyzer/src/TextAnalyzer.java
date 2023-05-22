import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.nio.file.*;

public class TextAnalyzer {
    private final Map<String, Integer> wordCount = new HashMap<>();
    private static final Pattern HTML_TAG = Pattern.compile("<[^>]+>");

    public void processFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            line = removeHtmlTags(line);
            countWords(line);
        }

        reader.close();
    }

    private void countWords(String line) {
        String[] words = line.toLowerCase().split("\\W+");
        for (String word : words) {
            if (!word.trim().isEmpty()) {
                if(word.endsWith("'s")) {
                    word = word.substring(0, word.length() - 2); // remove trailing 's
                }
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }
    }

    private String removeHtmlTags(String input) {
        Matcher matcher = HTML_TAG.matcher(input);
        return matcher.replaceAll("");
    }

    public void printTopWords(int limit) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordCount.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (int i = 0; i < Math.min(limit, entries.size()); i++) {
            System.out.println(entries.get(i).getKey() + ": " + entries.get(i).getValue());
        }
    }

    public static void main(String[] args) throws IOException {
        TextAnalyzer analyzer = new TextAnalyzer();
        Path filePath = findFileInDirectory(Paths.get("C:\\Users\\henry\\Desktop\\"), "TheRavenPoemWithHTMLTags.txt");
        if(filePath != null) {
            analyzer.processFile(filePath.toString());
            analyzer.printTopWords(20);
        } else {
            System.out.println("File not found.");
        }
    }

    public static Path findFileInDirectory(Path directory, String fileName) throws IOException {
        try (Stream<Path> paths = Files.walk(directory)) {
            return paths
                .filter(path -> path.getFileName().toString().equals(fileName))
                .findFirst()
                .orElse(null);
        }
    }
}