package com.alesegdia.jsearchgen.test;

import com.alesegdia.jsearchgen.config.DoorGenType;
import com.alesegdia.jsearchgen.config.GenerationConfig;
import com.alesegdia.jsearchgen.config.ManagerType;
import com.alesegdia.jsearchgen.config.SolverType;
import com.alesegdia.jsearchgen.machine.DungeonMachine;

public class Test_Machine {

	public static void main (String args[]) throws Exception {
		
		GenerationConfig gc = new GenerationConfig();
		gc.num_instances_per_prefab = new int[2];
		gc.num_instances_per_prefab[0] = 20;
		gc.num_instances_per_prefab[1] = 20;
		gc.random_seed = 0xDEADBEEF;
		gc.cache_enabled = false;
		gc.manager_type = ManagerType.PREFAB_MODEL;
		gc.doorgen_type = DoorGenType.ALL;
		gc.cloned_rooms = false;

		gc.solver_type = SolverType.RANDOM;
		LaunchMachineWithParams(gc);
		
		gc.solver_type = SolverType.BEST_SEARCH;
		LaunchMachineWithParams(gc);
	}
	
	static void LaunchMachineWithParams(GenerationConfig gc) throws Exception {
		DungeonMachine dm = new DungeonMachine();
		dm.Reset(gc);
		dm.Run();
	}
	
}
