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
	
	public GenerationConfig (int num_prefabs) {
		num_instances_per_prefab = new int[num_prefabs];
	}
	
	public CombinatorType combinator_type;
	public float combinator_decay;
	public float combinator_attack;
	public float[] fitnesses_params = new float[MultiObjectiveFitness.NUM_OBJECTIVES];
	public int divisor_n = 2;

	
	
}
