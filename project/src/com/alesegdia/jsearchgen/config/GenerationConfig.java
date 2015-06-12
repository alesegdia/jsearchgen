package com.alesegdia.jsearchgen.config;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;


public class GenerationConfig {

	public long random_seed; 					// seed for the RNG
	public static int MAX_PREFABS; 			// max prefabs there will be
	public int num_instances_per_prefab[]; 	// num instances per prefab
	public SolverType solver_type; 				// solver to perform each generation step
	public ManagerType manager_type; 			// defines the way to manage room instances
	public DoorGenType doorgen_type; 			// way to generate doors at startup (random, 
	public boolean cloned_rooms; 				// set to true if want to use same rooms for each prefab instance
	public CacheType cache_type;
	
	public GenerationConfig () {
		num_instances_per_prefab = new int[MAX_PREFABS];
	}

}