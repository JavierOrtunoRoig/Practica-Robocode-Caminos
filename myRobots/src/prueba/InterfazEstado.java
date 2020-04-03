package prueba;
import java.util.HashMap;
import java.util.LinkedList;

public interface InterfazEstado {

    public Tupla[] getCamino();
    
    public HashMap <Tupla, Tupla> getMapa();
    
    public int getFilaActualEstado();
    
    public int getColumnaActualEstado();
    
    public Tupla[] busqueda(int inicio, int nCol);
    
    public boolean finalp();
    
    public LinkedList<Estado> sucesores();
    
    public Tupla[] camino();
}