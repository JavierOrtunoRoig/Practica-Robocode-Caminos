package prueba;
import java.util.HashMap;

public interface InterfazEstado {

    /**
     * @return Array de Tuplas con el camino desde inicio hasta fin.
     */

    public Tupla[] getCamino();
    
    /**
     * @return Mapa con todos los estados y sus padres.
     */

    public HashMap <Tupla, Tupla> getMapa();

    /**
     * @return Devuelve la fila del estado.
     */
    
    public int getFilaActualEstado();

    /**
     * @return Devuelve la columna del estado.
     */
    
    public int getColumnaActualEstado();

    /**
     * 
     * @param inicio es un valor entre 0 y AxB - 1 (siendo A y B el número de filas y columnas respectivamente).
     * @param nCol  es el número de columnas que hay en la matriz
     * @return Lista de Tuplas con el camino desde el inicio hasta la meta.
     */
    
    public Tupla[] busqueda(int inicio, int nCol);
    
}