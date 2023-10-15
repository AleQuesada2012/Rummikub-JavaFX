package poo.rummikub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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
    private ToggleGroup jugadoresToggleGroup;

    private int selectedPlayerCount = 2; // Initialize with the default value

    @FXML
    public void initialize() {
        // Set up the ToggleGroup and initial states here
        player2RadioButton.setToggleGroup(jugadoresToggleGroup);
        player3RadioButton.setToggleGroup(jugadoresToggleGroup);
        player4RadioButton.setToggleGroup(jugadoresToggleGroup);

        // By default, select 2 players and show 2 text fields
        player2RadioButton.setSelected(true);
        player3TextField.setVisible(false);
        player4TextField.setVisible(false);
    }

    @FXML
    public void handleRadioButtonSelection(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) event.getSource();
        int selectedPlayerCount = Integer.parseInt(selectedRadioButton.getText().split(" ")[0]);

        // Show/hide text fields based on the selected player count
        player3TextField.setVisible(selectedPlayerCount >= 3);
        player4TextField.setVisible(selectedPlayerCount == 4);
    }

    @FXML
    private void startGame(ActionEvent event) {
        // Now you can access the selected player count from here
        System.out.println("Selected player count: " + selectedPlayerCount);

        // You can access the player names in the text fields here
        String player1Name = player1TextField.getText();
        String player2Name = player2TextField.getText();
        String player3Name = player3TextField.getText();
        String player4Name = player4TextField.getText();

        // Start the game or transition to the game board
        // You can implement this part in your application logic
    }
}
