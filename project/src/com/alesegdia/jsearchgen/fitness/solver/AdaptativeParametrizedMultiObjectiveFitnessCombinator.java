package com.alesegdia.jsearchgen.fitness.solver;

public class AdaptativeParametrizedMultiObjectiveFitnessCombinator extends ParametrizedMultiObjectiveFitnessCombinator {

	public AdaptativeParametrizedMultiObjectiveFitnessCombinator(float params[]) {
		super(params);
		// TODO Auto-generated constructor stub
	}
	
	float fitnesses[] = new float[4];
	
	float attack = 0.5f;
	float decay = 0.05f;
	
	@Override
	public void NotifySelected(MultiObjectiveFitness fitness) {
		fitnesses = fitness.objectives;
		int best = 0;
		for( int i = 1; i < 4; i++ ) {
			if( fitnesses[i] > fitnesses[best] ) {
				best = i;
			}
		}
		
		if( best == 0 ) {
			p_main_path_length *= decay;
			p_alt_path_branching *= attack;
			p_alt_path_length *= attack;
			p_room_condensation *= attack;
		} else if( best == 1 ) {
			p_main_path_length *= attack;
			p_alt_path_branching *= decay;
			p_alt_path_length *= attack;
			p_room_condensation *= attack;
		} else if( best == 2 ) {
			p_main_path_length *= attack;
			p_alt_path_branching *= attack;
			p_alt_path_length *= decay;
			p_room_condensation *= attack;
		} else if( best == 3 ) {
			p_main_path_length *= attack;
			p_alt_path_branching *= attack;
			p_alt_path_length *= attack;
			p_room_condensation *= decay;
		}

	}


}
