package com.alesegdia.jsearchgen.test;

import com.alesegdia.jsearchgen.machine.DungeonMachine;
import com.alesegdia.jsearchgen.machine.GenerationConfig;
import com.alesegdia.jsearchgen.machine.GenerationType;

public class Test_Machine {

	public static void main (String args[]) throws Exception {
		
		GenerationConfig gc = new GenerationConfig();
		gc.num_instances_per_prefab = new int[2];
		gc.num_instances_per_prefab[0] = 20;
		gc.num_instances_per_prefab[1] = 20;
		gc.render_at_finish = true;
		gc.random_seed = 0xDEADBEEF;
		gc.cache_enabled = false;
		gc.manager_type = ManagerType.PREFAB_MODEL;

		gc.generation_type = GenerationType.RANDOM;
		LaunchMachineWithParams(gc);
		
		gc.generation_type = GenerationType.BEST_SEARCH;
		LaunchMachineWithParams(gc);
	}
	
	static void LaunchMachineWithParams(GenerationConfig gc) throws Exception {
		DungeonMachine dm = new DungeonMachine();
		dm.Reset(gc);
		dm.Run();
	}
	
}
