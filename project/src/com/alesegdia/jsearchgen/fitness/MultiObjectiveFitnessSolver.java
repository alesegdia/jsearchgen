package com.alesegdia.jsearchgen.fitness;

import java.util.List;

import com.alesegdia.jsearchgen.matrixsolver.FloodFillFitnessSolver;
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

	private List<Integer> path;

	private void ComputeAllFitness(UpperMatrix2D<Float> graph_matrix) {
		UpperMatrix2D<Float> clone = new UpperMatrix2D<Float>(graph_matrix);
		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(new UpperMatrix2D<Float>(graph_matrix));
		//return fws.GetDistance();

		FloodFillFitnessSolver ffs = new FloodFillFitnessSolver();
		ffs.Solve(clone, fws.GetPath());

		this.alt_path_branching = ffs.GetBranchingFitness();
		this.alt_path_length = ffs.GetBranchingFitness();
		this.main_path_length = fws.GetDistance();
	}
	
	@Override
	public float ComputeFitness(UpperMatrix2D<Float> graph_matrix) {
		float fitness = this.alt_path_branching + 0.5f * this.alt_path_length;
		return fitness;
	}

}
