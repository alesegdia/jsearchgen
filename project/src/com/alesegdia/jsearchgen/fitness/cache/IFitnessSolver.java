package com.alesegdia.jsearchgen.fitness.cache;

import com.alesegdia.jsearchgen.fitness.solver.MultiObjectiveFitness;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public interface IFitnessSolver {
	
	public MultiObjectiveFitness ComputeFitness( UpperMatrix2D<Float> graph_matrix );

	public void NotifySelected(MultiObjectiveFitness fitness);

}
