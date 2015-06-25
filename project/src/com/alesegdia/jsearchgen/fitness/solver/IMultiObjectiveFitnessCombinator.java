package com.alesegdia.jsearchgen.fitness.solver;

public interface IMultiObjectiveFitnessCombinator {
	
	/**
	 * 
	 * @param main_path_length_fitness 
	 * @param alt_path_branching_fitness
	 * @param alt_path_length_fitness
	 * @param room_condensation_fitness
	 * @return
	 */
	public void CombineFitness(MultiObjectiveFitness mof );

	public void NotifySelected(MultiObjectiveFitness fitness);

}
