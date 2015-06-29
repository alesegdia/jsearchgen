package com.alesegdia.jsearchgen.fitness.solver;

public class MultiObjectiveFitness {

	/**
	 * Longitud del camino principal
	 */
	public static final int FO_MAIN_PATH_LENGTH = 0;

	/**
	 * Número de bifurcaciones (y subbifurcaciones) del camino principal
	 */
	public static final int FO_ALT_PATH_BRANCHING = 1;
	
	/**
	 * Número de habitaciones gastadas en caminos alternativos
	 */
	public static final int FO_ALT_PATH_LENGTH = 2;
	
	/**
	 * Condensación de las habitaciones
	 */
	public static final int FO_ROOM_CONDENSATION = 3;
	
	
	public float objectives[] = new float[4];

	public float total_fitness;
	
}
