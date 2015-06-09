package com.alesegdia.jsearchgen.generator;

import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.RoomInstance;

public class BestSearchGenerator extends MapGenerator {

	public BestSearchGenerator(GraphGridModel ggm) {
		super(ggm);
	}

	@Override
	protected boolean Step()
	{
		List<DoorPairEntry> feasible_door_pairs = this.ggm.ComputeAllFeasibleDPE();
		System.out.println(feasible_door_pairs.size());
		DoorPairEntry random = this.ggm.GetBestDPE(feasible_door_pairs);
		int r1 = random.other_door.ri_owner.id;
		int r2 = random.this_door.ri_owner.id;
		this.ggm.ConnectDPE(random);
		return random != null;
	}


}
