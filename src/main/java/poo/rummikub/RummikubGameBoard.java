package poo.rummikub;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RummikubGameBoard {


    String nombre1;
    String nombre2;
    String nombre3;
    String nombre4;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private HBox topHBox;

    @FXML
    private Label currentPlayerLabel;

    @FXML
    private Label turnLabel;

    @FXML
    private Button validateTurnButton;

    @FXML
    private Button drawTileButton;

    @FXML
    private VBox mainVBox;

    @FXML
    private GridPane gameGrid;

    public void initialize() {
        // This method is automatically called when the FXML is loaded.
        // You can perform initialization and setup here.
        currentPlayerLabel.setText("Jugador Actual: " + this.nombre1);
    }

    public void initNombres(String input1, String input2, String input3, String input4) {
        this.nombre1 = input1;
        this.nombre2 = input2;
        this.nombre3 = input3;
        this.nombre4 = input4;

        // Set the currentPlayerLabel text after names are initialized
        currentPlayerLabel.setText("Jugador Actual: " + this.nombre1);
    }

    @FXML
    private void handleValidateTurnButton() {
        // Define the action to be performed when the "Validate Turn" button is clicked.
    }

    @FXML
    private void handleDrawTileButton() {
        // Define the action to be performed when the "Draw Tile" button is clicked.
    }

    // You can add more methods and logic as needed for your game board.
}
