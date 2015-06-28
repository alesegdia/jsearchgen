package com.alesegdia.jsearchgen.fitness.solver;

import com.alesegdia.jsearchgen.matrixsolver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public class MainPathLengthFitnessSolver implements IFitnessSolver {

	@Override
	public MultiObjectiveFitness ComputeFitness(UpperMatrix2D<Float> graph_matrix) {
		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(new UpperMatrix2D<Float>(graph_matrix));
		MultiObjectiveFitness mof = new MultiObjectiveFitness();
		mof.main_path_length = fws.GetDistance();
		mof.total_fitness = mof.main_path_length;
		return mof;
	}

	@Override
	public void NotifySelected(MultiObjectiveFitness fitness) {
		// TODO Auto-generated method stub
		
	}

}
