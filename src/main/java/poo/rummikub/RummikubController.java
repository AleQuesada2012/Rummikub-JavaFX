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
        selectedPlayerCount = Integer.parseInt(selectedRadioButton.getText().split(" ")[0]);

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

/*        Juego juego = new Juego();

        Jugador p1 = new Jugador();
        p1.setNombre(player1Name);
        juego.agregarjugador(p1);

        Jugador p2 = new Jugador();
        p2.setNombre(player2Name);
        juego.agregarjugador(p2);

        if (!player3Name.isEmpty()) {
            Jugador p3 = new Jugador();
            p3.setNombre(player3Name);
            juego.agregarjugador(p3);
        }

        if (!player4Name.isEmpty()) {
            Jugador p4 = new Jugador();
            p4.setNombre(player4Name);
            juego.agregarjugador(p4);
        }*/

        System.out.println("player1: " + player1Name);
        System.out.println("player2: " + player2Name);
        System.out.println("player3: " + player3Name);
        System.out.println("player4: " + player4Name);


        // Get the reference to the current window (stage)
        Stage currentStage = (Stage) startButton.getScene().getWindow();

        try {
            // Load the FXML file for the game board
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RummikubGameBoard.fxml"));
            Parent gameBoardRoot = loader.load();
            Scene gameBoardScene = new Scene(gameBoardRoot);


            // Create a new stage for the game board
            Stage gameBoardStage = new Stage();
            gameBoardStage.setScene(gameBoardScene);
            gameBoardStage.setTitle("Rummikub - Game Board");

            // obtener la referencia a la clase de control para la siguiente pantalla
            RummikubGameBoard control = loader.getController();
            control.initNombres(player1Name, player2Name, player3Name, player4Name); // pasa los parámetros de los nombres

            // cierra la ventana actual y abre el tablero
            currentStage.close();
            gameBoardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Start the game or transition to the game board
        // You can implement this part in your application logic
    }
}
