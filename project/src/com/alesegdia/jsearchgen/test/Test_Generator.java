package com.alesegdia.jsearchgen.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.extra.GraphGridSimpleProxy;
import com.alesegdia.jsearchgen.model.extra.MapGraphInstance;
import com.alesegdia.jsearchgen.model.extra.MapGraphModel;
import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.Prefabs;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.solver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.solver.RandomSolver;
import com.alesegdia.jsearchgen.util.RNG;
import com.alesegdia.jsearchgen.view.GraphGridSolutionRenderer;

public class Test_Generator {

	public static void main(String[] args) throws Exception {
		
		// setup rng
		RNG.rng = new RNG();
		RNG.rng.setSeed(0xDEADFEED);
		
		// generate map layout
		List<RoomInstance> selected_list = Prefabs.GenerateALot();
		int i = 0;
		for( RoomInstance roominstance : selected_list ) {
			roominstance.id = i;
			i++;
		}

		List<RoomInstance> clone = new LinkedList<RoomInstance>();
		clone.addAll(selected_list);
		GraphGridModel ggm = null;
		try {
			ggm = new GraphGridModel(selected_list);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RandomSolver generator = new RandomSolver(ggm);
		long t1 = System.nanoTime();
		generator.Solve();
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

		// debug stuff
		MapGraphInstance mgi = mgm.CreateCleanInstance();
		mgm.Debug();
		GraphGridSolutionRenderer ggsr = new GraphGridSolutionRenderer((GraphGridModel) ggm);
		GraphGridSolutionRenderer ggsr2 = new GraphGridSolutionRenderer((GraphGridModel) ggm2);

		ggsr.Show();	
		ggsr2.Show();	
		
		//mgi.Debug();

	}
}
