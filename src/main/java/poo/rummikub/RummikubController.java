package poo.rummikub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class RummikubController {
    @FXML
    private RadioButton player2RadioButton;

    @FXML
    private RadioButton player3RadioButton;

    @FXML
    private RadioButton player4RadioButton;

    @FXML
    private TextField player1TextField;

    @FXML
    private TextField player2TextField;

    @FXML
    private TextField player3TextField;

    @FXML
    private TextField player4TextField;

    @FXML
    private void startGame(ActionEvent event) {
        // Get the selected number of players
        int numberOfPlayers = 0;
        if (player2RadioButton.isSelected()) {
            numberOfPlayers = 2;
        } else if (player3RadioButton.isSelected()) {
            numberOfPlayers = 3;
        } else if (player4RadioButton.isSelected()) {
            numberOfPlayers = 4;
        }

        // You can access the player names in the text fields here
        String player1Name = player1TextField.getText();
        String player2Name = player2TextField.getText();
        String player3Name = player3TextField.getText();
        String player4Name = player4TextField.getText();

        // Start the game or transition to the game board
        // You can implement this part in your application logic
    }
}
