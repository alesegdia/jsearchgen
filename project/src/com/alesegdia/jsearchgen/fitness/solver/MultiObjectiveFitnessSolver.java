package com.alesegdia.jsearchgen.fitness.solver;

import com.alesegdia.jsearchgen.matrixsolver.BFSSolver;
import com.alesegdia.jsearchgen.matrixsolver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;
import com.alesegdia.jsearchgen.util.Vec2;

public class MultiObjectiveFitnessSolver implements IFitnessSolver {

	private IMultiObjectiveFitnessCombinator combinator;
	
	public MultiObjectiveFitnessSolver( IMultiObjectiveFitnessCombinator combinator ) {
		this.combinator = combinator;
	}

	private MultiObjectiveFitness ComputeAllFitness(GraphGridModel ggm, int riID, Vec2 relativeToSolutionMap) {
		UpperMatrix2D<Float> clone = new UpperMatrix2D<Float>(ggm.graph_matrix);
		
		//BFSSolver ds = new BFSSolver();
		//ds.Solve(graph_matrix, riID, relativeToSolutionMap);

		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(new UpperMatrix2D<Float>(ggm.graph_matrix));
		
		//return fws.GetDistance();

		FloodFillGraphMatrixSolver ffs = new FloodFillGraphMatrixSolver();
		ffs.Solve(clone, fws.GetPath());

		MultiObjectiveFitness mof = new MultiObjectiveFitness();
		mof.objectives[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = ffs.GetBranchingFitness();
		mof.objectives[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = ffs.GetAltPathLengthFitness();
		//mof.main_path_length = fws.GetDistance();
		mof.objectives[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = fws.GetPath().size();
		return mof;
	}
	
	@Override
	public MultiObjectiveFitness ComputeFitness(GraphGridModel ggm, int riID, Vec2 relativeToSolutionMap) {
		MultiObjectiveFitness mof = ComputeAllFitness(ggm, riID, relativeToSolutionMap);
		combinator.CombineFitness( mof );
		return mof;
	}

	@Override
	public void NotifySelected(MultiObjectiveFitness fitness) {
		combinator.NotifySelected(fitness);
	}

}
