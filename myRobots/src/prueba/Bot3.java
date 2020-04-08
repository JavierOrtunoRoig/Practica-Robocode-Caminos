package prueba;

import static robocode.util.Utils.normalRelativeAngleDegrees;


import java.awt.Color;

import robocode.Robot;

/**
 * 
 */

/**
 * @date 2018-03-22
 * 
 * Plantilla para la definiciÃ³n de un robot en Robocode
 *
 */
public class Bot3 extends Robot {


	//The main method in every robot
	public void run() {
				

		System.out.println("Iniciando ejecución del robot");
		
		// Nuestro robot serÃ¡ rojo
		setAllColors(Color.red);


		//Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));
		


		//  1. GENERACIÓN DEL PROBLEMA DE BÚSQUEDA

		int numPixelFila= 800;
		int numPixelCol=600;
		int tamCelda = 50;       //celdas de 50 x 50
		int numObstaculos = 40;
		long semilla = 1;  
		int nFil = numPixelFila / tamCelda; 
		int nCol = numPixelCol  / tamCelda;

		Problema problema = new Problema(semilla, numObstaculos, nFil, nCol);

		int inicio = problema.generarPosIni();
		problema.generarPosFinal();
		problema.generarObstaculos();



		//  2. BUSQUEDA DE LA SOLUCIÓN CON UN ALGORITMO DE BÚSQUEDA


		/*Tres posibles resoluciones del problema, podemos hacerlo con las 3 lineas de abajo, o con la interfaz creada

		Estado estadoActual = new Estado(inicio/nCol, inicio%nCol); //estado inicial
		EstadoGreedy estadoActual = new EstadoGreedy(inicio/nCol, inicio%nCol); //estado inicial
		EstadoA estadoActual = new EstadoA (inicio/nCol, inicio%nCol, 0); //estado inicial */

		InterfazEstado estadoActual = new Estado(inicio/nCol, inicio%nCol);


		Tupla[] solucion;
		solucion = estadoActual.busqueda(inicio, nCol);					

		

		//  3. EJECUCIÓN DE LA SOLUCIÓN ENCONTRADA


		for (int i = 0; i < solucion.length - 1; i++) {

			double gradosNuevos = calculaGrados(solucion, i);

			moverBot(gradosNuevos);
			
		}
	}



	/**
	 * Mueve al robot por el mapa.
	 * @param gradosNuevos son los grados nuevos que hay que girar el robot.
	 */

	private void moverBot(double gradosNuevos) {
		double distanciaARecorrer;
		if (gradosNuevos == 0 || gradosNuevos == 90 || gradosNuevos == 180 || gradosNuevos == 270) {
			distanciaARecorrer = 50;
		} else {
			distanciaARecorrer = Math.sqrt(5000);
		}

		gradosNuevos = gradosNuevos - 90;

		this.turnRight(gradosNuevos + normalRelativeAngleDegrees(0 - getHeading()));
		this.ahead(distanciaARecorrer);
	}



	/**
	 * 
	 * @param solucion es la solución generada.
	 * @param i es la posición del array que estamos mirando.
	 * @return Los grados que hay que girar el robot.
	 */

	private double calculaGrados(Tupla[] solucion, int i) {
		double gradosNuevos;

		if (solucion[i + 1].getPrimero() < solucion[i].getPrimero() && solucion[i + 1].getSegundo() < solucion[i].getSegundo()) { //esquina sup izq
			gradosNuevos = 315;
		} else if (solucion[i + 1].getPrimero() < solucion[i].getPrimero() && solucion[i + 1].getSegundo() > solucion[i].getSegundo()) { //esquina sup der
			gradosNuevos = 45;
		} else if (solucion[i + 1].getPrimero() > solucion[i].getPrimero() && solucion[i + 1].getSegundo() < solucion[i].getSegundo()) { //esquina inf izq
			gradosNuevos = 225;
		} else if (solucion[i + 1].getPrimero() < solucion[i].getPrimero() && solucion[i + 1].getSegundo() == solucion[i].getSegundo()) { // arriba
			gradosNuevos = 0;
		} else if (solucion[i + 1].getPrimero() == solucion[i].getPrimero() && solucion[i + 1].getSegundo() < solucion[i].getSegundo()) { //izq
			gradosNuevos = 270;
		} else if (solucion[i + 1].getPrimero() == solucion[i].getPrimero() && solucion[i + 1].getSegundo() > solucion[i].getSegundo()) { //der
			gradosNuevos = 90;
		} else if (solucion[i + 1].getPrimero() > solucion[i].getPrimero() && solucion[i + 1].getSegundo() == solucion[i].getSegundo()) { //abajo
			gradosNuevos = 180;
		} else { //esquina inf der
			gradosNuevos = 135;
		}

		return gradosNuevos;
	}
}
