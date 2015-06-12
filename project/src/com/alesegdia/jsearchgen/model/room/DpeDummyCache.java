package com.alesegdia.jsearchgen.model.room;

public class DpeDummyCache implements IDpeCache {

	@Override
	public DoorPairEntry Precached(DoorPairEntry other) {
		return null;
	}

	@Override
	public void Cache(DoorPairEntry dpe) {
		return;
	}

}
