package com.alesegdia.jsearchgen.fitness.solver;

public class AdaptativeParametrizedMultiObjectiveFitnessCombinator extends ParametrizedMultiObjectiveFitnessCombinator {

	public AdaptativeParametrizedMultiObjectiveFitnessCombinator(float p1,
			float p2, float p3, float p4) {
		super(p1, p2, p3, p4);
		// TODO Auto-generated constructor stub
	}
	
	float fitnesses[] = new float[4];
	
	float attack = 0.5f;
	float decay = 0.05f;
	
	@Override
	public void NotifySelected(MultiObjectiveFitness fitness) {
		fitnesses[0] = fitness.main_path_length;
		fitnesses[1] = fitness.alt_path_branching;
		fitnesses[2] = fitness.alt_path_length;
		fitnesses[3] = fitness.room_condensation;
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
