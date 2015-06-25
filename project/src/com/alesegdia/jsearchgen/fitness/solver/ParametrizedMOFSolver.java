package com.alesegdia.jsearchgen.fitness.solver;

public class ParametrizedMOFSolver extends MultiObjectiveFitnessSolver {

	private float p1, p2, p3, p4;

	public ParametrizedMOFSolver( float p1, float p2, float p3, float p4 ) {
		this.p1 = p1; this.p2 = p2; this.p3 = p3; this.p4 = p4;
	}
	
	@Override
	protected float ComputeFinalFitness(float main_path_length_fitness, float alt_path_branching_fitness, float alt_path_length_fitness, float room_condensation_fitness ) {
		return p1 * main_path_length_fitness + p2 * alt_path_branching_fitness + p3 * alt_path_length_fitness + p4 * room_condensation_fitness;
	}

}
