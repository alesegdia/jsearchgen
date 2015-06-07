package com.alesegdia.jsearchgen.test;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.Prefabs;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.solver.RandomGenerator;
import com.alesegdia.jsearchgen.solver.SearchGenerator;
import com.alesegdia.jsearchgen.util.RNG;
import com.alesegdia.jsearchgen.view.GraphGridSolutionRenderer;

public class Test_Generators {

	public static void main(String[] args) throws Exception {
		
		RNG.rng = new RNG();
		RNG.rng.setSeed(0xDEADFEED);
		
		// generate and clone room list
		List<RoomInstance> selected_list = Prefabs.GenerateALot();
		List<RoomInstance> clone = CloneListRooms(selected_list);
		
		// create ggm and generators
		GraphGridModel ggm = null;
		GraphGridModel ggm2 = null;
		ggm = new GraphGridModel(selected_list);
		ggm2 = new GraphGridModel(clone);
		RandomGenerator random_generator = new RandomGenerator(ggm);
		SearchGenerator search_generator = new SearchGenerator(ggm2);
		
		// generate random
		long t1 = System.nanoTime();
		random_generator.Generate();
		long t2 = System.nanoTime();
		long solve_time = t2 - t1;
		System.out.println("time to solve RANDOM: " + solve_time);
		
		// generate search
		t1 = System.nanoTime();
		search_generator.Generate();
		t2 = System.nanoTime();
		solve_time = t2 - t1;
		System.out.println("time to solve SEARCH: " + solve_time);

		GraphGridSolutionRenderer ggsr = new GraphGridSolutionRenderer((GraphGridModel) ggm);
		GraphGridSolutionRenderer ggsr2 = new GraphGridSolutionRenderer((GraphGridModel) ggm2);
		ggsr.Show();	
		ggsr2.Show();	


	}

	private static List<RoomInstance> CloneListRooms(List<RoomInstance> selected_list) {
		RoomInstance.nextID = 0;
		List<RoomInstance> l = new LinkedList<RoomInstance>();
		for( RoomInstance ri : selected_list ) {
			l.add(new RoomInstance(ri));
		}
		return l;
	}
	
}
