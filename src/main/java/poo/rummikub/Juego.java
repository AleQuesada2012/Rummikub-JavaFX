package poo.rummikub;//package src;

import java.util.Vector;

public class Juego {

    private Mesa Tablero;

    private Mesa temporalmesa;

    private Vector<Jugador> jugadores;





    public Juego() {
        Tablero = new Mesa();
        temporalmesa = new Mesa(1);
        jugadores = new Vector<>();
    }

    public void setJugadores(Vector<Jugador> jugadores) {
        this.jugadores = jugadores;
    }





    public void agregarjugador(Jugador jugador){
        jugadores.add(jugador);
    }

    public int determinarOrden() {
        int maxficha = -1;
        int indicedeprimerjugador = 0;

        for (int i = 0; i < this.jugadores.size(); i++) {
            Jugador jugador = this.jugadores.get(i);
            jugador.agregarFicha(Tablero.agarrarpila());
            for (Ficha ficha : jugador.getFichasEnMano().getFichas()) {
                if (ficha.getNum() > maxficha) {
                    maxficha= ficha.getNum();
                    indicedeprimerjugador= i;
                }
            }
        }

        return indicedeprimerjugador;
    }

    public void sumarPuntos(){
        int cont = 0;
        Jugador ganador = null;
        for(Jugador jugador : getJugadores()){
            if(jugador.getFichasEnMano().getCantfichas()==0){
                jugador.setGanador(true);
                ganador = jugador;

            }
            else {
                int puntos = jugador.getFichasEnMano().getsumadefichas();
                jugador.setPuntos(puntos * -1); // le coloca en negativo los puntos al jugador
                jugador.setPuntosTotales(jugador.getPuntosTotales()  + (puntos * -1));
                cont += puntos;

            }
        }
        if(ganador!=null){
            ganador.setPuntos(cont);
        }
    }

    public void sumarPuntosPilaEnCero() {
        Jugador ganador = null;
        int sumaMenorDePuntos = 1000000;
        for (Jugador jugador : getJugadores()) {
            if (jugador.getFichasEnMano().getsumadefichas() < sumaMenorDePuntos) {
                ganador = jugador;
                sumaMenorDePuntos = jugador.getFichasEnMano().getsumadefichas();
            }
        }
        ganador.setGanador(true);

        // se vuelven a recorrer, esta vez ya sabiendo el que tenía la cantidad menor de puntos
        for (Jugador jugador : getJugadores()) {
            int puntos = jugador.getFichasEnMano().getsumadefichas();
            if (!jugador.isGanador()) {
                puntos *= -1;
            }
            jugador.setPuntos(puntos);
            jugador.setPuntosTotales(jugador.getPuntosTotales() + puntos);
        }
    }

    public void agarrarfichas(){
        for(Jugador jugador :  jugadores) {
            for (int i = 0; i < 13; i++) {
                jugador.agregarFicha((Tablero.agarrarpila()));
            }
        }

    }

    public Vector<Ficha> copiarFichasEnMano(Jugador jugador) {
        Vector<Ficha> copiedTiles = new Vector<>();

        for (Ficha ficha : jugador.getFichasEnMano().getFichas()) {
            Ficha copiedFicha = new Ficha(ficha.getNum(), ficha.getColor());
            copiedTiles.add(copiedFicha);
        }

        return copiedTiles;
    }



    public void retirarFicha(int x, int y){
        Ficha temp = Tablero.getMatrizFichas()[x][y];
        Tablero.getMatrizFichas()[x][y] = null;

    }

    public Mesa getTemporalmesa() {
        return temporalmesa;
    }

    public void actualizarMesa(){
        if(temporalmesa.matrizValida()){
            Tablero.copiarMesa(temporalmesa);
        }
    }

    public void terminarTurno(){
        actualizarMesa();

    }

    public Mesa getTablero() {
        return Tablero;
    }

    public Vector<Jugador> getJugadores() {
        return this.jugadores;
    }

}
