package com.alesegdia.jsearchgen.fitness;

import com.alesegdia.jsearchgen.model.room.DoorPairEntry;

public class DpeDummyCache implements IDpeFitnessCache {

	@Override
	public DoorPairEntry Precached(DoorPairEntry other) {
		return null;
	}

	@Override
	public void Cache(DoorPairEntry dpe) {
		return;
	}

}
