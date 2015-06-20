package com.alesegdia.jsearchgen.fitness;

import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public interface IFitnessSolver {
	
	public float ComputeFitness( UpperMatrix2D<Float> graph_matrix );

}
