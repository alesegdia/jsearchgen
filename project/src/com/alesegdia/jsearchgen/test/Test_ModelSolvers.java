package com.alesegdia.jsearchgen.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.generatorsolver.RandomSolver;
import com.alesegdia.jsearchgen.matrixsolver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.PrefabManager;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.model.room.PrefabModelInstanceManager;
import com.alesegdia.jsearchgen.util.RNG;
import com.alesegdia.jsearchgen.view.GraphGridSolutionRenderer;

public class Test_ModelSolvers {

	public static void main(String[] args) throws Exception {
		/*
		// setup rng
		RNG.rng = new RNG();
		RNG.rng.setSeed(0xDEADFEED);

		PrefabManager pmgr = new PrefabManager();
		InstanceManager rm = new InstanceManager(pmgr);

		// generate map layout
		int num_prefabs[] = { 10, 10 };
		List<RoomInstance> selected_list = rm.GenerateALot(num_prefabs);
		List<RoomInstance> clone = new LinkedList<RoomInstance>();
		clone.addAll(selected_list);
		
		GraphGridModel ggm = null;
		ggm = new GraphGridModel(selected_list);
		RandomGenerator generator = new RandomGenerator(ggm);
		long t1 = System.nanoTime();
		generator.Generate();
		long t2 = System.nanoTime();
		long solve_time = t2 - t1;
		System.out.println("time to solve: " + solve_time);
		
		ggm.graph_matrix.Debug();
		
		GraphGridModel ggm2 = null;
		try {
			ggm2 = new GraphGridModel(clone, false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		t1 = System.nanoTime();
		ggm2.BuildFromPath(ggm.CloneBuildData());
		t2 = System.nanoTime();
		long rebuild_time = t2 - t1;
		System.out.println("time to rebuild: " + rebuild_time);
		System.out.println("rebuild times faster than solve: " + (solve_time / rebuild_time) );
		
		// compute first and last rooms
		MapGraphModel mgm = new MapGraphModel(new GraphGridSimpleProxy(ggm));
		FloydWarshallSolver fwsolver = new FloydWarshallSolver();
		fwsolver.Solve(mgm.CloneSCM());
		mgm.CloneSCM().Debug();
		
		fwsolver = new FloydWarshallSolver();
		fwsolver.Solve(ggm.graph_matrix.Clone());
		ggm.graph_matrix.Debug();


		// debug stuff
		MapGraphInstance mgi = mgm.CreateCleanInstance();
		//mgm.Debug();
		GraphGridSolutionRenderer ggsr = new GraphGridSolutionRenderer((GraphGridModel) ggm);
		GraphGridSolutionRenderer ggsr2 = new GraphGridSolutionRenderer((GraphGridModel) ggm2);

		ggsr.Show();	
		ggsr2.Show();	
		
		//mgi.Debug();
		*/
	}
}
