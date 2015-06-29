package com.alesegdia.jsearchgen.fitness.solver;

import com.alesegdia.jsearchgen.matrixsolver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public class MainPathLengthFitnessSolver implements IFitnessSolver {

	@Override
	public MultiObjectiveFitness ComputeFitness(UpperMatrix2D<Float> graph_matrix) {
		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(new UpperMatrix2D<Float>(graph_matrix));
		MultiObjectiveFitness mof = new MultiObjectiveFitness();
		mof.objectives[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = fws.GetDistance();
		mof.total_fitness = mof.objectives[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH];
		return mof;
	}

	@Override
	public void NotifySelected(MultiObjectiveFitness fitness) {
		// TODO Auto-generated method stub
		
	}

}
