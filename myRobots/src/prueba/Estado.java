package prueba;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;



public class Estado implements InterfazEstado {
    private int [][] tablero = Problema.tableroInicial;
    private static HashMap <Tupla, Tupla> mapa = new HashMap<>(); 

    private int filaFinal = Problema.fFinal;
    private int columnaFinal = Problema.cFinal;

    private int filaActual;
    private int columnaActual;

    private Tupla[] camino = null;


    /**
     * @param f es la fila del estado
     * @param c es la cloumna del nuevo estado
     */

    public Estado(int f, int c) {
        filaActual = f;
        columnaActual = c;
    }

    /**
     * @return Array de Tuplas con el camino desde inicio hasta fin
     */

    public Tupla[] getCamino() {return camino;}


    /**
     * @return Mapa con todos los estados y sus padres.
     */

    public HashMap <Tupla, Tupla> getMapa() {return mapa;}

    /**
     * @return Devuelve la fila del estado.
     */

    public int getFilaActualEstado() {return filaActual;}

    /**
     * @return Devuelve la columna del estado.
     */

    public int getColumnaActualEstado() {return columnaActual;}


    /**
     * @param inicio es un valor entre 0 y AxB - 1 (siendo A y B el número de filas y columnas respectivamente).
     * @param nCol  es el número de columnas que hay en la matriz
     * @return Lista de Tuplas con el camino desde el inicio hasta la meta.
     */

    public Tupla[] busqueda(int inicio, int nCol) {

        Estado estadoActual = new Estado(inicio/nCol, inicio%nCol); //estado inicial
		LinkedList<Estado> cola = estadoActual.sucesores(); //sucesores del incial
		
		while (!estadoActual.finalp() && !cola.isEmpty()) { //mientras no llegue al final y la cola tenga número, sigo
			estadoActual = cola.get(0);
			cola.remove();
			LinkedList<Estado> nuevaCola = estadoActual.sucesores();
			cola.addAll(nuevaCola);
        }
        
        this.camino = estadoActual.getCamino();
		return estadoActual.getCamino();

    }

    /**
     * @return Devuelve true si estamos en el estado final.
     */

    private boolean finalp() {
        boolean res = filaActual == filaFinal && columnaActual == columnaFinal;

        if (res) {
            camino = camino();
        }
        return res;
    }

    /**
     * Añade un nuevo nodo en el parámetro nodosAbiertos.
     * 
     * @param nuevoF es la fila del nuevo estado.
     * @param nuevoC es la columna del nuevo estado.
     * @param nodosAbiertos es la lista de nodos que quedan por explorar.
     */

    private void meterNuevoNodo(int nuevoF, int nuevoC, LinkedList <Estado> nodosAbiertos) {
        Tupla nuevoMovimiento = new Tupla(nuevoF, nuevoC);
        Tupla posicionAntigua = new Tupla(filaActual, columnaActual);

         if (!mapa.containsKey(nuevoMovimiento)) {
            mapa.put(nuevoMovimiento, posicionAntigua);
            nodosAbiertos.addLast(new Estado(nuevoF, nuevoC));
        }
    }

    /**
     * 
     * @return Devuelve la lista con los nuevos sucesores.
     */

    private LinkedList<Estado> sucesores() {
        LinkedList <Estado> nodosAbiertos = new LinkedList<>();

        int numeroFilas = tablero.length;
        int numeroColumnas = tablero[0].length;


        for (int i = filaActual - 1; i <= filaActual + 1; i++) {
            for (int j = columnaActual - 1; j <= columnaActual + 1; j++) {

                if ((filaActual - i != 0 || columnaActual - j != 0) && i >= 0 && i <= numeroFilas - 1 && j >= 0 && j <= numeroColumnas - 1) { //Está dentro de los limites de la matriz
                    if ((tablero[i][j] == 0 || tablero[i][j] == 3) && (Math.abs(i-filaActual) + Math.abs(j-columnaActual) == 1)) {//arriba, abajo, izq, der libre
                        meterNuevoNodo(i, j, nodosAbiertos);

                    }   else if ((tablero[i][j] == 0 || tablero[i][j] == 3) && (Math.abs(i-filaActual) + Math.abs(j-columnaActual) != 1)) { //esquinas libres
                        if (i - filaActual > 0 && j - columnaActual > 0) { //esquina sup izq
                            if (i - 1 >= 0 && i - 1<= numeroFilas - 1 && j - 1 >= 0 && j - 1 <= numeroColumnas - 1 && tablero[i-1][j] != 1 && tablero[i][j-1] != 1) {
                                    meterNuevoNodo(i, j, nodosAbiertos);
                                                        } 
                        } else if (i - filaActual > 0 && j - columnaActual < 0) { //esquina sup der
                            if (i - 1 >= 0 && i - 1 <= numeroFilas - 1 && j + 1 >= 0 && j + 1 <= numeroColumnas - 1 && tablero[i-1][j] != 1 && tablero[i][j+1] != 1) {
                                    meterNuevoNodo(i, j, nodosAbiertos);
                            } 
                        } else if (i - filaActual < 0 && j - columnaActual > 0) { //esquina inf izq
                            if (i + 1 >= 0 && i + 1<= numeroFilas - 1 && j - 1 >= 0 && j - 1 <= numeroColumnas - 1 && tablero[i+1][j] != 1 && tablero[i][j-1] != 1) {
                                    meterNuevoNodo(i, j, nodosAbiertos);
                            } 
                        } else { //esquina inf der
                            if (i + 1 >= 0 && i + 1<= numeroFilas - 1 && j + 1 >= 0 && j + 1 <= numeroColumnas - 1 && tablero[i+1][j] != 1 && tablero[i][j+1] != 1) {
                                    meterNuevoNodo(i, j, nodosAbiertos);
                            } 
                        }
                    }  
                }
            }
        }

        return nodosAbiertos;
    }

    /**
     * 
     * @return El camino hasta la meta.
     */

    private Tupla[] camino() {
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
}

