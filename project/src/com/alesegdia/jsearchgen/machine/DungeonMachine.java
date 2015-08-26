package com.alesegdia.jsearchgen.machine;

import com.alesegdia.jsearchgen.config.CombinatorType;
import com.alesegdia.jsearchgen.config.GenerationConfig;
import com.alesegdia.jsearchgen.config.ManagerType;
import com.alesegdia.jsearchgen.config.SolverType;
import com.alesegdia.jsearchgen.fitness.solver.AdaptativeParametrizedMultiObjectiveFitnessCombinator;
import com.alesegdia.jsearchgen.fitness.solver.FloodFillGraphMatrixSolver;
import com.alesegdia.jsearchgen.fitness.solver.IMultiObjectiveFitnessCombinator;
import com.alesegdia.jsearchgen.fitness.solver.MultiObjectiveFitnessSolver;
import com.alesegdia.jsearchgen.fitness.solver.ParametrizedMultiObjectiveFitnessCombinator;
import com.alesegdia.jsearchgen.generatorsolver.BestSearchSolver;
import com.alesegdia.jsearchgen.generatorsolver.IMapGenSolver;
import com.alesegdia.jsearchgen.generatorsolver.RandomSolver;
import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.AInstanceManager;
import com.alesegdia.jsearchgen.model.room.BruteInstanceManager;
import com.alesegdia.jsearchgen.model.room.PrefabModelInstanceManager;
import com.alesegdia.jsearchgen.model.room.PrefabManager;
import com.alesegdia.jsearchgen.util.RNG;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;
import com.alesegdia.jsearchgen.view.GraphGridModelRenderer;

public class DungeonMachine {

	private GenerationConfig config;
	private GraphGridModel ggm;
	private IMapGenSolver mapgenerator;
	GraphGridModelRenderer ggsr;
	AInstanceManager imgr;

	boolean show = false;
	
	public void setShow( boolean show ) {
		this.show = show;
	}
	
	public void Reset(GenerationConfig config, PrefabManager pmgr) throws Exception {
		
		this.config = config;
		RNG.rng = new RNG();
		RNG.rng.setSeed(config.random_seed);

		
		AInstanceManager rm;
		if( config.manager_type == ManagerType.BRUTE_FORCE ) {
			rm = new BruteInstanceManager(pmgr);
		} else {
			rm = new PrefabModelInstanceManager(pmgr);
		}
		
		imgr = rm;
		rm.GenRooms(config);
		IMultiObjectiveFitnessCombinator combinator;
		if( config.combinator_type == CombinatorType.PARAMETRIZED ) {
			combinator = new ParametrizedMultiObjectiveFitnessCombinator(config.fitnesses_params);
		} else /*if( config.combinator_type == CombinatorType.ADAPTATIVE_PARAMETRIZED ) */{
			combinator = new AdaptativeParametrizedMultiObjectiveFitnessCombinator(config.fitnesses_params, config.combinator_attack, config.combinator_decay);
		}
		this.ggm = new GraphGridModel(rm);

		this.ggm.SetupCache(config.cache_type);
		
		this.ggm.SetFitnessSolver(new MultiObjectiveFitnessSolver(combinator));
		
		if( config.solver_type == SolverType.RANDOM ) {
			this.mapgenerator = new RandomSolver();
		} else if( config.solver_type == SolverType.BEST_SEARCH ) {
			this.mapgenerator = new BestSearchSolver();
		}

		
		this.ggsr = new GraphGridModelRenderer(ggm);

	}
	
	public void Run() throws Exception {
		long solve_time = Generate();
		System.out.println("Config used: \n" + this.config);
		System.out.println("SOLVE TIME: " + solve_time/10e8);
		System.out.println("FITNESS TIME: " + GraphGridModel.fitness_time/10e8);
	}
	
	public long Generate() throws Exception {

		int n = imgr.getNumRooms();
		FloodFillGraphMatrixSolver.visited = new UpperMatrix2D<Boolean>(n+1,n+1,false);
		long t1 = System.nanoTime();
		while(!this.ggm.IsComplete()){
			if(!this.mapgenerator.Step(this.ggm)) {
				throw new Exception("ERROR: can't build a complete solution from this partial solution and this list of remaining rooms " + this.ggm);
			}
			if( show ) this.ggsr.Update();
		}
		long t2 = System.nanoTime();

		if( show ) {
			this.ggsr.Update();
			this.ggsr.Show();			
		}
		
		return t2-t1;

	}

}
