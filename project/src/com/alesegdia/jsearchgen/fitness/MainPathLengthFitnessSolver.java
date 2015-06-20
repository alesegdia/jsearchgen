package com.alesegdia.jsearchgen.fitness;

import com.alesegdia.jsearchgen.matrixsolver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public class MainPathLengthFitnessSolver implements IFitnessSolver {

	@Override
	public float ComputeFitness(UpperMatrix2D<Float> graph_matrix) {
		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(new UpperMatrix2D<Float>(graph_matrix));
		return fws.GetDistance();
	}

}
