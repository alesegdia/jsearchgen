package com.alesegdia.jsearchgen.machine;

import com.alesegdia.jsearchgen.test.ManagerType;

public class GenerationConfig {

	public long random_seed;
	public static int MAX_PREFABS;
	public int num_instances_per_prefab[];
	public GenerationType generation_type;
	public boolean render_at_finish = false;
	public boolean cache_enabled = false;
	public ManagerType manager_type;
	
	public GenerationConfig () {
		num_instances_per_prefab = new int[MAX_PREFABS];
	}

}
