package poo.rummikub;// package src;

import java.util.Vector;
import java.util.Random;

/**
 * Clase para representar la totalidad de fichas del tablero con sus respectivos atributos y métodos.
 */
public class Pila{
    private Vector<Ficha> Stack;
    private Random random;

    /**
     * Constructor de la clase pila el cual genera el vector
     * donde se almacenarán las fichas al igual que genera la pila.
     */
    public Pila() {
        Stack = new Vector<>();
        random = new Random();
        generarpila();
    }

    /**
     * Método para generar la totalidad de las fichas del tablero.
     */
    public void generarpila() {
        String[] colores = {"N", "A", "Y", "R"};
        for (String color : colores) {
            for (int number = 1; number <= 13; number++) {
                for (int pair = 0; pair < 2; pair++) {
                    Ficha ficha = new Ficha(number, color);
                    Stack.add(ficha);
                }
            }
        }
        Ficha comodin = new Ficha(0,"comodin");
        Ficha comodin2 = new Ficha(0,"comodin");
        Stack.add(comodin);
        Stack.add(comodin2);
    }

    /**
     * Método el cual substrae una ficha del montón.
     * @param pos la posición del vector que se desea obtener su ficha.
     * @return la ficha de la posición deseada.
     */
    public Ficha eliminar(int pos){
        return  Stack.remove(pos);
    }

    /**
     * Método para agarrar una ficha random de la pila.
     * @return la ficha que se agarró.
     */
    public Ficha agarrarficha() {
        if (Stack.isEmpty()) {
            return null;
        }

        int randomficha = random.nextInt(Stack.size());
        Ficha ficha = eliminar(randomficha);
        return ficha;
    }

    /**
     * Método para obtener el tamaño de la pila.
     * @return la cantidad de fichas en la pila.
     */
    public int getStackSize(){
       return this.Stack.size();
    }

    /**
     * Método el cual obtiene la pila.
     * @return retorna la pila.
     */
    public Vector<Ficha> getStack(){
        return this.Stack;
    }

}
