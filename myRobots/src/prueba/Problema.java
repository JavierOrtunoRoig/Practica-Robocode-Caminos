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
	
	public Problema(long seed, int nObstaculos, int f, int c) {
		this.seed = seed;
		this.nObstaculos = nObstaculos;
		this.rnd = new Random();
		rnd.setSeed(this.seed);
		
		nFil = f;
		nCol = c;
		tableroInicial = new int[nFil][nCol]; 
	}
	
	public int[][] getTablero() {
		return tableroInicial;
	}
	public void setTablero(int [][] nuevoTab) {
		tableroInicial = nuevoTab;
	}
	
	public int  generarPosIni() {
		int inicio = rnd.nextInt(nFil*nCol);
		tableroInicial[inicio/nCol][inicio%nCol] = 2;

		fInicial = inicio/nCol;
		cInicial = inicio%nCol;
		
		return inicio;
	}
	
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
