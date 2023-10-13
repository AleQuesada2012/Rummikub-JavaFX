package poo.rummikub;

import java.util.Vector;

public class Mesa {
    private Ficha[][] matrizFichas;

    private Pila fichas;

    private int espaciolleno;

    public Mesa() {
        fichas = new Pila();
        matrizFichas = new Ficha[15][15];
    }

    public Mesa (int x){
        matrizFichas = new Ficha[15][15];
    }





    public void ingresarFicha(Ficha ficha, int x, int y) {
        matrizFichas[x][y] = ficha;
    }

    public boolean matrizValida(){
        if(estaVacia())return true;

        boolean esvalida = false;
        Vector<Ficha> fichas =  new Vector<>();
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                if(matrizFichas[i][j]!=null){
                    fichas.add(matrizFichas[i][j]);
                }
                if(matrizFichas[i][j] == null){
                    Jugada posibleJugada = new Jugada(fichas);
                    if(posibleJugada.serieValida() || posibleJugada.escaleraValida()){
                        esvalida = true;
                        fichas.clear();
                    }
                    else{
                        return false;
                    }

                }


            }

        }
        return esvalida;
    }

    public boolean estaVacia(){
        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){

                if(matrizFichas[i][j]!= null){
                    return false;
                }
            }
        }
        return true;
    }



    public void copiarMesa(Mesa Mesaoriginal) {
        for (int fila = 0; fila < matrizFichas.length; fila++) {
            for (int col = 0; col < matrizFichas[fila].length; col++) {
                matrizFichas[fila][col] = Mesaoriginal.matrizFichas[fila][col];
            }
        }
        espaciolleno = Mesaoriginal.espaciolleno;
    }

    public Ficha agarrarpila(){
       return  fichas.agarrarficha();
    }

    public Pila getFichas() {
        return fichas;
    }



    public void imprimirMesa() {
        for (int fila = 0; fila < matrizFichas.length; fila++) {
            for (int columna = 0; columna < matrizFichas[fila].length; columna++) {
                Ficha ficha = matrizFichas[fila][columna];
                if (ficha != null) {
                    System.out.print(ficha.getNum() + " " + ficha.getColor() + "\t");
                } else {
                    System.out.print("-\t");
                }
            }
            System.out.println();
        }
    }

    public Ficha[][] getMatrizFichas() {
        return matrizFichas;
    }
}
