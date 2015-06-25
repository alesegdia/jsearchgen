package com.alesegdia.jsearchgen.fitness.solver;

import java.util.List;

import com.alesegdia.jsearchgen.fitness.cache.IFitnessSolver;
import com.alesegdia.jsearchgen.matrixsolver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public abstract class MultiObjectiveFitnessSolver implements IFitnessSolver {

	/**
	 * Longitud del camino principal
	 */
	private float main_path_length;
	
	/**
	 * Número de bifurcaciones (y subbifurcaciones) del camino principal
	 */
	private float alt_path_branching;
	
	/**
	 * Número de habitaciones gastadas en caminos alternativos
	 */
	private float alt_path_length;
	
	/**
	 * Condensación de las habitaciones
	 */
	private float room_condensation;

	private List<Integer> path;

	private void ComputeAllFitness(UpperMatrix2D<Float> graph_matrix) {
		UpperMatrix2D<Float> clone = new UpperMatrix2D<Float>(graph_matrix);
		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(new UpperMatrix2D<Float>(graph_matrix));
		//return fws.GetDistance();

		FloodFillGraphMatrixSolver ffs = new FloodFillGraphMatrixSolver();
		ffs.Solve(clone, fws.GetPath());

		this.alt_path_branching = ffs.GetBranchingFitness();
		this.alt_path_length = ffs.GetAltPathLengthFitness();
		this.main_path_length = fws.GetDistance();
	}
	
	@Override
	public float ComputeFitness(UpperMatrix2D<Float> graph_matrix) {
		ComputeAllFitness(graph_matrix);
		return ComputeFinalFitness( this.main_path_length, this.alt_path_branching, this.alt_path_length, this.room_condensation );
	}

	protected abstract float ComputeFinalFitness(float main_path_length_fitness, float alt_path_branching_fitness, float alt_path_length_fitness, float room_condensation_fitness );
	
}
