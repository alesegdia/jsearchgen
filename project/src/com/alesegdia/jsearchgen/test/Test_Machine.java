package com.alesegdia.jsearchgen.test;

import com.alesegdia.jsearchgen.machine.DungeonMachine;
import com.alesegdia.jsearchgen.machine.GenerationConfig;
import com.alesegdia.jsearchgen.machine.GenerationType;

public class Test_Machine {

	public static void main (String args[]) throws Exception {
		DungeonMachine dm = new DungeonMachine();
		GenerationConfig gc = new GenerationConfig();
		gc.num_instances_per_prefab = new int[2];
		gc.num_instances_per_prefab[0] = 20;
		gc.num_instances_per_prefab[1] = 20;
		gc.render_at_finish = true;
		gc.generation_type = GenerationType.RANDOM;
		gc.random_seed = 0xDEADBEEF;
		gc.cache_enabled = true;
		dm.Reset(gc);
		dm.Run();
		
		DungeonMachine dm2 = new DungeonMachine();
		gc.generation_type = GenerationType.BEST_SEARCH;
		dm2.Reset(gc);
		dm2.Run();
	}
	
}
