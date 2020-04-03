package prueba;

import static robocode.util.Utils.normalRelativeAngleDegrees;


import java.awt.Color;
import java.util.Arrays;

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
				

		System.out.println("Iniciando ejecuciÃ³n del robot");
		
		// Nuestro robot serÃ¡ rojo
		setAllColors(Color.red);

		//DATOS QUE DEBEN COINCIDIR CON LOS DEL PROGRAMA main DE LA CLASE RouteFinder
		//long semilla = 0;
		//int nFil = 16;
		//int nCol = 12;
		//int nObst = 40;
		//int tamCelda = 50;
		
		//Orientamos inicialmente el robot hacia arriba
		turnRight(normalRelativeAngleDegrees(0 - getHeading()));
		
		//A continuaciÃ³n nuestro robot girarÃ¡ un poco sobre sÃ­ mismo		
		/* int k = 0;
		while(k < 20){
			turnRight(90);
			k++;
		} */
		
		
		// AQUÃ� DEBE:
		//  1. GENERARSE EL PROBLEMA DE BÃšSQUEDA
		//  2. BUSCAR LA SOLUCIÃ“N CON UN ALGORITMO DE BÃšSQUEDA
		//  3. EJECUTAR LA SOLUCIÃ“N ENCONTRADA

		int numPixelFila= 800;
		int numPixelCol=600;
		int tamCelda = 50;       //celdas de 50 x 50
		int numObstaculos = 40;
		long semilla = 5;  
		int nFil = numPixelFila / tamCelda;
		int nCol = numPixelCol  / tamCelda;

		Problema problema = new Problema(semilla, numObstaculos, nFil, nCol);

		int inicio = problema.generarPosIni();
		problema.generarPosFinal();
		problema.generarObstaculos();

		//Estado estadoActual = new Estado(inicio/nCol, inicio%nCol); //estado inicial
		//EstadoGreedy estadoActual = new EstadoGreedy(inicio/nCol, inicio%nCol, 0); //estado inicial
		EstadoA estadoActual = new EstadoA (inicio/nCol, inicio%nCol, 0); //estado inicial

		Tupla[] solucion;
		//solucion = estadoActual.busquedaAnchura(inicio, nCol);			
		//solucion = estadoActual.busquedaGreedy(inicio, nCol);			
		solucion = estadoActual.busquedaA(inicio, nCol);			

		System.out.println(Arrays.toString(solucion));
		System.out.println(solucion.length);

		for (int i = 0; i < solucion.length - 1; i++) {
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
	}
}
