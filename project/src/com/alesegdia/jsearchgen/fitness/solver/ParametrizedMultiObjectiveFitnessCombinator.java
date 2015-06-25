package com.alesegdia.jsearchgen.fitness.solver;

public class ParametrizedMultiObjectiveFitnessCombinator implements IMultiObjectiveFitnessCombinator {

	protected float p_main_path_length, p_alt_path_branching, p_alt_path_length, p_room_condensation;

	public ParametrizedMultiObjectiveFitnessCombinator( float p1, float p2, float p3, float p4 ) {
		this.p_main_path_length = p1; this.p_alt_path_branching = p2; this.p_alt_path_length = p3; this.p_room_condensation = p4;
	}
	
	@Override
	public void CombineFitness(MultiObjectiveFitness mof) {
		mof.total_fitness = mof.main_path_length * p_main_path_length + mof.alt_path_branching * p_alt_path_branching + mof.alt_path_length * p_alt_path_length + mof.room_condensation * p_room_condensation;
	}

	@Override
	public void NotifySelected(MultiObjectiveFitness fitness) {
		// TODO Auto-generated method stub
	}

}
