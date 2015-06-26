package com.alesegdia.jsearchgen.model.room;

import java.util.Iterator;
import java.util.List;

public interface IPrefabSelector {
	
	Iterator<RoomInstance> GetPrefabIterator(List<RoomInstance> modelInstances);

}
