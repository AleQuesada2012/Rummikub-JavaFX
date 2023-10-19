package poo.rummikub;// package src;

import java.util.*;

public class Jugador {
    private String Nombre;
    private int puntos;

    private Vector<Integer> listaPuntos;
    private Soporte fichasEnMano;
    private boolean puedoempezar;
    private boolean ganador;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public Vector<Integer> getPuntos() {
        return this.listaPuntos;
    }

    public void setPuntos(int puntos) {
        // se cambian los puntos por un vector para tener registro de las partidas anteriores.
        this.listaPuntos.add(puntos);
    }

    public void crearVectorPuntos() {
        this.listaPuntos = new Vector<>();
    }

    public void setFichasEnMano() {
            this.fichasEnMano = new Soporte();
    }

    public Soporte getFichasEnMano() {
        return fichasEnMano;
    }

    public boolean isGanador() {
        return ganador;
    }

    public void setGanador(boolean ganador) {
        this.ganador = ganador;
    }

    public boolean puedoEmpezar() {
        return puedoempezar;
    }

    public void setPuedoempezar(boolean miTurno) {
        puedoempezar = miTurno;
    }

    public int cantFichas(){return fichasEnMano.getCantfichas();}
    
    public Ficha escogerficha(int x){
        return fichasEnMano.getficha(x);
    }

    public void setFichasEnMano(Soporte nuevoSoporte) {
        this.fichasEnMano = nuevoSoporte;
    }

    /**
     * Metodo para imprimir las fichas contenidas en el soporte del jugador
     */
    public void printFichas() {
        for (int i = 0; i < getFichasEnMano().getCantfichas(); i++) {
            Ficha fichaTemp = getFichasEnMano().getficha(i);
            System.out.println(i + ": " + fichaTemp.getNum() + " " + fichaTemp.getColor());
        }
    }


    public void agregarFicha(Ficha ficha) {
        fichasEnMano.ingresarficha(ficha);
    }

    public void reestablecerJugador() {
        setGanador(false);
        setFichasEnMano(); // hace un soporte nuevo por lo que basta para reestablecer
        setPuedoempezar(false);
        //TODO: eliminar el atributo puntos si ya no se va a usar
    }


    @Override
    public String toString() {
        return "Jugador{" +
                "Nombre='" + Nombre + '\'' +
                ", puntos=" + puntos +
                ", fichasEnMano=" + fichasEnMano +
                ", puedoempezar=" + puedoempezar +
                ", ganador=" + ganador +
                '}';
    }
}






