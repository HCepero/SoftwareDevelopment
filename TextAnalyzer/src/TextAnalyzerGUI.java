import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;

/**
 * A Graphical User Interface (GUI) for the TextAnalyzer application.
 * It allows users to select a text file, then displays the most frequently
 * occurring words in the file in a text area.
 *
 * @version 1.0
 */
public class TextAnalyzerGUI extends Application {
    private TextAnalyzer analyzer = new TextAnalyzer();
    private TextArea resultArea = new TextArea();

    /**
     * The entry point of the application. Sets up the scene and displays the window.
     *
     * @param primaryStage The main window of the application.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Text Analyzer");

        // Open file button
        Button openButton = new Button("Open File");
        openButton.setOnAction(e -> openFileAction(primaryStage));

        // Text area to display results
        resultArea.setEditable(false);
        resultArea.setPrefSize(400, 300); // double the size of text area

        // Scene
        VBox vbox = new VBox(openButton, resultArea);
        vbox.setSpacing(10);

        primaryStage.setScene(new Scene(vbox, 400, 400)); // accommodates larger text area
        primaryStage.show();
    }

    /**
     * Handles the action of opening a file. Uses a FileChooser to let the user select a file,
     * then processes the file with the TextAnalyzer and displays the results.
     *
     * @param primaryStage The main window of the application.
     */
    private void openFileAction(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file != null) {
            try {
                analyzer.processFile(file.getPath());
                List<Map.Entry<String, Integer>> entries = analyzer.getTopWords(20);
                displayResults(entries);
            } catch (IOException e) {
                showAlert("Error processing file", "Could not process the selected file.");
            }
        }
    }

    /**
     * Shows an alert dialog with a specified title and message.
     *
     * @param title The title of the alert.
     * @param message The message to display in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays the results of the TextAnalyzer in the resultArea TextArea.
     *
     * @param entries A list of Map entries containing the most frequently occurring words and their frequencies.
     */
    private void displayResults(List<Map.Entry<String, Integer>> entries) {
        String resultText = entries.stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining("\n"));
        resultArea.setText(resultText);
    }

    /**
     * The main method for launching the application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}