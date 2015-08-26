package com.alesegdia.jsearchgen.fitness.solver;

import com.alesegdia.jsearchgen.matrixsolver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;
import com.alesegdia.jsearchgen.util.Vec2;

public class MainPathLengthFitnessSolver implements IFitnessSolver {

	@Override
	public MultiObjectiveFitness ComputeFitness(GraphGridModel ggm, int riID, Vec2 relativeToSolutionMap) {
		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(new UpperMatrix2D<Float>(ggm.graph_matrix));
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
