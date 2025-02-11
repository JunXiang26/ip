package dubey;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the Main GUI.
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
    @FXML
    private TextArea chatArea;
    @FXML
    private TextField inputField;

    private Dubey dubey;
    private final Ui ui = new Ui();

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/person.png"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/bot.png"));

    /**
     * Initializes the JavaFX components when the controller is loaded with a welcome message from Duke to the dialog
     * container. Binds the scroll pane's vertical scroll value to the height of the dialog container, ensuring
     * automatic scrolling as new messages are added.
     *
     */
    @FXML
    public void initialize() {
        dialogContainer.getChildren().add(DialogBox.getDukeDialog(ui.showWelcomeMessage(), dukeImage));
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Instantiates dubey
     *
     * @param d
     */
    public void setDuke(Dubey d) {
        dubey = d;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Dubey's reply and then appends them to
     * the dialog container. Clears the user input after processing. If input 'bye' is read, application terminates
     * after a delay of 2 seconds to show the Goodbye Message.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        String response = dubey.getResponse(input);
        assert response != null : "Chatbot response should never be null";
        assert !response.trim().isEmpty() : "Chatbot response should not be empty";

        if (input.equalsIgnoreCase("bye")) {
            handleByeInput(input);
            return;
        }

        handleResponse(input, response);
    }

    private void handleByeInput(String input) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(ui.showGoodbyeMessage(), dukeImage)
        );

        // Delay before closing
        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> closeApplication());
        delay.play();
    }

    private void handleResponse(String input, String response) {
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage)
        );
        userInput.clear();
    }

    private void closeApplication() {
        Stage stage = (Stage) dialogContainer.getScene().getWindow();
        stage.close();
    }
}
