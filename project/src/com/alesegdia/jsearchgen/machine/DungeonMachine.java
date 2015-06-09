package com.alesegdia.jsearchgen.machine;

import com.alesegdia.jsearchgen.generatorsolver.BestSearchSolver;
import com.alesegdia.jsearchgen.generatorsolver.IMapGenSolver;
import com.alesegdia.jsearchgen.generatorsolver.RandomSolver;
import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.InstanceManager;
import com.alesegdia.jsearchgen.model.room.PrefabManager;
import com.alesegdia.jsearchgen.util.RNG;
import com.alesegdia.jsearchgen.view.GraphGridSolutionRenderer;

public class DungeonMachine {

	private GenerationConfig config;
	private GraphGridModel ggm;
	private IMapGenSolver mapgenerator;
	GraphGridSolutionRenderer ggsr;

	public void Reset(GenerationConfig config) throws Exception {
		
		this.config = config;
		RNG.rng = new RNG();
		RNG.rng.setSeed(config.random_seed);
		PrefabManager pmgr = new PrefabManager();
		InstanceManager rm = new InstanceManager(pmgr);
		rm.GenerateALot(config.num_instances_per_prefab);
		this.ggm = new GraphGridModel(rm);
		if( config.generation_type == GenerationType.RANDOM ) {
			this.mapgenerator = new RandomSolver();
		} else if( config.generation_type == GenerationType.BEST_SEARCH ) {
			this.mapgenerator = new BestSearchSolver();
		}
		ggm.cache_enabled = config.cache_enabled;
		this.ggsr = new GraphGridSolutionRenderer(ggm);

	}
	
	public void Run() throws Exception {
		long t1 = System.nanoTime();
		Generate();
		long t2 = System.nanoTime();
		long solve_time = t2 - t1;
		System.out.println("Config used: \n" + this.config);
		System.out.println("SOLVE TIME: " + solve_time/10e8);
		System.out.println("FITNESS TIME: " + GraphGridModel.fitness_time/10e8);
	}
	
	public void Generate() throws Exception {
		this.ggsr.Show();
		while(!this.ggm.IsComplete()){
			if(!this.mapgenerator.Step(this.ggm)) {
				throw new Exception("ERROR: can't build a complete solution from this partial solution and this list of remaining rooms " + this.ggm);
			}
			this.ggsr.Update();
		}
	}

}
