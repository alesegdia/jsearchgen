package com.alesegdia.jsearchgen.solver;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.util.RNG;

public class RandomGenerator extends MapGenerator {

	public RandomGenerator(GraphGridModel ggm) {
		super(ggm);
	}

	@Override
	protected boolean Step() throws Exception
	{
		List<DoorPairEntry> feasible_door_pairs = this.ggm.ComputeAllFeasibleDPE();
		DoorPairEntry random = this.ggm.GetRandomDPE(feasible_door_pairs);
		this.ggm.ConnectDPE(random);
		return random != null;
	}

}
