package com.alesegdia.jsearchgen.generatorsolver;

import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;

public class BestSearchSolver implements IMapGenSolver {

	@Override
	public boolean Step(GraphGridModel ggm)
	{
		List<DoorPairEntry> feasible_door_pairs = ggm.ComputeAllFeasibleDPE();
		System.out.println(feasible_door_pairs.size());
		DoorPairEntry random = ggm.GetBestDPE(feasible_door_pairs);
		ggm.ConnectDPE(random);
		return random != null;
	}


}
