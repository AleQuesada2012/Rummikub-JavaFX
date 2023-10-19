package poo.rummikub;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Vector;

/**
 * Clase controladora para el form de la pantalla de ganadores. Es instanciada cuando un método dentro de la pantalla de juego
 * carga la pantalla de puntajes.
 */
public class RummikubGanador {


    private Vector<Jugador> jugadores;

    @FXML
    Button botonJugarOtraVez;


    /**
     * método que se invoca automáticamente cuando el "form" que controla esta clase se carga en una escena de JavaFX.
     * Se encarga de instanciar los objetos de JavaFX existentes en la interfaz para que puedan ser manipulados o interactuar
     * con ellos por medio de métodos vinculados a eventos de cada objeto.
     */
    public void initialize() {
        // aquí se instancian los objetos que pertenecen a la interfaz de
    }

    public void recibirJugadores(Vector<Jugador> jugadores) {
        this.jugadores = jugadores;
        //TODO: crear y llamar al método que carga los puntajes de los jugadores en la pantalla
    }

    /**
     * Método para cerrar la pantalla de puntajes y volver a abrir la pantalla de juego
     * Se encarga de cargar una nueva partida donde se van a ir acumulando los puntajes
     */
    @FXML
    public void jugarOtraVez() {
        Stage currentStage = (Stage) botonJugarOtraVez.getScene().getWindow();

        try {
            // Carga el archivo FXML para la pantalla de juego
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RummikubGameBoard.fxml"));
            Parent gameBoardRoot = loader.load();
            Scene gameBoardScene = new Scene(gameBoardRoot);


            // Crea un nuevo escenario para la pantalla de juego
            Stage gameBoardStage = new Stage();
            gameBoardStage.setScene(gameBoardScene);
            gameBoardStage.setTitle("Rummikub - Game Board");

            // obtener la referencia a la clase de control para volver a la pantalla de juego
            RummikubGameBoard control = loader.getController();
            control.volverAJugar(this.jugadores);
            // cierra la ventana actual y abre el tablero
            currentStage.close();
            gameBoardStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
