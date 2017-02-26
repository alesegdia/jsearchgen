package com.alesegdia.jsearchgen.experiment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

	private static PrefabManager makeRandomModels(int numModel, int size) {
		return makeRandomModels(numModel, new Vec2(size, size));
	}
	
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

	public static class IncompleteGenerationConfig extends GenerationConfig implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;

		public IncompleteGenerationConfig(String string) {
			this(0);
			combinator_type = CombinatorType.PARAMETRIZED;
			combinator_attack = 0;
			combinator_decay = 0;
			fitnesses_params[0] = 1;
			fitnesses_params[1] = 1;
			fitnesses_params[2] = 1;
			fitnesses_params[3] = 1;

			this.name = string;
		}
		
		public IncompleteGenerationConfig(int num_prefabs) {
			super(num_prefabs);
		}
		
		HashMap<String, String> propsMap = new HashMap<String, String>();
		
		public void buildPropsMap() {
			
			/* SOLVER TYPE **********************************************/
			if( this.solver_type == SolverType.BEST_SEARCH ) {
				propsMap.put("SolverType", "BestSearch");
				propsMap.put("BestSearch DPE Divisor", String.valueOf(this.bestsearch_dpe_divisor));
			} else if( this.solver_type == SolverType.RANDOM ) {
				propsMap.put("SolverType", "Random");
			} else if( this.solver_type == SolverType.MIXED ) {
				propsMap.put("SolverType", "Mixed");
				propsMap.put("BestSearch DPE Divisor", String.valueOf(this.bestsearch_dpe_divisor));
			}
			/************************************************************/
			
			/* CACHE TYPE ***********************************************/
			if( this.cache_type == CacheType.NO_CACHE ) {
				propsMap.put("CacheType", "NO CACHE");
			} else if( this.cache_type == CacheType.ALWAYS ) {
				propsMap.put("CacheType", "ALWAYS");
			} else if( this.cache_type == CacheType.REFRESHER ) {
				propsMap.put("CacheType", "REFRESHER");
				propsMap.put("Refresher cache divisor", String.valueOf(this.refresher_n));
			}
			/************************************************************/

			if( this.doorgen_type == DoorGenType.ALL ) {
				propsMap.put("DoorGenType", "ALL");
			} else if( this.doorgen_type == DoorGenType.RANDOM ) {
				propsMap.put("DoorGenType", "RANDOM");
				propsMap.put("Random doors param", ""+this.doorgen_random_doors);
			} else if( this.doorgen_type == DoorGenType.DIVISOR ) {
				propsMap.put("DoorGenType", "DIVISOR");
				propsMap.put("Divisor doors param", "" + this.doorgen_divisor);
			}
		}
		
		public String toString() {
			if( propsMap.size() == 0 ) {
				this.buildPropsMap();
			}
			String s = "";
			s += "\\begin{center}\n" +
					   "	\\begin{tabular}{ | c | c | }\n\\hline\n" +
					   " 		Property & Value \\\\ \\hline\n";
			for( Map.Entry<String, String> entry : propsMap.entrySet() ) {
				s += entry.getKey() + " & " + entry.getValue() + " \\\\ \n";
			}
			s += "\\hline\n" +
				 "	\\end{tabular}\n" +
				 "\\end{center}\n";
			return s;
		}
		
	}
	
	public static class TestCase implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public IncompleteGenerationConfig cfg;
		public List<TestEntry> entries = new LinkedList<TestEntry>();
		
		public String toString() {
			String s = cfg.toString() + "\n";
			s += "\\begin{center}\n" +
				 "	\\begin{tabular}{ | c | c | c | c | c | }\n\\hline\n";

			s += "Tam. habs. & Num. modelos & Instancias/modelo & Total habs. & Tiempo ejec. \\\\ \\hline \n";
			for( TestEntry te : entries ) {
				s += te.toString();
			}
			
			s += "\\hline\n" +
					 "	\\end{tabular}\n" +
					 "\\end{center}\n";
					
			return s;
		}

		
	}
	
	public static class TestEntry implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		int size;
		int numModels;
		int numRoomsPerModel;
		float time;
		
		public String toString() {
			return size + " & " + numModels + " & " + numRoomsPerModel + " & " + numModels * numRoomsPerModel + " & " + String.format(Locale.ENGLISH, "%.4f", time) + " \\\\ \n";
		}
	}
	
	public static IncompleteGenerationConfig MakeDpeDivConfig(String name, float dpediv) {
		IncompleteGenerationConfig bs_dpediv = new IncompleteGenerationConfig(name);
		bs_dpediv.solver_type = SolverType.BEST_SEARCH;
		bs_dpediv.bestsearch_dpe_divisor = dpediv;
		bs_dpediv.cache_type = CacheType.NO_CACHE;
		bs_dpediv.refresher_n = 10;
		bs_dpediv.manager_type = ManagerType.PREFAB_MODEL;
		bs_dpediv.doorgen_type = DoorGenType.ALL;
		bs_dpediv.doorgen_random_doors = 0.9f;
		return bs_dpediv;
	}
	
	public static IncompleteGenerationConfig MakeRandoorsConfig( String name, float randoorsParam ) {
		IncompleteGenerationConfig bs_randoors = new IncompleteGenerationConfig(name);
		bs_randoors.solver_type = SolverType.BEST_SEARCH;
		bs_randoors.bestsearch_dpe_divisor = 1f;
		bs_randoors.cache_type = CacheType.NO_CACHE;
		bs_randoors.manager_type = ManagerType.PREFAB_MODEL;
		bs_randoors.doorgen_type = DoorGenType.RANDOM;
		bs_randoors.doorgen_random_doors = randoorsParam;
		return bs_randoors;
	}
	
	public static IncompleteGenerationConfig MakeRefresherConfig(String name, int refresherN) {
		IncompleteGenerationConfig bs_refresher = new IncompleteGenerationConfig(name);
		bs_refresher.solver_type = SolverType.BEST_SEARCH;
		bs_refresher.bestsearch_dpe_divisor = 1f;
		bs_refresher.cache_type = CacheType.REFRESHER;
		bs_refresher.refresher_n = refresherN;
		bs_refresher.manager_type = ManagerType.PREFAB_MODEL;
		bs_refresher.doorgen_type = DoorGenType.ALL;
		return bs_refresher;
	}
	
	public static TestCase makeTestCase( String tcName, IncompleteGenerationConfig cfg,
			List<Integer> seeds, 		List<Integer> sizes,
			List<Integer> numModels, List<Integer> roomsPerType) throws Exception
	{
		System.out.println("TESTCASE: " + tcName);
		TestCase tc = new TestCase();
		tc.cfg = cfg;
	
		for (int size : sizes) {
			System.out.println("64 / " + size + " = " + 64/size + " rooms per side");
			System.out.println(64/size + " ^ 2 = " + ((int)Math.pow(64/size, 2)) + " rooms to fit");
			for (int numModel : numModels) {

				for (int numRooms : roomsPerType) {
					TestEntry te = new TestEntry();
					te.size = size;
					te.numModels = numModel;
					te.numRoomsPerModel = numRooms;
					tc.entries.add(te);
					float time = 0f;
					for( int seed : seeds ) {
						DungeonMachine m = new DungeonMachine();
						//PrefabManager pmgr = makeRandomModels(numModel, size);
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
						gcfg.random_seed = seed;
						gcfg.doorgen_random_doors = cfg.doorgen_random_doors;
						gcfg.doorgen_divisor = cfg.doorgen_divisor;
						gcfg.bestsearch_dpe_divisor = cfg.bestsearch_dpe_divisor;
						gcfg.refresher_n = cfg.refresher_n;
						gcfg.num_instances_per_prefab = new int[pmgr.numPrefabs()];
						
						for (int i = 0; i < gcfg.num_instances_per_prefab.length; i++) {
							gcfg.num_instances_per_prefab[i] = numRooms;
						}

						m.Reset(gcfg, pmgr);
						float t = m.Generate();
						time += t;
						// System.out.println(size + "\t\t" + numModel + "\t\t"
						// + numRooms + "\t\t" + time/10e8);
					}
					te.time = (time/10e8f) / seeds.size();
					System.out.print(te);
				}
			}
		}
		System.out.println("============");
		FileOutputStream fos = new FileOutputStream(tcName);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(tc);
		oos.close();
		fos.close();
		return tc;
	}
	
	public static List<Integer> mkList( Integer... args ) {
		return Arrays.asList(args);
	}

	public static void main(String[] args) throws Exception {
		RNG.rng = new RNG();
		RNG.rng.setSeed(1234);

		System.in.read();
		
		IncompleteGenerationConfig icfg;
		
		List<Integer> seeds = mkList(0xDEADBEEF, 0xDEADC0DE, 0xDEADF155, 0xFACEFEED, 0xFACEF155);
		
		/******************************************/
		icfg = new IncompleteGenerationConfig("Random");
		icfg.solver_type = SolverType.RANDOM;
		icfg.manager_type = ManagerType.PREFAB_MODEL;
		
		icfg.doorgen_type = DoorGenType.RANDOM;
		icfg.doorgen_random_doors = 2;

		//genConfigs.add(icfg);
		/******************************************/

		/****************************************************************/
		/* OPTIMAL: BESTSEARCH, REFRESHER 10, DPEDIVISOR 0.9, RANDOM DOORGEN 0.9 */
		IncompleteGenerationConfig bs_opt = new IncompleteGenerationConfig("bs-opt");
		bs_opt.solver_type = SolverType.BEST_SEARCH;
		bs_opt.bestsearch_dpe_divisor = 0.9f;
		bs_opt.cache_type = CacheType.REFRESHER;
		bs_opt.refresher_n = 10;
		bs_opt.manager_type = ManagerType.PREFAB_MODEL;
		bs_opt.doorgen_type = DoorGenType.RANDOM;
		bs_opt.doorgen_random_doors = 0.5f;

		/******************************************/

		/****************************************************************/
		/* OPTIMAL: BESTSEARCH, REFRESHER 10, DPEDIVISOR 0.5, RANDOM DOORGEN 0.3 */
		IncompleteGenerationConfig bs_opt_varfix = new IncompleteGenerationConfig("bs-opt-variability-fix");
		bs_opt_varfix.solver_type = SolverType.BEST_SEARCH;
		bs_opt_varfix.bestsearch_dpe_divisor = 0.5f;
		bs_opt_varfix.cache_type = CacheType.REFRESHER;
		bs_opt_varfix.refresher_n = 10;
		bs_opt_varfix.manager_type = ManagerType.PREFAB_MODEL;
		bs_opt_varfix.doorgen_type = DoorGenType.RANDOM;
		bs_opt_varfix.cloned_rooms = true;
		bs_opt_varfix.doorgen_random_doors = 0.3f;
		/******************************************/
		
		/****************************************************************/
		/* OPTIMAL: BESTSEARCH, REFRESHER 10, DPEDIVISOR 0.5, RANDOM DOORGEN 0.3 */
		IncompleteGenerationConfig bs_opt_varfix2 = new IncompleteGenerationConfig("bs-opt-variability-fix");
		bs_opt_varfix2.solver_type = SolverType.BEST_SEARCH;
		bs_opt_varfix2.bestsearch_dpe_divisor = 0.2f;
		bs_opt_varfix2.cache_type = CacheType.REFRESHER;
		bs_opt_varfix2.refresher_n = 10;
		bs_opt_varfix2.manager_type = ManagerType.PREFAB_MODEL;
		bs_opt_varfix2.doorgen_type = DoorGenType.RANDOM;
		bs_opt_varfix2.cloned_rooms = true;
		bs_opt_varfix2.doorgen_random_doors = 0.1f;
		/******************************************/
		
		/*************************************************/
		/* BESTSEARCH, NO CACHE, ALLDOORS, DPEDIVISOR 1f */
		IncompleteGenerationConfig bs_nocache = new IncompleteGenerationConfig("bs-nocache");
		bs_nocache.solver_type = SolverType.BEST_SEARCH;
		bs_nocache.bestsearch_dpe_divisor = 1f;
		bs_nocache.cache_type = CacheType.NO_CACHE;
		bs_nocache.refresher_n = 10;
		bs_nocache.manager_type = ManagerType.PREFAB_MODEL;
		bs_nocache.doorgen_type = DoorGenType.ALL;
		bs_nocache.doorgen_random_doors = 0.9f;
		/******************************************/

		/*************************************************/
		/* BESTSEARCH, ALWAYS CACHE, ALLDOORS, DPEDIVISOR 1f */
		IncompleteGenerationConfig bs_alwayscache = new IncompleteGenerationConfig("bs-alwayscache");
		bs_alwayscache.solver_type = SolverType.BEST_SEARCH;
		bs_alwayscache.bestsearch_dpe_divisor = 1f;
		bs_alwayscache.cache_type = CacheType.ALWAYS;
		bs_alwayscache.refresher_n = 10;
		bs_alwayscache.manager_type = ManagerType.PREFAB_MODEL;
		bs_alwayscache.doorgen_type = DoorGenType.ALL;
		bs_alwayscache.doorgen_random_doors = 0.9f;
		/******************************************/

		/*************************************************/
		/* BESTSEARCH, REFRESHER 15, ALLDOORS, DPEDIVISOR 1f */
		IncompleteGenerationConfig bs_refresher2 = MakeRefresherConfig("bs-refresher2", 2);
		IncompleteGenerationConfig bs_refresher5 = MakeRefresherConfig("bs-refresher5", 5);
		IncompleteGenerationConfig bs_refresher10 = MakeRefresherConfig("bs-refresher10", 10);
		/******************************************/

		/*************************************************/
		/* BESTSEARCH, NO CACHE, RANDOM 0.4f, DPEDIVISOR 1f */
		IncompleteGenerationConfig bs_randoors_04f = MakeRandoorsConfig("bs-randoors-04f", 0.4f);
		IncompleteGenerationConfig bs_randoors_06f = MakeRandoorsConfig("bs-randoors-06f", 0.6f);
		IncompleteGenerationConfig bs_randoors_08f = MakeRandoorsConfig("bs-randoors-08f", 0.8f);
		/******************************************/

		/*************************************************/
		/* BESTSEARCH, NO CACHE, ALLDOORS, DPEDIVISOR 0.7f */
		IncompleteGenerationConfig bs_dpediv075f = MakeDpeDivConfig("bs_dpediv075f", 0.75f);
		IncompleteGenerationConfig bs_dpediv085f = MakeDpeDivConfig("bs_dpediv085f", 0.85f);
		IncompleteGenerationConfig bs_dpediv095f = MakeDpeDivConfig("bs_dpediv095f", 0.95f);
		/******************************************/
		
		/*
		makeTestCase("bs-randoors08f", bs_randoors_08f, seeds,
				mkList(10),
				mkList(4, 6),
				mkList(8, 10));
		
		makeTestCase("bs-randoors06f", bs_randoors_06f, seeds,
				mkList(10),
				mkList(4, 6),
				mkList(8, 10));

		makeTestCase("bs-randoors04f", bs_randoors_04f, seeds,
				mkList(10),
				mkList(4, 6),
				mkList(8, 10));
		
		makeTestCase("bs-refresher2", bs_refresher2, seeds,
				mkList(6),
				mkList(4, 6),
				mkList(10, 15));		

		makeTestCase("bs-refresher5", bs_refresher5, seeds,
				mkList(6),
				mkList(4, 6),
				mkList(10, 15));		

		makeTestCase("bs-refresher10", bs_refresher10, seeds,
				mkList(6),
				mkList(4, 6),
				mkList(10, 15));		

		
		makeTestCase("bs-dpediv095f", bs_dpediv095f, seeds,
				mkList(10),
				mkList(2, 4),
				mkList(20, 30));
		
		makeTestCase("bs-dpediv085f", bs_dpediv085f, seeds,
				mkList(10),
				mkList(2, 4),
				mkList(20, 30));
		
		makeTestCase("bs-dpediv75f", bs_dpediv075f, seeds,
				mkList(10),
				mkList(2, 4),
				mkList(20, 30));

		makeTestCase("bs-opt-real-sample", bs_opt, seeds,
				mkList(6),
				mkList(5, 10, 15, 20),
				mkList(1, 2, 3, 4));

		makeTestCase("bs-opt-medium-sample", bs_opt, seeds,
				mkList(10), 		// room sizes
				mkList(5, 10, 15), 		// num models
				mkList(1, 2, 3));		// rooms per model
		
		makeTestCase("bs-opt-variabilty-sample", bs_opt, seeds,
				mkList(6), 		// room sizes
				mkList(20, 30, 40, 50), 		// num models
				mkList(1, 2));		// rooms per model
		makeTestCase("bs-opt-variabilty-sample-fixed", bs_opt_varfix, seeds,
				mkList(6), 		// room sizes
				mkList(20, 30, 40, 50), 		// num models
				mkList(1, 2, 3));		// rooms per model
		
		makeTestCase("bs-alwayscache-compare", bs_alwayscache, mkList(0),
				mkList(8), 		// room sizes
				mkList(10), 		// num models
				mkList(2, 4, 6, 8));		// rooms per model

		makeTestCase("bs-opt-compare", bs_opt, mkList(0),
				mkList(8), 		// room sizes
				mkList(10), 		// num models
				mkList(2, 4, 6, 8));		// rooms per model
		
		makeTestCase("bs-nocache-compare", bs_nocache, mkList(0),
				mkList(8), 		// room sizes
				mkList(10), 		// num models
				mkList(2, 4, 6, 8));		// rooms per model
		
		makeTestCase("bs-opt-big-sample", bs_opt_varfix2, mkList(0),
				mkList(20), 		// room sizes
				mkList(10, 20, 30, 40, 50), 		// num models
				mkList(1, 2, 3, 4));		// rooms per model
		*/
	}

}
