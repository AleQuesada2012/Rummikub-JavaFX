package poo.rummikub;

public class FilaPuntajes {
    private String numRonda;
    private String jugador1;
    private String jugador2;
    private String jugador3;
    private String jugador4;

    public FilaPuntajes(String roundNumber, String player1, String player2, String player3, String player4) {
        setNumRonda(roundNumber);
        setJugador1(player1);
        setJugador2(player2);
        setJugador3(player3);
        setJugador4(player4);
    }

    public String getNumRonda() {
        return numRonda;
    }

    public void setNumRonda(String numRonda) {
        this.numRonda = numRonda;
    }

    public String getJugador1() {
        return jugador1;
    }

    public void setJugador1(String jugador1) {
        this.jugador1 = jugador1;
    }

    public String getJugador2() {
        return jugador2;
    }

    public void setJugador2(String jugador2) {
        this.jugador2 = jugador2;
    }

    public String getJugador3() {
        return jugador3;
    }

    public void setJugador3(String jugador3) {
        this.jugador3 = jugador3;
    }

    public String getJugador4() {
        return jugador4;
    }

    public void setJugador4(String jugador4) {
        this.jugador4 = jugador4;
    }
}