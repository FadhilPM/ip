package bezdelnik;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

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

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        userInput.setOnAction(event -> sendButton.fire());

        // Listener for text field changes
        userInput.textProperty().addListener((observable, oldValue, newValue) -> {
            sendButton.setDisable(newValue.trim().isEmpty());
        });
    }

    public void setBezdelnik(Bezdelnik b) {
        Pair<String, Bezdelnik> initialisedBezdelnik = b.initialise();
        String response = initialisedBezdelnik.first();
        this.bezdelnik = initialisedBezdelnik.second();
        dialogContainer.getChildren().addAll(DialogBox.getDukeDialog(response, dukeImage));
    }

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
