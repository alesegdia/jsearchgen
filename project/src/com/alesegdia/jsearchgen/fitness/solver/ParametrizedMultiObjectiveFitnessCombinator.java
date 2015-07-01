package com.alesegdia.jsearchgen.fitness.solver;

public class ParametrizedMultiObjectiveFitnessCombinator implements IMultiObjectiveFitnessCombinator {

	protected float[] params;

	public ParametrizedMultiObjectiveFitnessCombinator( float params[] ) {
		this.params = params;
	}
	
	@Override
	public void CombineFitness(MultiObjectiveFitness mof) {
		for( int i = 0; i < MultiObjectiveFitness.NUM_OBJECTIVES; i++ ) {
			mof.total_fitness += mof.objectives[i] * this.params[i];
		}
	}

	@Override
	public void NotifySelected(MultiObjectiveFitness fitness) {
		// TODO Auto-generated method stub
	}

}
