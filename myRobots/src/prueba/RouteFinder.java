package prueba;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSetup;
import robocode.control.RobotSpecification;



/**
 * 
 * @date   2018-03-22
 * 
 * Plantilla para la prÃ¡ctica de algoritmos de bÃºsqueda con Robocode (G. Ing. Comp.)
 * 
 * 
 */

public class RouteFinder {
	public static void main(String[] args) {
		
		
		
		//Creamos un mapa con los datos especificados
		// Create the battlefield
		
		int numPixelFila= 800;
		int numPixelCol=600;
		int tamCelda = 50;       //celdas de 50 x 50
		
		//nÃºmero de obstÃ¡culos
		////DEBERÃ� COINCIDIR CON EL VALOR PROPORCIONADO AL ROBOT
		int numObstaculos = 40;
		
		//semilla para el generador de nÃºmeros aleatorios
		//DEBERÃ� COINCIDIR CON EL VALOR PROPORCIONADO AL ROBOT
		long semilla = 5;  
		
		//tamaÃ±o del mapa de obstÃ¡culos
		//DEBERÃ� COINCIDIR CON EL VALOR PROPORCIONADO AL ROBOT
		int nFil = numPixelFila / tamCelda;
		int nCol = numPixelCol  / tamCelda;
		


		//AQUÍ SE DEBERÁ DE GENERAR EL MAPA DE OBSTÁCULOS Y LAS POSICIONES INICIAL Y FINAL DEL ROBOT
		
		Problema problema = new Problema(semilla, numObstaculos, nFil, nCol);
		
		int inicio = problema.generarPosIni();
		problema.generarPosFinal();
		problema.generarObstaculos();
		
		int [][] tablero = problema.getTablero();



		//Usaré el 0 como libre, 1 será obstaculo, 2 sitio de inicio y 3 sitio donde acabar	
		
				
		// Crear el RobocodeEngine desde una instalaciÃ³n en C:/Robocode
		RobocodeEngine engine =
				new RobocodeEngine(new java.io.File("C:/Robocode"));
		// Mostrar el simulador de Robocode
		engine.setVisible(true);
		
		
		
		BattlefieldSpecification battlefield =
				new BattlefieldSpecification(numPixelFila, numPixelCol);
		// Establecer parÃ¡metros de la batalla
		int numberOfRounds = 5;
		long inactivityTime = 10000000;
		double gunCoolingRate = 1.0;
		int sentryBorderSize = 50;
		boolean hideEnemyNames = false;
		

		
		// En modelRobots recogemos la especificaciÃ³n de los robots que utilizaremos en la simulaciÃ³n.
		// En este caso serÃ¡n un robot sittingDuck y nuestro propio robot. La referencia a nuestro robot
		// debe ser relativa al proyecto que pusimos en Options>Preferences>DevelopmentOptions en Robocode,
		// indicando el nÃ³mbre del paquete (si lo hay) y del robot.  En nuestro caso suponemos como nombre 
		// prueba.Bot3*
		// Al nombre de los robots definidos por el usuario siempre hay que aÃ±adirle el carÃ¡cter * al final. 
		RobotSpecification[] modelRobots =
				engine.getLocalRepository
				("sample.SittingDuck,prueba.Bot3*");
		
		// Incluiremos un robot sittingDuck por obstÃ¡culo, mÃ¡s nuestro propio robot.
		RobotSpecification[] existingRobots =
				new RobotSpecification[numObstaculos+1];
		RobotSetup[] robotSetups = new RobotSetup[numObstaculos+1];
		
		/*
	     * Creamos primero nuestro propio robot y lo colocamos en la posiciÃ³n inicial del problema,
	     * que deberÃ¡ estar libre de obstÃ¡culo.
		 */
		
		int indice = 0;
		
		existingRobots[indice]=modelRobots[1];

		
		robotSetups[indice]=new RobotSetup(   (inicio/nCol * 50) + 25.0 ,        //AQUÃ� DEBE FIGURAR LA FILA EN PIXELS CORRESPONDIENTE A LA POSICIÃ“N INICIAL DEL ROBOT
				                                     (inicio%nCol * 50) + 25.0 ,        //AQUÃ� DEBE FIGURAR LA COLUMNA EN PIXELS CORRESPONDIENTE A LA POSICIÃ“N INICIAL DEL ROBOT    
										   0.0);              //orientaciÃ³n inicial

		
		/*
	     * Creamos un robot sittingDuck por cada obstÃ¡culo, y lo colocamos en el centro de la 
	     * celda correspondiente.
		 */
		
		
		
		//AQUÃ� SE DEBERÃ�A CREAR UN ROBOT sittingDuck EN LA POSICIÃ“N DE CADA OBSTÃ�CULO DE LA MALLA.

		indice++;
		for (int f = 0; f < nFil; f++){
			for (int c = 0; c < nCol; c++){
				System.out.print(tablero[f][c] + " ");
				if (tablero[f][c] == 1) {//SI HAY OBSTÃ�CULO EN ESA POSICIÃ“N DE LA MALLA
					
					existingRobots[indice]=modelRobots[0];   //sittingDuck
					
					
					robotSetups[indice]=  new RobotSetup( f*50 + 25.0 ,   //AQUÃ� DEBE FIGURAR LA FILA EN PIXELS CORRESPONDIENTE AL CENTRO DE LA CELDA DONDE HAY OBSTÃ�CULO
							                              c*50 + 25.0 ,   //AQUÃ� DEBE FIGURAR LA FILA EN PIXELS CORRESPONDIENTE AL CENTRO DE LA COLUMNA DONDE HAY OBSTÃ�CULO
							                             0.0);    //orientaciÃ³n
					indice++;
					
				}//if mapa
			}//for c
			System.out.println();
		}//for f
			
		System.out.println("Generados " + (indice -1) + " sitting ducks.");


		//Estado estadoActual = new Estado(inicio/nCol, inicio%nCol); //estado inicial
		//EstadoGreedy estadoActual = new EstadoGreedy(inicio/nCol, inicio%nCol, 0); //estado inicial
		//EstadoA estadoActual = new EstadoA(inicio/nCol, inicio%nCol, 0); //estado inicial


		//Tupla[] solucion;
		//solucion = estadoActual.busquedaAnchura(inicio, nCol);
		//solucion = estadoActual.busquedaGreedy(inicio, nCol);
		//solucion = estadoActual.busquedaA(inicio, nCol);
		//System.out.println(Arrays.toString(solucion));
		


	/*	LinkedList<Estado> cola = estadoActual.sucesores(); //sucesores del incial
		
		while (!estadoActual.finalp() && !cola.isEmpty()) { //mientras no llegue al final y la cola tenga número, sigo
			estadoActual = cola.get(0);
			cola.remove();
			LinkedList<Estado> nuevaCola = estadoActual.sucesores();
			cola.addAll(nuevaCola);
		}
		
		if (estadoActual.getCamino() == null) {
			System.out.println("No hay camino posible.");
		} else {
			System.out.println(Arrays.toString(estadoActual.getCamino()));
		} */

		


				
		
		/* 
		 * Crear y desarrollar la batalla con los robots antes definidos
		 */
		BattleSpecification battleSpec =
				new BattleSpecification(battlefield,
						numberOfRounds,
						inactivityTime,
						gunCoolingRate,
						sentryBorderSize,
						hideEnemyNames,
						existingRobots,
						robotSetups);
		
		
		// Ejecutar la simulaciÃ³n el tiempo especificado
		engine.runBattle(battleSpec, true); 
		// Cerrar la simulaciÃ³n
		engine.close();
		// Asegurarse de que la MV de Java se cierra adecuadamente.
		System.exit(0);
		

	}
	
}
