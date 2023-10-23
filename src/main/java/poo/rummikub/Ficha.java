package poo.rummikub;// package src;

/**
 * La clase ficha que crea la lógica destinada a las características de la ficha.
 */
public class Ficha {
    private int num;

    private String color;

    private boolean esta;

    /**
     * Método de acceso para el atributo "está".
     * @return un boolean de si la ficha se encuentra en la mesa o no.
     */
    public boolean isEsta() {
        return esta;
    }

    /**
     *Metodo de cambio para el atributo "está".
     * @param esta un boolean que dice si la ficha se encuentra en la mesa o no.
     */
    public void setEsta(boolean esta) {
        this.esta = esta;
    }

    /**
     * Contructor de la clase "Ficha" el cual crea una ficha con los parámetros dados.
     * @param num un int que dice el numero que va a tener la ficha.
     * @param color un String que dice el color que va a tener la ficha.
     */
    public Ficha(int num, String color){
        setColor(color);
        setNum(num);
    }

    /**
     *Método de acceso para el atributo "color".
     * @return un String el cual es el color de la ficha seleccionada.
     */
    public String getColor() {
        return color;
    }

    /**
     * Método de cambio para el atributo "color".
     * @param color un String el cual va a ser el nuevo color que tendra la ficha.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Método de acceso para el atributo "num".
     * @return un int con el número de la ficha seleccionada.
     */
    public int getNum() {
        return num;
    }

    /**
     * Método de cambio para el atributo "num".
     * @param num un int que será el nuevo número que tendra la ficha seleciconada.
     */
    public void setNum(int num) {
        this.num = num;
    }

}
