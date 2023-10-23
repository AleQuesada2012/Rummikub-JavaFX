package poo.rummikub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RummikubController {
    public Button startButton;
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
    private int selectedPlayerCount = 2; // Initializado con este valor por defecto

    @FXML
    public void initialize() {
        // configura el ToggleGroup y su estado inicial dentro del método que se llama automáticamente al cargar la pantalla
        player2RadioButton.setToggleGroup(jugadoresToggleGroup);
        player3RadioButton.setToggleGroup(jugadoresToggleGroup);
        player4RadioButton.setToggleGroup(jugadoresToggleGroup);

        // Por defecto, selecciona 2 jugadores y oculta los otros 2 TextFields
        player2RadioButton.setSelected(true);
        player3TextField.setVisible(false);
        player4TextField.setVisible(false);
    }

    @FXML
    public void manejarSeleccionRadioButton(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) event.getSource();
        selectedPlayerCount = Integer.parseInt(selectedRadioButton.getText().split(" ")[0]);

        // Show/hide text fields based on the selected player count
        player3TextField.setVisible(selectedPlayerCount >= 3);
        player4TextField.setVisible(selectedPlayerCount == 4);
    }

    @FXML
    private void startGame(ActionEvent event) {
        // Now you can access the selected player count from here
        System.out.println("cantidad seleccionada: " + selectedPlayerCount);

        // You can access the player names in the text fields here
        String player1Name = player1TextField.getText();
        String player2Name = player2TextField.getText();
        String player3Name = player3TextField.getText();
        String player4Name = player4TextField.getText();



        System.out.println("player1: " + player1Name);
        System.out.println("player2: " + player2Name);
        System.out.println("player3: " + player3Name);
        System.out.println("player4: " + player4Name);


        // Se obtiene la referencia a la ventana actual (stage)
        Stage currentStage = (Stage) startButton.getScene().getWindow();

        try {
            // Se carga el archivo FXML para el tablero de juego
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RummikubGameBoard.fxml"));
            Parent gameBoardRoot = loader.load();
            Scene gameBoardScene = new Scene(gameBoardRoot);


            // Se crea una nueva Stage (ventana) para la pantalla de juego
            Stage gameBoardStage = new Stage();
            gameBoardStage.setScene(gameBoardScene);
            gameBoardStage.setTitle("Rummikub - Pantalla de Juego");

            // Se obtiene la referencia a la clase de control para la siguiente pantalla
            RummikubGameBoard control = loader.getController();
            control.initNombres(player1Name, player2Name, player3Name, player4Name); // pasa los parámetros de los nombres

            // Cierra la ventana actual y abre el tablero
            currentStage.close();
            gameBoardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
