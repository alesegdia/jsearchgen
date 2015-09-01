package com.alesegdia.jsearchgen.test;

import com.alesegdia.jsearchgen.config.CacheType;
import com.alesegdia.jsearchgen.config.CombinatorType;
import com.alesegdia.jsearchgen.config.DoorGenType;
import com.alesegdia.jsearchgen.config.GenerationConfig;
import com.alesegdia.jsearchgen.config.ManagerType;
import com.alesegdia.jsearchgen.config.SolverType;
import com.alesegdia.jsearchgen.fitness.solver.MultiObjectiveFitness;
import com.alesegdia.jsearchgen.machine.DungeonMachine;
import com.alesegdia.jsearchgen.model.room.PrefabManager;

public class Test_Machine {

	private static PrefabManager pmgr;

	public static void main (String args[]) throws Exception {
		pmgr = new PrefabManager();
		pmgr.AddPrefab("rooms/room0.json");
		pmgr.AddPrefab("rooms/room1.json");

		GenerationConfig gc = new GenerationConfig(pmgr.numPrefabs());
		gc.num_instances_per_prefab = new int[2];
		gc.num_instances_per_prefab[0] = 	30;
		gc.num_instances_per_prefab[1] = 	30;
		gc.random_seed = 					0xDEADBEEF;
		gc.cloned_rooms = 					false;
		gc.doorgen_divisor = 				0.5f;
		gc.bestsearch_dpe_divisor = 		1f;
		gc.doorgen_type = 					DoorGenType.RANDOM;
		gc.doorgen_random_doors = 			0.5f;
		gc.manager_type = 					ManagerType.PREFAB_MODEL;
		gc.cache_type = 					CacheType.REFRESHER;
		gc.refresher_n = 					5;
		gc.combinator_type = 				CombinatorType.PARAMETRIZED;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 100f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		gc.combinator_decay = 				0.5f;
		gc.combinator_attack = 				1.f;
		gc.bestsearch_dpe_divisor = 		0.9f;

		gc.solver_type = 					SolverType.RANDOM;
		//LaunchMachineWithParams(gc);
		
		gc.solver_type = 					SolverType.BEST_SEARCH;
		//LaunchMachineWithParams(gc);

		//gc.cache_type = CacheType.NO_CACHE;
		gc.combinator_type = 				CombinatorType.PARAMETRIZED;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 100f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		gc.combinator_decay = 				0.5f;
		gc.combinator_attack = 				1.f;		
		//LaunchMachineWithParams(gc);

		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 100f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		//LaunchMachineWithParams(gc);

		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 100f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		//LaunchMachineWithParams(gc);

		// MAINalt
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 0f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 1f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 5.8f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		//LaunchMachineWithParams(gc);

		// MAINaltbranch
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 1f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 1f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 5.8f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		//LaunchMachineWithParams(gc);

		// adaptMAINaltbranch
		gc.combinator_type = 				CombinatorType.ADAPTATIVE_PARAMETRIZED;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 1f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 1f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 10f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		gc.combinator_decay = 				0.5f;
		gc.combinator_attack = 				1.f;
		//LaunchMachineWithParams(gc);

		// adaptmainaltbranch
		gc.combinator_type = 				CombinatorType.ADAPTATIVE_PARAMETRIZED;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 10f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 10f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 10f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		gc.combinator_decay = 				0.5f;
		gc.combinator_attack = 				1f;
		//LaunchMachineWithParams(gc);

		// adaptmainaltbranch2
		gc.combinator_type = 				CombinatorType.ADAPTATIVE_PARAMETRIZED;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_BRANCHING] = 10f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ALT_PATH_LENGTH] = 10f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_MAIN_PATH_LENGTH] = 10f;
		gc.fitnesses_params[MultiObjectiveFitness.FO_ROOM_CONDENSATION] = 0f;
		gc.combinator_decay = 				0.9f;
		gc.combinator_attack = 				1.1f;
		//LaunchMachineWithParams(gc);
		

	}
	
	static void LaunchMachineWithParams(GenerationConfig gc) throws Exception {
		DungeonMachine dm = new DungeonMachine();
		dm.setShow(true);
		dm.Reset(gc, pmgr);
		dm.Run();
	}
	
}
