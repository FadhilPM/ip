package bezdelnik.ui;

import bezdelnik.utils.Bezdelnik;
import bezdelnik.utils.Pair;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main window of the Bezdelnik GUI.
 * <p>
 * This class handles the interaction between the user and the Bezdelnik
 * application through the graphical user interface. It manages the display
 * of dialog messages and processes user input.
 * </p>
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;
    private Bezdelnik bezdelnik;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Bezdelnik.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/Zvezda.png"));

    /**
     * Initializes the main window components.
     * <p>
     * Sets up the scroll pane to automatically scroll to the bottom when new content is added,
     * configures the text field to trigger the send button on Enter key press, and
     * disables the send button when the input field is empty.
     * </p>
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userInput.setOnAction(event -> sendButton.fire());

        // Listener for text field changes
        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            sendButton.setDisable(newValue.trim().isEmpty());
        });
    }

    /**
     * Sets the Bezdelnik instance and initializes the chat with a welcome message.
     *
     * @param b The Bezdelnik instance to use for processing commands
     */
    public void setBezdelnik(Bezdelnik b) {
        Pair<String, Bezdelnik> initialisedBezdelnik = b.initialise();
        String response = initialisedBezdelnik.first();
        this.bezdelnik = initialisedBezdelnik.second();
        dialogContainer.getChildren().addAll(DialogBox.getDukeDialog(response, dukeImage));
    }

    /**
     * Handles user input from the text field.
     * <p>
     * Processes the input text, sends it to the Bezdelnik instance for execution,
     * and displays the response in the dialog container. Exits the application if
     * the input matches exit commands.
     * </p>
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        userInput.clear();
        if (input.matches("(bye|(/)?ex(it)?)")) {
            Platform.exit();
        } else {
            Pair<String, Bezdelnik> response = bezdelnik.getResponse(input);

            String toPrint = response.first();
            this.bezdelnik = response.second();

            dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(toPrint, dukeImage)
            );
        }
    }
}
