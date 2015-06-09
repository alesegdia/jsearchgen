package com.alesegdia.jsearchgen.machine;

import java.util.List;

import com.alesegdia.jsearchgen.generator.BestSearchGenerator;
import com.alesegdia.jsearchgen.generator.MapGenerator;
import com.alesegdia.jsearchgen.generator.RandomGenerator;
import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.InstanceManager;
import com.alesegdia.jsearchgen.model.room.PrefabManager;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.util.RNG;
import com.alesegdia.jsearchgen.view.GraphGridSolutionRenderer;

public class DungeonMachine {

	private GenerationConfig config;
	private GraphGridModel ggm;
	private MapGenerator mapgenerator;

	public void Reset(GenerationConfig config) throws Exception {
		
		this.config = config;
		RNG.rng = new RNG();
		RNG.rng.setSeed(config.random_seed);
		PrefabManager pmgr = new PrefabManager();
		InstanceManager rm = new InstanceManager(pmgr);
		rm.GenerateALot(config.num_instances_per_prefab);
		this.ggm = new GraphGridModel(rm);
		if( config.generation_type == GenerationType.RANDOM ) {
			this.mapgenerator = new RandomGenerator(ggm);
		} else if( config.generation_type == GenerationType.BEST_SEARCH ) {
			this.mapgenerator = new BestSearchGenerator(ggm);
		}

	}
	
	public void Run() throws Exception {
		long t1 = System.nanoTime();
		this.mapgenerator.Generate();
		long t2 = System.nanoTime();
		long solve_time = t2 - t1;
		System.out.println("Config used: \n" + this.config);
		System.out.println("SOLVE TIME: " + solve_time/10e8);
		System.out.println("FITNESS TIME: " + GraphGridModel.fitness_time/10e8);
		if( config.render_at_finish ) {
			GraphGridSolutionRenderer ggsr = new GraphGridSolutionRenderer(ggm);
			ggsr.Show();
		}
	}
	
}
