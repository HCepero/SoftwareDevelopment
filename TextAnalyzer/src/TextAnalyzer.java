import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;
import java.nio.file.*;

/**
 * TextAnalyzer is a utility class for processing a text file. It can remove HTML tags, count word occurrences, 
 * and print or retrieve most frequently occurring words.
 *
 * @version 1.1
 */
public class TextAnalyzer {
    private final Map<String, Integer> wordCount = new HashMap<>();
    private static final Pattern HTML_TAG = Pattern.compile("<[^>]+>");

    /**
     * Processes a text file, line by line, removing HTML tags and counting word occurrences.
     *
     * @param filename The path to the file to be processed.
     * @throws IOException If there's a problem reading the file.
     */
    public void processFile(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            line = removeHtmlTags(line);
            countWords(line);
        }

        reader.close();
    }

    /**
     * Counts the occurrences of each word in a line of text, updating the wordCount map.
     *
     * @param line A line of text to be analyzed for word occurrences.
     */
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

    /**
     * Removes HTML tags from a line of text.
     *
     * @param input A line of text possibly containing HTML tags.
     * @return The input string with all HTML tags removed.
     */
    private String removeHtmlTags(String input) {
        Matcher matcher = HTML_TAG.matcher(input);
        return matcher.replaceAll("");
    }

    /**
     * Prints out the most frequently occurring words in the processed text, up to a specified limit.
     *
     * @param limit The maximum number of top frequent words to print.
     */
    public void printTopWords(int limit) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordCount.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (int i = 0; i < Math.min(limit, entries.size()); i++) {
            System.out.println(entries.get(i).getKey() + ": " + entries.get(i).getValue());
        }
    }

    /**
     * Retrieves the most frequently occurring words in the processed text, up to a specified limit.
     *
     * @param limit The maximum number of top frequent words to retrieve.
     * @return A list of Map.Entry with words and their respective frequencies.
     */
    public List<Map.Entry<String, Integer>> getTopWords(int limit) {
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(wordCount.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        return entries.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }

    /**
     * Main method for the TextAnalyzer class.
     * Processes a specific text file and prints out the 20 most frequently occurring words.
     *
     * @param args Not used.
     * @throws IOException If there's a problem reading the file.
     */
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

    /**
     * Finds a file with a specific name in a given directory (including subdirectories).
     *
     * @param directory The directory to search in.
     * @param fileName The name of the file to search for.
     * @return The Path to the file if it's found, null otherwise.
     * @throws IOException If there's a problem reading the directory.
     */
    public static Path findFileInDirectory(Path directory, String fileName) throws IOException {
        try (Stream<Path> paths = Files.walk(directory)) {
            return paths
                .filter(path -> path.getFileName().toString().equals(fileName))
                .findFirst()
                .orElse(null);
        }
    }
}