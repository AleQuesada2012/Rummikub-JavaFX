package poo.rummikub;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;


public class RummikubGameBoard {


    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String nombre4;

    private Juego partida; // instancia que regula los tableros, jugadores, y cumplimiento de las reglas del juego
    private Jugador jugadorActual; //TODO: utilizar este atributo para la logica del juego

    private Ficha fichaActual; // apunta a una ficha temporal que se podria mover

    private int turnoActual; // guarda el numero de turnos jugados

    private boolean vieneDelSoporte;

    private int indiceSoporte; // guarda la posicion en el soporte que tiene la ficha actual
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
        int contieneFicha = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                Button button = new Button("Button " + row + "-" + col);
                button.setUserData(new int[]{row, col, contieneFicha}); // Store the row and column index
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
        int hayFichaDentro = 0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button button = new Button("SoporteBtn " + i + '-' + columnas);
                button.setUserData(new int[] {i, j, posSoporte, hayFichaDentro}); // almacena la fila y columna igual que en el tablero
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
        int tieneFicha = rowIndex[2];
        if(tieneFicha == 0) {
            System.out.println("entró a que el botón no tiene ficha");
            if (fichaActual != null && vieneDelSoporte) {
                System.out.println("entró a que la ficha no es nula y viene del soporte");
                // quiere decir que vamos a sacar una ficha del soporte y ponerla en el boton que se hizo click
                partida.getTemporalmesa().ingresarFicha(fichaActual, row, col, jugadorActual); // ya coloque la ficha
                fichaActual = null;
                indiceSoporte = 0;
                vieneDelSoporte = false;
                this.setSoporteInicial(jugadorActual);
                this.cargarTableroTemporal();
            }
        }
        else {
            //TODO: add logic for when players can and can't move tiles from the board (until they've played the first move).
        }

        System.out.println("Button clicked at Row " + row + ", Column " + col);
        // Handle the button click as needed
    }

    private void manejarClickFichaSoporte(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();

        /*if (clickedButton.getText() == "") {
            // validar mover la ficha
            //temporalSoporte = {null, null, null}; // EJEMPLO
            //TODO: implementar funcionalidad de mover la ficha
        }*/
        //temporalSoporte<1/0, "4F">
        int[] rowIndex = (int[]) clickedButton.getUserData();
        int row = rowIndex[0];
        int col = rowIndex[1];
        int indiceOriginal = rowIndex[2];
        int tieneFicha = rowIndex[3];

        if(fichaActual == null) {
            System.out.println("entro a que ficha actual no es nula");
            if (tieneFicha == 1) {
                System.out.println("entró a que el botón clicado sí tiene ficha");
                this.fichaActual = jugadorActual.escogerficha(indiceOriginal);
                System.out.println("ficha Actual: " + fichaActual.getNum() + fichaActual.getColor());
                indiceSoporte = indiceOriginal;
                vieneDelSoporte = true;
                this.setSoporteInicial(jugadorActual);
                this.cargarTableroTemporal();
            }
        }


        System.out.println("Button clicked at Row " + row + ", Column " + col);
        System.out.println("La ficha en el soporte esta en el indice: " + indiceOriginal);
        // Handle the button click as needed
    }

