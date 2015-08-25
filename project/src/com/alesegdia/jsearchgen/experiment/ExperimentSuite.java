package com.alesegdia.jsearchgen.experiment;

import java.util.ArrayList;
import java.util.List;

import com.alesegdia.jsearchgen.config.CacheType;
import com.alesegdia.jsearchgen.config.CombinatorType;
import com.alesegdia.jsearchgen.config.DoorGenType;
import com.alesegdia.jsearchgen.config.GenerationConfig;
import com.alesegdia.jsearchgen.config.ManagerType;
import com.alesegdia.jsearchgen.config.SolverType;
import com.alesegdia.jsearchgen.machine.DungeonMachine;
import com.alesegdia.jsearchgen.model.map.TileMap;
import com.alesegdia.jsearchgen.model.map.TileType;
import com.alesegdia.jsearchgen.model.room.PrefabManager;
import com.alesegdia.jsearchgen.model.room.RoomPrefab;
import com.alesegdia.jsearchgen.util.RNG;
import com.alesegdia.jsearchgen.util.Vec2;

public class ExperimentSuite {

	public static PrefabManager makeRandomModels( int numModels, Vec2 sizeRange ) {
		PrefabManager pm = new PrefabManager();
		for( int i = 0; i < numModels; i++ ) {
			int w = RNG.rng.nextInt(sizeRange.x, sizeRange.y);
			int h = RNG.rng.nextInt(sizeRange.x, sizeRange.y);
			TileMap tm = new TileMap(h+2, w+2, TileType.FREE);
			for( int c = 1; c < tm.cols-1; c++ ) {
				for( int r = 1; r < tm.rows-1; r++ ) {
					tm.Set(r, c, TileType.USED);
				}
			}
			for( int c = 1; c < tm.cols-1; c++ ) {
				tm.Set(1, c, TileType.WALL );
				tm.Set(h, c, TileType.WALL );
			}
			for( int r = 1; r < tm.rows-1; r++ ) {
				tm.Set(r, 1, TileType.WALL );
				tm.Set(r, w, TileType.WALL );
			}
			pm.AddPrefab(tm);
		}
		return pm;
	}
	
	
	class ExperimentInfoEntry {
		Vec2 size;
		int roomsPerType;
	}
	
	class ExperimentResult {
		float time = 0f;
	}

	public static class IncompleteGenerationConfig extends GenerationConfig {

		public IncompleteGenerationConfig() {
			super(0);
		}
		
		public IncompleteGenerationConfig(int num_prefabs) {
			super(num_prefabs);
		}
		
	}

	public static void main(String[] args) throws Exception {
		RNG.rng = new RNG();
		RNG.rng.setSeed(1234);

		List<Vec2> sizes = new ArrayList<Vec2>();
		sizes.add(new Vec2(5, 10));
		sizes.add(new Vec2(20, 30));

		List<Integer> numModels = new ArrayList<Integer>();
		numModels.add(2);
		numModels.add(5);
		numModels.add(10);

		List<Integer> roomsPerType = new ArrayList<Integer>();
		roomsPerType.add(5);
		roomsPerType.add(10);
		roomsPerType.add(15);

		List<IncompleteGenerationConfig> genConfigs = new ArrayList<IncompleteGenerationConfig>();
		IncompleteGenerationConfig icfg;

		icfg = new IncompleteGenerationConfig();
		icfg.solver_type = SolverType.RANDOM;
		icfg.manager_type = ManagerType.PREFAB_MODEL;
		icfg.cache_type = CacheType.NO_CACHE;
		icfg.doorgen_type = DoorGenType.ALL;

		genConfigs.add(icfg);

		/******************************************/
		icfg = new IncompleteGenerationConfig();

		icfg.solver_type = SolverType.BEST_SEARCH;
		icfg.cache_type = CacheType.NO_CACHE;
		icfg.manager_type = ManagerType.PREFAB_MODEL;
		icfg.combinator_type = CombinatorType.PARAMETRIZED;
		icfg.doorgen_type = DoorGenType.ALL;

		icfg.combinator_attack = 0;
		icfg.combinator_decay = 0;

		icfg.fitnesses_params[0] = 1;
		icfg.fitnesses_params[1] = 1;
		icfg.fitnesses_params[2] = 1;
		icfg.fitnesses_params[3] = 1;

		icfg.random_seed = 1234;

		genConfigs.add(icfg);
		/******************************************/

		System.in.read();
		System.out.println("sizes\t\tnumModels\troomsPerModel\tconfig\ttime");
		for( int numModel : numModels ) {

			for( Vec2 size : sizes ) {
				for( int numRooms : roomsPerType ) {
					int k = 0;
					for( IncompleteGenerationConfig cfg : genConfigs ) {
						DungeonMachine m = new DungeonMachine();
						PrefabManager pmgr = makeRandomModels(numModel, size);
						GenerationConfig gcfg = new GenerationConfig(pmgr.numPrefabs());
						gcfg.solver_type = cfg.solver_type;
						gcfg.cache_type = cfg.cache_type;
						gcfg.manager_type = cfg.manager_type;
						gcfg.combinator_type = cfg.combinator_type;
						gcfg.doorgen_type = cfg.doorgen_type;

						gcfg.combinator_attack = cfg.combinator_attack;
						gcfg.combinator_decay = cfg.combinator_decay;

						gcfg.fitnesses_params = cfg.fitnesses_params;
						gcfg.random_seed = cfg.random_seed;
						gcfg.num_instances_per_prefab = new int[pmgr.numPrefabs()];
						for( int i = 0; i < gcfg.num_instances_per_prefab.length; i++ ) {
							gcfg.num_instances_per_prefab[i] = numRooms;
						}

						m.Reset(gcfg, pmgr);
						float time = m.Generate();
						System.out.println(size + "\t\t" + numModel + "\t\t" + numRooms + "\t\t" + k + "\t" + time/10e8);
						//System.out.println("SIZE: " + "(" + size.x + "," + size.y + "), numRooms: " + numRooms + ", config: " + k);
						//System.out.println("time: " + time/10e8);
						k++;
					}
				}
				System.out.println();
			}
		}
	}
	
}
