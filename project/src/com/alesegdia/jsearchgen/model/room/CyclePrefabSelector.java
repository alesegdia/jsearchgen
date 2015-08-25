package com.alesegdia.jsearchgen.model.room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CyclePrefabSelector implements IPrefabSelector {

	int nextSelectedPrefab = 0;
	List<RoomInstance> list;
	
	@Override
	public Iterator<RoomInstance> GetPrefabIterator(List<RoomInstance> modelInstances) {
		list = new ArrayList<RoomInstance>();
		list.add(modelInstances.get(nextSelectedPrefab));
		nextSelectedPrefab = (nextSelectedPrefab + 1) % modelInstances.size();
		return list.iterator();
	}

}
