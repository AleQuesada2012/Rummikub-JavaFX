package poo.rummikub;// package src;

import java.util.Vector;

    public class Jugada {
        private Vector<Ficha> jugada;


        public Jugada() {
            jugada = new Vector<>();
        }

        public  Jugada(Vector<Ficha>lista){
            jugada = lista;
        }

        /**
         * Metodo para verificar que la secuencia de fichas del tipo escalera sea valido
         * @return
         */
        public boolean escaleraValida() {
            int escalera = jugada.size();
            if (escalera < 3 || escalera > 13) {
                return false;
            }
            else {
                Vector<String>color = new Vector<>();
                color.add(this.getFichaPos(0).getColor().equals("comodin") ? this.getFichaPos(1).getColor() : this.getFichaPos(0).getColor());
                color.add("comodin");

                int cont = (this.getFichaPos(0).getColor().equals("comodin")) ? this.getFichaPos(1).getNum() : this.getFichaPos(0).getNum();

                for (int i = 0; i < escalera-1; i++) {
                    Ficha currentTile = this.jugada.get(i);
                    Ficha siguiente = this.jugada.get(i+1);

                    if(currentTile.getNum()==13 && siguiente.getColor().equals("comodin")){
                        return false;
                    }


                    if("comodin".equals(currentTile.getColor())){
                        if(i == 0){
                            if(siguiente.getNum() != 1){ //Si no es el primero y el siguiente no es uno, va bien
                                continue;
                            }
                            else {
                                return false;
                            }
                        }
                    }

                    if("comodin".equals(siguiente.getColor())) {
                        cont++;
                        continue;
                    }

                    if (cont +1 != siguiente.getNum() || (!color.contains(currentTile.getColor()) && !color.contains(siguiente.getColor()))) {
                        return false;
                    }
                    cont++;
                }
            }
            return true;
        }

        /**
         * Metodo el cual verifica si la secuencia de fichas del tipo serie sea valido
         * @return Un booleano que verifica si la jugada fue correcta
         */
        public boolean serieValida() {
            int serie = this.jugada.size();
            if (serie < 3 || serie > 4) {
                return false;
            } else {
                Vector<String> color = new Vector<>();
                int num = (this.getFichaPos(0).getColor().equals("comodin")) ? this.getFichaPos(1).getNum() : this.getFichaPos(0).getNum();
                for (int i = 0; i < serie; i++) {
                    Ficha currentTile = this.jugada.get(i);

                    if ("comodin".equals(currentTile.getColor()) ) {
                        continue;
                    }

                    if (num != currentTile.getNum() || (color.contains(currentTile.getColor()))) {
                        return false;

                    } else {
                        color.add(currentTile.getColor());
                    }

                }
                return true;
            }
        }

        /**
         * Metodo para calcular el valor de una jugada según los puntos de las fichas
         * @return un valor entero que representa los puntos de la jugada calculada
         */
        public int valorJugada(){
            int cont = 0;
            if(this.escaleraValida() || this.serieValida()){
                for(int i=0;i<this.jugada.size();i++){
                    Ficha ficha = this.jugada.get(i);
                    cont+=ficha.getNum();

                }
            }
            return cont;
        }

        public Ficha retirarFicha(int x){
            Ficha ficha = this.jugada.remove(x);
            return ficha;
        }

        public Vector<Ficha> getJugada() {
            return jugada;
        }

        public Ficha getFichaPos(int x){
                return this.jugada.get(x);

        }

        public int getSizeJugada(){
            return this.jugada.size();
        }


    }
