package com.alesegdia.jsearchgen.test;

import com.alesegdia.jsearchgen.config.CacheType;
import com.alesegdia.jsearchgen.config.CombinatorType;
import com.alesegdia.jsearchgen.config.DoorGenType;
import com.alesegdia.jsearchgen.config.GenerationConfig;
import com.alesegdia.jsearchgen.config.ManagerType;
import com.alesegdia.jsearchgen.config.SolverType;
import com.alesegdia.jsearchgen.fitness.solver.MultiObjectiveFitness;
import com.alesegdia.jsearchgen.machine.DungeonMachine;

public class Test_Machine {

	public static void main (String args[]) throws Exception {
		
		GenerationConfig gc = new GenerationConfig();
		gc.num_instances_per_prefab = new int[2];
		gc.num_instances_per_prefab[0] = 	20;
		gc.num_instances_per_prefab[1] = 	20;
		gc.random_seed = 					0xDEADBEEF;
		gc.cloned_rooms = 					false;
		gc.doorgen_type = 					DoorGenType.ALL;
		gc.manager_type = 					ManagerType.PREFAB_MODEL;
		gc.cache_type = 					CacheType.NO_CACHE;
		gc.combinator_type = 				CombinatorType.ADAPTATIVE_PARAMETRIZED;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 100f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 100f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		gc.combinator_decay = 				0.5f;
		gc.combinator_attack = 				1.f;		

		gc.solver_type = 					SolverType.RANDOM;
		LaunchMachineWithParams(gc);
		
		gc.solver_type = 					SolverType.BEST_SEARCH;
		LaunchMachineWithParams(gc);
	}
	
	static void LaunchMachineWithParams(GenerationConfig gc) throws Exception {
		DungeonMachine dm = new DungeonMachine();
		dm.Reset(gc);
		dm.Run();
	}
	
}
