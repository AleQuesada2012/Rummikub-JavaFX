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

    private int[] coordenadasFicha;

    private int turnoActual; // guarda el numero de turnos jugados

    private boolean vieneDelSoporte;

    private int indiceSoporte; // guarda la posicion en el soporte que tiene la ficha actual

    private int primerJugadorIndex;
    private Button botonPrevio;

    private Button[][] botonesTablero;

    private int cantidadPreviaDeFichas;
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
        botonesTablero = new Button[15][15];
        int rows = 15;
        int columns = 15;
        int contieneFicha = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                botonesTablero[row][col] = new Button("Button " + row + "-" + col);
                botonesTablero[row][col].setUserData(new int[]{row, col, contieneFicha}); // Store the row and column index
                botonesTablero[row][col].setText("");
                botonesTablero[row][col].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                // Add an event handler for the button's action
                botonesTablero[row][col].setOnAction(e -> manejarClickFichaMatriz(e));

                gameGrid.add(botonesTablero[row][col], col, row); // Add the button to the grid
                //GridPane.setRowIndex(button, row);
                //GridPane.setColumnIndex(button, col);
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
            if (fichaActual != null && !vieneDelSoporte) {
                // entra cuando lo que se quiere hacer es mover una ficha del tablero a la que ya se le hizo click
                if (jugadorActual.isPuedoempezar() && botonPrevio != null) {

                    // restaura que el boton anterior ya no contenga que si tiene ficha.
                    int[] datosBoton = (int[]) botonPrevio.getUserData();
                    datosBoton[2] = 0; // ahora el boton previo no deberia tener ficha
                    botonPrevio.setUserData(datosBoton);

                    clickedButton.setStyle(botonPrevio.getStyle());
                    clickedButton.setText(botonPrevio.getText());// copia la apariencia del boton anterior
                    //
                    datosBoton = (int[]) clickedButton.getUserData();
                    datosBoton[2] = 1; //ahora el boton nuevo si tiene ficha.

                    int[] datosBtnPrevio = (int[]) botonPrevio.getUserData();
                    partida.getTemporalmesa().reacomodarFicha(datosBtnPrevio[0], datosBtnPrevio[1], datosBoton[0], datosBoton[1]);

                    botonPrevio.setStyle("");
                    botonPrevio.setText("");
                    //partida.getTemporalmesa().reacomodarFicha(coordenadasFicha[0], coordenadasFicha[1], row, col);
                    cargarTableroTemporal();
                    // despues de reubicar la ficha, se pone el valor de la ficha actual en nulo, y de las coordenadas en el valor de conveniencia
                    fichaActual = null;
                    botonPrevio = null;
                } else {
                    showErrorMessage("No puede manipular las fichas del tablero hasta que haga su primera jugada.");
                }
            }
        }
        else {
            if (fichaActual == null) {
                System.out.println("entra a que la ficha actual es nula y el boton si tiene ficha");
                // no hace falta validar si la seleccion es del soporte porque el metodo se llama solo si se presiona un boton en el tablero
                if (jugadorActual.isPuedoempezar() && botonPrevio == null) {
                    System.out.println("entra a cuando saca la ficha actual del boton cliqueado");
                    fichaActual = partida.getTemporalmesa().getFichaEnXY(row, col);
                    System.out.println("Ficha Actual: " + fichaActual.getNum() + fichaActual.getColor());
                    botonPrevio = clickedButton;
                }
                else {
                    showErrorMessage("No puede manipular fichas del tablero hasta hacer una jugada de 30 puntos.");
                }
            }
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
        else {
            System.out.println("entra a que la ficha actual no era nula y va a cambiar la referencia");
            if (vieneDelSoporte) {
                // lo que quiere hacer aqui es escoger otra ficha del soporte
                this.fichaActual = jugadorActual.escogerficha(indiceOriginal);
                System.out.println("ficha actual: " + fichaActual.getNum() + fichaActual.getColor());
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

        primerJugadorIndex = partida.determinarOrden();
        Jugador primerJugador = partida.getJugadores().get(primerJugadorIndex);
        this.jugadorActual = primerJugador;
        System.out.println(primerJugador); // para depurar y ver si se crea el jugador correctamente
        partida.agarrarfichas();

        boolean gameOver = false;
        turnoActual = 1;
        fichaActual = null;
        //coordenadasFicha = new int[]{-1, -1};
        botonPrevio = null;
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
        fichaActual = null;
        botonPrevio = null;

        if (partida.getTemporalmesa().valorDeJugada() && partida.getTemporalmesa().matrizValida()) {
            jugadorActual.setPuedoempezar(true);
        }
        if (!jugadorActual.isPuedoempezar()) {
            showErrorMessage("Su última jugada fue inválida. Intente de nuevo");
            partida.getTemporalmesa().restaurarFichas(jugadorActual);
            partida.getTemporalmesa().copiarMesa(partida.getTablero());
            setSoporteInicial(jugadorActual);
            cargarTableroTemporal();
        }
        else {
            // entra cuando se puede cambiar de turno porque supera los 30 puntos y es valido
            if (partida.getTemporalmesa().matrizValida() && cantidadPreviaDeFichas < partida.getTemporalmesa().getCantFichas()) {
                partida.getTablero().copiarMesa(partida.getTemporalmesa());
                partida.getTablero().sonpartede();

                cantidadPreviaDeFichas = partida.getTablero().getCantFichas();

                int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
                primerJugadorIndex = indiceSigJugador;
                jugadorActual = partida.getJugadores().get(primerJugadorIndex);

                turnoActual ++;
                turnLabel.setText("Turno: " + turnoActual);
                currentPlayerLabel.setText("Jugando: " + jugadorActual.getNombre());

                cargarTableroValido();
                setSoporteInicial(jugadorActual);
            }
            else {
                showErrorMessage("Debe colocar o tomar una ficha para pasar turno.");
                partida.getTemporalmesa().restaurarFichas(jugadorActual);
                partida.getTemporalmesa().copiarMesa(partida.getTablero());
                setSoporteInicial(jugadorActual);
                cargarTableroTemporal();
            }
        }
    }

    @FXML
    private void handleDrawTileButton() {
        fichaActual = null;
        botonPrevio = null;
        if (partida.getTemporalmesa().matrizValida()) {
            Ficha tomada = partida.getTablero().agarrarpila();
            if (tomada != null) { // si no es nulo es porque aun quedan fichas en la pila
                jugadorActual.getFichasEnMano().ingresarficha(tomada);
                int indiceSigJugador = (primerJugadorIndex + 1) % partida.getJugadores().size();
                primerJugadorIndex = indiceSigJugador;
                jugadorActual = partida.getJugadores().get(primerJugadorIndex);
                cargarTableroValido();
                setSoporteInicial(jugadorActual);

                cantidadPreviaDeFichas = partida.getTablero().getCantFichas();

                turnoActual ++;
                turnLabel.setText("Turno: " + turnoActual);
                currentPlayerLabel.setText("Jugando: " + jugadorActual.getNombre());
            }
        }
        else {
            showErrorMessage("La disposición del tablero no era válida. Por favor reorganice sus fichas.");
            partida.getTemporalmesa().restaurarFichas(jugadorActual);
            partida.getTemporalmesa().copiarMesa(partida.getTablero());
            setSoporteInicial(jugadorActual);
            cargarTableroTemporal();

        }
    }

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