/*    public void initJuego(Juego partidaCreada) {
        this.partida = partidaCreada;
    }*/
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
        this.jugadorActual = primerJugador;
        System.out.println(primerJugador); // para depurar y ver si se crea el jugador correctamente
        partida.agarrarfichas();

        boolean gameOver = false;
        turnoActual = 1;
        currentPlayerLabel.setText("Jugando: " + primerJugador.getNombre());
        setSoporteInicial(primerJugador);
        cargarTableroTemporal();
        turnLabel.setText("Turno: " + turnoActual);
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
        System.out.println("Sus fichas: ");
        jugadorActual.printFichas();
        int sizeSoporte = jugadorActual.cantFichas();
        for (Node boton : soporteGridPane.getChildren()) {
            Button button = (Button) boton; // convierte el objeto de tipo nodo a un objeto de tipo boton
            if (indiceSoporte < sizeSoporte){// deja de recorrer el arreglo de fichas cuando llegue a la ultima
            Ficha fichaActual = jugadorActual.getFichasEnMano().getficha(indiceSoporte);
                // como el comodin tiene un 0, se hace una validacion adicional para cambiar el texto a COM
                if(fichaActual.getNum() == 0) {
                    button.setText("C");
                }
                else {
                    button.setText(Integer.toString(fichaActual.getNum()));
                }
                int[] infoAlmacenada = (int[]) button.getUserData();
                infoAlmacenada[3] = 1;
                button.setUserData(infoAlmacenada);


                // logica para cambiar el color del boton segun el valor de la ficha
                switch (fichaActual.getColor()) {
                    case "N" -> button.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5); -fx-text-fill: black;");
                    case "A" -> button.setStyle("-fx-background-color: rgba(0, 0, 255, 0.5); -fx-text-fill: black;");
                    case "Y" -> button.setStyle("-fx-background-color: rgba(255, 255, 0, 0.5); -fx-text-fill: black;");
                    case "R" -> button.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-text-fill: black;");
                    case "comodin" -> button.setStyle("-fx-background-color: rgba(0, 200, 200, 0.5); -fx-text-fill: black");
                }
            }
            else {
                button.setText("");
                button.setStyle("");
                int[] infoAlmacenada = (int[]) button.getUserData();
                infoAlmacenada[3] = 0;
                button.setUserData(infoAlmacenada);
            }
            indiceSoporte++;
        }
    }

    private void cargarTableroTemporal() {
        Mesa tableroTemporal = partida.getTemporalmesa(); // matriz de fichas
        System.out.println("Tablero actual");
        tableroTemporal.imprimirMesa();
        int filaMesa = 0;
        int columnaMesa = 0;
        Ficha fichaActual;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Button botonActual = getButtonByRowColumnIndex(i, j, gameGrid);
                fichaActual = tableroTemporal.getMatrizFichas()[i][j];
                if (fichaActual != null) {
                    if(fichaActual.getNum() == 0) {
                        botonActual.setText("C");
                    }
                    else {
                        botonActual.setText(Integer.toString(fichaActual.getNum()));
                    }

                    // cambiar el valor del arreglo de user Data para que marque que si contiene ficha
                    int[] infoAlmacenada = (int[]) botonActual.getUserData();
                    infoAlmacenada[2] = 1;
                    botonActual.setUserData(infoAlmacenada);

                    // se cambia la apariencia del boton para que refleje la ficha que representa
                    switch (fichaActual.getColor()) {
                        case "N" -> botonActual.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5); -fx-text-fill: black;");
                        case "A" -> botonActual.setStyle("-fx-background-color: rgba(0, 0, 255, 0.5); -fx-text-fill: black;");
                        case "Y" -> botonActual.setStyle("-fx-background-color: rgba(255, 255, 0, 0.5); -fx-text-fill: black;");
                        case "R" -> botonActual.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-text-fill: black;");
                        case "comodin" -> botonActual.setStyle("-fx-background-color: rgba(0, 200, 200, 0.5); -fx-text-fill: black");
                    }
                }
                else {
                    botonActual.setText("");
                    botonActual.setStyle("");
                    int[] infoAlmacenada = (int[]) botonActual.getUserData();
                    infoAlmacenada[2] = 0;
                    botonActual.setUserData(infoAlmacenada);
                }
            }
        }
    }

    private void cargarTableroValido() {
        Mesa tableroValido = partida.getTablero(); // matriz de fichas
        System.out.println("Tablero Valido:");
        tableroValido.imprimirMesa();
        int filaMesa = 0;
        int columnaMesa = 0;
        Ficha fichaActual;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Button botonActual = getButtonByRowColumnIndex(i, j, gameGrid);
                fichaActual = tableroValido.getMatrizFichas()[i][j];
                if (fichaActual != null) {
                    if(fichaActual.getNum() == 0) {
                        botonActual.setText("C");
                    }
                    else {
                        botonActual.setText(Integer.toString(fichaActual.getNum()));
                    }

                    // cambiar el valor del arreglo de user Data para que marque que si contiene ficha
                    int[] infoAlmacenada = (int[]) botonActual.getUserData();
                    infoAlmacenada[2] = 1;
                    botonActual.setUserData(infoAlmacenada);

                    // se cambia la apariencia del boton para que refleje la ficha que representa
                    switch (fichaActual.getColor()) {
                        case "N" -> botonActual.setStyle("-fx-background-color: rgba(128, 128, 128, 0.5); -fx-text-fill: black;");
                        case "A" -> botonActual.setStyle("-fx-background-color: rgba(0, 0, 255, 0.5); -fx-text-fill: black;");
                        case "Y" -> botonActual.setStyle("-fx-background-color: rgba(255, 255, 0, 0.5); -fx-text-fill: black;");
                        case "R" -> botonActual.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-text-fill: black;");
                        case "comodin" -> botonActual.setStyle("-fx-background-color: rgba(0, 200, 200, 0.5); -fx-text-fill: black");
                    }
                }
                else {
                    botonActual.setText("");
                    botonActual.setStyle("");
                    int[] infoAlmacenada = (int[]) botonActual.getUserData();
                    infoAlmacenada[2] = 0;
                    botonActual.setUserData(infoAlmacenada);
                }
            }
        }
    }


    private Button getButtonByRowColumnIndex(int row, int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column && node instanceof Button) {
                return (Button) node;
            }
        }
        return null; // Button not found
    }

    @FXML
    private void handleValidateTurnButton() {
        // Define the action to be performed when the "Validate Turn" button is clicked.
        turnoActual ++;
        turnLabel.setText("Turno: " + turnoActual);
        boolean puedePasarTurno = partida.getTemporalmesa().matrizValida();
        if (!puedePasarTurno) {
            showErrorMessage("Su última jugada fue inválida. Intente de nuevo");
            partida.getTemporalmesa().restaurarFichas(jugadorActual);
            partida.getTemporalmesa().copiarMesa(partida.getTablero());
            setSoporteInicial(jugadorActual);
            cargarTableroTemporal();
        }
        else {

        }
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
    private void showErrorMessage(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(errorMessage);

        // Set the dialog modality to APPLICATION_MODAL
        alert.initModality(Modality.APPLICATION_MODAL);

        // Create an "OK" button to close the dialog
        ButtonType buttonTypeOk = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeOk);

        // Show the dialog and wait for the user's response
        alert.showAndWait();
    }

}
