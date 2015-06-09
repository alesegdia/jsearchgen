package com.alesegdia.jsearchgen.solver;

import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.RoomInstance;

public class SearchGenerator extends MapGenerator {

	public SearchGenerator(GraphGridModel ggm) {
		super(ggm);
	}

	@Override
	protected boolean Step()
	{
		List<DoorPairEntry> feasible_door_pairs = this.ggm.ComputeAllFeasibleDPE();
		DoorPairEntry random = this.ggm.GetBestDPE(feasible_door_pairs);
		this.ggm.ConnectDPE(random);
		return random != null;
	}


}
