package prueba;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;


public class EstadoA implements Comparator <EstadoA>, Comparable <EstadoA>, InterfazEstado{
    private int [][] tablero = Problema.tableroInicial;
    private static HashMap <Tupla, Tupla> mapa = new HashMap<>(); 

    private int filaFinal = Problema.fFinal;
    private int columnaFinal = Problema.cFinal;

    private int filaActual;
    private int columnaActual;

    private double g;
    private double coste;

    private Tupla[] camino = null;



    public EstadoA(int f, int c, double g) {
        filaActual = f;
        columnaActual = c;
        this.g = g;

        this.coste = g + Math.abs(f - filaFinal) + Math.abs(c - columnaFinal);
    }



    public Tupla[] getCamino() {return camino;}

    public HashMap <Tupla, Tupla> getMapa() {return mapa;}

    public int getFilaActualEstado() {return filaActual;}

    public int getColumnaActualEstado() {return columnaActual;}

    //private double getG() {return g;}


    public Tupla[] busqueda(int inicio, int nCol) {
        EstadoA estadoActual = new EstadoA(inicio/nCol, inicio%nCol, 0); //estado inicial

        PriorityQueue <EstadoA> cola = estadoActual.sucesores();

        while (!estadoActual.finalp() && !cola.isEmpty()) { //mientras no llegue al final y la cola tenga número, sigo
			estadoActual = cola.remove();
			PriorityQueue<EstadoA> nuevaCola = estadoActual.sucesores();
			cola.addAll(nuevaCola);
		}
		
        return estadoActual.getCamino();
    }

    public boolean finalp() {
        boolean res = filaActual == filaFinal && columnaActual == columnaFinal;

        if (res) {
            camino = camino();
        }
        return res;
    }




    private void meterNuevoNodo(int nuevoF, int nuevoC, PriorityQueue <EstadoA> nodosAbiertos, double g) {
        Tupla nuevoMovimiento = new Tupla(nuevoF, nuevoC);
        Tupla posicionAntigua = new Tupla(filaActual, columnaActual);

         if (!mapa.containsKey(nuevoMovimiento)) {
            mapa.put(nuevoMovimiento, posicionAntigua);
            EstadoA nuevoEstado = new EstadoA(nuevoF, nuevoC, g + 1); 
            nodosAbiertos.add(nuevoEstado);
        }
    }

    private PriorityQueue<EstadoA> sucesores() {
        PriorityQueue <EstadoA> nodosAbiertos = new PriorityQueue<>();

        int numeroFilas = tablero.length;
        int numeroColumnas = tablero[0].length;


        for (int i = filaActual - 1; i <= filaActual + 1; i++) {
            for (int j = columnaActual - 1; j <= columnaActual + 1; j++) {

                if ((filaActual - i != 0 || columnaActual - j != 0) && i >= 0 && i <= numeroFilas - 1 && j >= 0 && j <= numeroColumnas - 1) { //Está dentro de los limites de la matriz
                    if ((tablero[i][j] == 0 || tablero[i][j] == 3) && (Math.abs(i-filaActual) + Math.abs(j-columnaActual) == 1)) {//arriba, abajo, izq, der libre
                        meterNuevoNodo(i, j, nodosAbiertos, this.g);

                    }   else if ((tablero[i][j] == 0 || tablero[i][j] == 3) && (Math.abs(i-filaActual) + Math.abs(j-columnaActual) != 1)) { //esquinas libres
                        if (i - filaActual > 0 && j - columnaActual > 0) { //esquina sup izq
                            if (i - 1 >= 0 && i - 1<= numeroFilas - 1 && j - 1 >= 0 && j - 1 <= numeroColumnas - 1 && tablero[i-1][j] != 1 && tablero[i][j-1] != 1) {
                                meterNuevoNodo(i, j, nodosAbiertos, this.g);
                                                        } 
                        } else if (i - filaActual > 0 && j - columnaActual < 0) { //esquina sup der
                            if (i - 1 >= 0 && i - 1 <= numeroFilas - 1 && j + 1 >= 0 && j + 1 <= numeroColumnas - 1 && tablero[i-1][j] != 1 && tablero[i][j+1] != 1) {
                                meterNuevoNodo(i, j, nodosAbiertos, this.g);
                            } 
                        } else if (i - filaActual < 0 && j - columnaActual > 0) { //esquina inf izq
                            if (i + 1 >= 0 && i + 1<= numeroFilas - 1 && j - 1 >= 0 && j - 1 <= numeroColumnas - 1 && tablero[i+1][j] != 1 && tablero[i][j-1] != 1) {
                                meterNuevoNodo(i, j, nodosAbiertos, this.g);
                            } 
                        } else { //esquina inf der
                            if (i + 1 >= 0 && i + 1<= numeroFilas - 1 && j + 1 >= 0 && j + 1 <= numeroColumnas - 1 && tablero[i+1][j] != 1 && tablero[i][j+1] != 1) {
                                meterNuevoNodo(i, j, nodosAbiertos, this.g);
                            } 
                        }
                    }  
                }
            }
        }

        return nodosAbiertos;
    }

    public Tupla[] camino() {
        Tupla[] path = new Tupla[1];

        Tupla fin, ini;
        ini = new Tupla(Problema.fInicial, Problema.cInicial);
        fin = new Tupla(filaFinal, columnaFinal);

        path[0] = fin;
        int i = 1;


        while (!path[i-1].equals(ini)) {

            path = Arrays.copyOf(path, path.length+1);

            Tupla ant = mapa.get(path[i-1]);
            i++;
            path[i-1] = ant;
        } 

        for(int j = 0; j < path.length / 2; j++) // Le doy la vuelta al camino para que sea de Inicial -> Final
        {
            Tupla temp = path[j];
            path[j] = path[path.length - j - 1];
            path[path.length - j - 1] = temp;
        }

        return path;
    }

    @Override
    public int compareTo(EstadoA o) {
        if (this.coste < o.coste){
            return -1;
        } else if (this.coste > o.coste) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int compare(EstadoA o1, EstadoA o2) {
        if (o1.coste < o2.coste){
            return -1;
        } else if (o1.coste > o2.coste) {
            return 1;
        } else {
            return 0;
        }
    }
}

