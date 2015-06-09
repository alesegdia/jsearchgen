package com.alesegdia.jsearchgen.generatorsolver;

import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;

public class RandomSolver implements IMapGenSolver {

	@Override
	public boolean Step(GraphGridModel ggm) throws Exception
	{
		List<DoorPairEntry> feasible_door_pairs = ggm.ComputeAllFeasibleDPE();
		DoorPairEntry random = ggm.GetRandomDPE(feasible_door_pairs);
		if( random == null ) return false;
		else ggm.ConnectDPE(random);
		return true;
	}

}
