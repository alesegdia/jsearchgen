package com.alesegdia.jsearchgen.fitness;

public class MultiObjectiveFitness {
	
	/**
	 * Longitud del camino principal
	 */
	public float main_path_length;
	
	/**
	 * Número de bifurcaciones (y subbifurcaciones) del camino principal
	 */
	public float alt_path_branching;
	
	/**
	 * Número de habitaciones gastadas en caminos alternativos
	 */
	public float alt_path_length;
	
	/**
	 * Condensación de las habitaciones
	 */
	public float room_condensation;
	
	/**
	 * Fitness final computado a partir de los otros
	 */
	public float overall_fitness;
}
