package com.alesegdia.jsearchgen.model.room;

import java.util.Iterator;
import java.util.List;

public class DummyPrefabSelector implements IPrefabSelector {

	@Override
	public Iterator<RoomInstance> GetPrefabIterator(List<RoomInstance> modelInstances) {
		return modelInstances.iterator();
	}

}
