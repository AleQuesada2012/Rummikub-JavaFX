package poo.rummikub;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class RummikubGameBoard {


    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String nombre4;

    private Juego partida;

    private int indiceSoporte;
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
                button.setOnAction(e -> manejarClickFichaMatriz(e));

                gameGrid.add(button, col, row); // Add the button to the grid
            }
        }
    }

    private void crearBotonesSoporte() {
        int filas = 3;
        int columnas = 15;
        int posSoporte = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button button = new Button("SoporteBtn " + i + '-' + columnas);
                button.setUserData(new int[] {i, j, posSoporte}); // almacena la fila y columna igual que en el tablero
                button.setText("");
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setOnAction(e -> manejarClickFichaSoporte(e));
                soporteGridPane.add(button, j, i);
                posSoporte ++;
            }
        }
    }

    private void manejarClickFichaMatriz(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int[] rowIndex = (int[]) clickedButton.getUserData();
        int row = rowIndex[0];
        int col = rowIndex[1];
        System.out.println("Button clicked at Row " + row + ", Column " + col);
        // Handle the button click as needed
    }

    private void manejarClickFichaSoporte(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int[] rowIndex = (int[]) clickedButton.getUserData();
        int row = rowIndex[0];
        int col = rowIndex[1];
        int indiceOriginal = rowIndex[2];
        System.out.println("Button clicked at Row " + row + ", Column " + col);
        System.out.println("La ficha en el soporte esta en el indice: " + indiceOriginal);
        // Handle the button click as needed
    }

    public void initJuego(Juego partidaCreada) {
        this.partida = partidaCreada;
    }
    public void initNombres(String input1, String input2, String input3, String input4) {

        setNombre1(input1);
        setNombre2(input2);
        setNombre3(input3);
        setNombre4(input4);

        // Set the currentPlayerLabel text after names are initialized
        //currentPlayerLabel.setText("Jugador Actual: " + this.nombre1);
        startGame();
    }

    private void startGame() {
        this.partida = getJuego();

        int primerJugadorIndex = partida.determinarOrden();
        Jugador primerJugador = partida.getJugadores().get(primerJugadorIndex);
        System.out.println(primerJugador); // para depurar y ver si se crea el jugador correctamente
        partida.agarrarfichas();

        boolean gameOver = false;

        currentPlayerLabel.setText("Jugando: " + primerJugador.getNombre());
        setSoporteInicial(primerJugador);
    }

    private Juego getJuego() {
        Juego partida = new Juego();
        Jugador j1 = new Jugador();
        j1.setNombre(nombre1);
        j1.setFichasEnMano();
        partida.agregarjugador(j1);

        Jugador j2 = new Jugador();
        j2.setNombre(nombre2);
        j2.setFichasEnMano();
        partida.agregarjugador(j2);

        if (!nombre3.isEmpty()) {
            Jugador j3 = new Jugador();
            j3.setNombre(nombre3);
            j3.setFichasEnMano();
            partida.agregarjugador(j3);
        }

        if (!nombre4.isEmpty()) {
            Jugador j4 = new Jugador();
            j4.setNombre(nombre4);
            j4.setFichasEnMano();
            partida.agregarjugador(j4);
        }
        return partida;
    }

    private void setSoporteInicial(Jugador jugadorActual) {
        indiceSoporte = 0;
        int sizeSoporte = jugadorActual.cantFichas();
        for (Node boton : soporteGridPane.getChildren()) {
            if (indiceSoporte < sizeSoporte){// deja de recorrer el arreglo de fichas cuando llegue a la ultima
            Ficha fichaActual = jugadorActual.getFichasEnMano().getficha(indiceSoporte);
                Button button = (Button) boton; // convierte el objeto de tipo nodo a un objeto de tipo boton
                button.setText(Integer.toString(fichaActual.getNum()));

                // logica para cambiar el color del boton segun el valor de la ficha
                switch (fichaActual.getColor()) {
                    case "N" -> button.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5); -fx-text-fill: black;");
                    case "A" -> button.setStyle("-fx-background-color: rgba(0, 0, 255, 0.5); -fx-text-fill: black;");
                    case "Y" -> button.setStyle("-fx-background-color: rgba(255, 255, 0, 0.5); -fx-text-fill: black;");
                    case "R" -> button.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-text-fill: black;");
                }
            }
            indiceSoporte++;
        }
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

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public void setNombre3(String nombre3) {
        this.nombre3 = nombre3;
    }

    public void setNombre4(String nombre4) {
        this.nombre4 = nombre4;
    }
}
