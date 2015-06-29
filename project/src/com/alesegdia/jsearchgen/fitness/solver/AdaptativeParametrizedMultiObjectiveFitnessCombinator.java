package com.alesegdia.jsearchgen.fitness.solver;

public class AdaptativeParametrizedMultiObjectiveFitnessCombinator extends ParametrizedMultiObjectiveFitnessCombinator {

	public AdaptativeParametrizedMultiObjectiveFitnessCombinator(float params[]) {
		super(params);
		// TODO Auto-generated constructor stub
	}
	
	float fitnesses[] = new float[MultiObjectiveFitness.NUM_OBJECTIVES];
	
	float attack = 0.5f;
	float decay = 0.05f;
	
	@Override
	public void NotifySelected(MultiObjectiveFitness fitness) {
		fitnesses = fitness.objectives;
		int best = 0;
		for( int i = 1; i < MultiObjectiveFitness.NUM_OBJECTIVES; i++ ) {
			if( fitnesses[i] > fitnesses[best] ) {
				best = i;
			}
		}
		
		for( int i = 0; i < MultiObjectiveFitness.NUM_OBJECTIVES; i++ ) {
			if( i == best ) this.params[i] *= decay;
			else this.params[i] *= attack;
		}
	}

}
