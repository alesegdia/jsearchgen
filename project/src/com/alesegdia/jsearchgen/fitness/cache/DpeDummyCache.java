package com.alesegdia.jsearchgen.fitness.cache;

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

	@Override
	public void NotifyStep() {
		// TODO Auto-generated method stub
		
	}

}
