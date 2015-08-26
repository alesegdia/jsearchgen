package com.alesegdia.jsearchgen.generatorsolver;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.util.RNG;

public class BestSearchSolver implements IMapGenSolver {

	@Override
	public boolean Step(GraphGridModel ggm)
	{
		List<DoorPairEntry> feasible_door_pairs = ggm.ComputeAllFeasibleDPE();
		shuffle(feasible_door_pairs);
		DoorPairEntry random = ggm.GetBestDPE(feasible_door_pairs);
		ggm.ConnectDPE(random);
		return random != null;
	}

	private void shuffle(List<DoorPairEntry> l) {
		for( int i = 0; i < l.size(); i++ ) {
			int k = RNG.rng.nextInt(l.size());
			DoorPairEntry tmp = l.get(k);
			l.set(k, l.get(i));
			l.set(i, tmp);
		}
	}


}
