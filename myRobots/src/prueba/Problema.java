package prueba;
import java.util.Random;

public class Problema {

	private long seed;
	private int nObstaculos;
	private Random rnd;
	
	private int nFil;
	private int nCol;

	public static int fInicial;
	public static int cInicial;

	public static int fFinal;
	public static int cFinal;
	
	static public int [][] tableroInicial; //Usar� el 0 como libre, 1 ser� obstaculo, 2 sitio de inicio y 3 sitio donde acabar
	

	/**
	 * 
	 * @param seed es la semilla para el Random().
	 * @param nObstaculos es el número de obsrtaculos.
	 * @param f es el número de filas.
	 * @param c es el número de columnas.
	 */
	public Problema(long seed, int nObstaculos, int f, int c) {
		this.seed = seed;
		this.nObstaculos = nObstaculos;
		this.rnd = new Random();
		rnd.setSeed(this.seed);
		
		nFil = f;
		nCol = c;
		tableroInicial = new int[nFil][nCol]; 
	}
	
	/**
	 * 
	 * @return Devuelve la matriz del problema.
	 */
	public int[][] getTablero() {
		return tableroInicial;
	}

	/**
	 * Establece el tablero del parámetro como nuevo.
	 * @param nuevoTab es el nuevo tablero.
	 */
	public void setTablero(int [][] nuevoTab) {
		tableroInicial = nuevoTab;
	}

	/**
	 * Genera la pocisión inicial del bot.
	 * @return Una posición entre 0 y AxB - 1 (siendo A el número de filas y B el número de columnas).
	 */
	
	public int generarPosIni() {
		int inicio = rnd.nextInt(nFil*nCol);
		tableroInicial[inicio/nCol][inicio%nCol] = 2;

		fInicial = inicio/nCol;
		cInicial = inicio%nCol;
		
		return inicio;
	}

	/**
	 * Genera la pocisión final del bot.
	 */
	
	public void generarPosFinal() {
		boolean huecoEncontrado = false;
		while (!huecoEncontrado) {
			int fin = rnd.nextInt(nFil*nCol);

			fFinal = fin/nCol;
			cFinal = fin%nCol;
			
			if (tableroInicial[fFinal][cFinal] == 0) {
				huecoEncontrado = true;
				tableroInicial[fFinal][cFinal] = 3;
			}
		}
	}

	/**
	 * Genera los obstáculos del problema.
	 */
	
	public void generarObstaculos() {
		int i = 0;
		while (i < nObstaculos) {
			int posObstaculo = rnd.nextInt(nFil*nCol); 
			
			if (tableroInicial[posObstaculo/nCol][posObstaculo%nCol] == 0) {
				i++;
				tableroInicial[posObstaculo/nCol][posObstaculo%nCol] = 1;
			}
		}
	}
	

}
