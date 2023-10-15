package poo.rummikub;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
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

    @FXML
    private GridPane soporteGridPane;

    public void initialize() {
        createButtonsForGrid(); // Call a method to create buttons
        crearBotonesSoporte();
    }

    private void createButtonsForGrid() {
        int rows = 15;
        int columns = 15;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Button button = new Button("Button " + row + "-" + col);
                button.setUserData(new int[]{row, col}); // Store the row and column index
                button.setText("");
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                // Add an event handler for the button's action
                button.setOnAction(e -> handleGridButtonAction(e));

                gameGrid.add(button, col, row); // Add the button to the grid
            }
        }
    }

    private void crearBotonesSoporte() {
        int filas = 3;
        int columnas = 15;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button button = new Button("SoporteBtn " + i + '-' + columnas);
                button.setUserData(new int[] {i, j}); // almacena la fila y columna igual que en el tablero
                button.setText("");
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setOnAction(e -> handleGridButtonAction(e));

                soporteGridPane.add(button, i, j);
            }
        }
    }

    private void handleGridButtonAction(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int[] rowIndex = (int[]) clickedButton.getUserData();
        int row = rowIndex[0];
        int col = rowIndex[1];
        System.out.println("Button clicked at Row " + row + ", Column " + col);
        // Handle the button click as needed
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
