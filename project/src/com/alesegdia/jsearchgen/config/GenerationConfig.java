package com.alesegdia.jsearchgen.config;

import com.alesegdia.jsearchgen.fitness.solver.MultiObjectiveFitness;


public class GenerationConfig {

	public long random_seed; 					// seed for the RNG
	public int num_instances_per_prefab[]; 	// num instances per prefab
	public SolverType solver_type; 				// solver to perform each generation step
	public ManagerType manager_type; 			// defines the way to manage room instances
	public DoorGenType doorgen_type; 			// way to generate doors at startup (random, 
	public boolean cloned_rooms; 				// set to true if want to use same rooms for each prefab instance
	public CacheType cache_type;
	public float bestsearch_dpe_divisor; 		// defines how many DPEs will be taken from the set at each step
												// 1f 	-> takes all
												// 0f 	-> takes 1
												// 0.5f	-> takes set.size/2
	public GenerationConfig (int num_prefabs) {
		num_instances_per_prefab = new int[num_prefabs];
	}
	
	public CombinatorType combinator_type;
	public float combinator_decay;
	public float combinator_attack;
	public float[] fitnesses_params = new float[MultiObjectiveFitness.NUM_OBJECTIVES];
	public int doorgen_divisor = 2;

	public int refresher_divisor = 1;
	
	
}
