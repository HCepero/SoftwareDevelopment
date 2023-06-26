import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

/**
 * Unit tests for the TextAnalyzer class.
 *
 * @version 1.0
 */
public class TextAnalyzerTest {
    private TextAnalyzer analyzer;

    @BeforeEach
    public void setUp() {
        analyzer = new TextAnalyzer();
    }

    /**
     * Test for the countWords method.
     */
    @Test
    public void testCountWords() {
        analyzer.countWords("test test testing");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("test", 2);
        expected.put("testing", 1);

        List<Map.Entry<String, Integer>> topWords = analyzer.getTopWords(3);
        assertTrue(entriesContainSameElements(expected, topWords));
    }

    /**
     * Test for the removeHtmlTags method.
     */
    @Test
    public void testRemoveHtmlTags() {
        String input = "<p>This is a <b>test</b>.</p>";
        String expected = "This is a test.";
        String actual = analyzer.removeHtmlTags(input);

        assertEquals(expected, actual);
    }

    /**
     * Utility method to compare a map with a list of entries.
     */
    private boolean entriesContainSameElements(Map<String, Integer> map, List<Map.Entry<String, Integer>> entries) {
        if (map.size() != entries.size()) {
            return false;
        }
        for (Map.Entry<String, Integer> entry : entries) {
            if (!map.containsKey(entry.getKey()) || !map.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }
}