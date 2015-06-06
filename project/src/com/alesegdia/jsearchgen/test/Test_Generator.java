package com.alesegdia.jsearchgen.test;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.algo.mapgen.GraphGridModel;
import com.alesegdia.jsearchgen.algo.mapgen.RandomSolver;
import com.alesegdia.jsearchgen.algo.mapgen.proxy.GraphGridSimpleProxy;
import com.alesegdia.jsearchgen.algo.roomselect.FloydWarshallSolver;
import com.alesegdia.jsearchgen.algo.roomselect.MapGraphModel;
import com.alesegdia.jsearchgen.algo.roomselect.MapGraphInstance;
import com.alesegdia.jsearchgen.core.data.Prefabs;
import com.alesegdia.jsearchgen.core.data.RoomInstance;
import com.alesegdia.jsearchgen.core.map.render.GraphGridSolutionRenderer;
import com.alesegdia.jsearchgen.core.util.RNG;

public class Test_Generator {

	public static void main(String[] args) {
		
		// setup rng
		RNG.rng = new RNG();
		RNG.rng.setSeed(0xDEADFEED);
		
		// generate map layout
		List<RoomInstance> selected_list = Prefabs.GenerateALot();
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
