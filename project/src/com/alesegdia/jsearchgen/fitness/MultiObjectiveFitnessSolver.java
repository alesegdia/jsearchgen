package com.alesegdia.jsearchgen.fitness;

import com.alesegdia.jsearchgen.matrixsolver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public class MultiObjectiveFitnessSolver implements IFitnessSolver {

	/**
	 * Longitud del camino principal
	 */
	float main_path_length;
	
	/**
	 * Número de bifurcaciones (y subbifurcaciones) del camino principal
	 */
	float alt_path_branching;
	
	/**
	 * Número de habitaciones gastadas en caminos alternativos
	 */
	float alt_path_length;
	
	/**
	 * Condensación de las habitaciones
	 */
	float room_condensation;

	
	@Override
	public float ComputeFitness(UpperMatrix2D<Float> graph_matrix) {
		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(new UpperMatrix2D<Float>(graph_matrix));
		main_path_length = fws.GetDistance();
		return 0.f;
	}

}
