package com.alesegdia.jsearchgen.generator;

import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;

public class RandomGenerator extends MapGenerator {

	public RandomGenerator(GraphGridModel ggm) {
		super(ggm);
	}

	@Override
	protected boolean Step() throws Exception
	{
		List<DoorPairEntry> feasible_door_pairs = this.ggm.ComputeAllFeasibleDPE();
		DoorPairEntry random = this.ggm.GetRandomDPE(feasible_door_pairs);
		if( random == null ) return false;
		else this.ggm.ConnectDPE(random);
		return true;
	}

}
