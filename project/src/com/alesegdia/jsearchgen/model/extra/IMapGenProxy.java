package com.alesegdia.jsearchgen.model.extra;

import java.util.List;

import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.RoomInstance;

public interface IMapGenProxy {

	public List<DoorPairEntry> GetDoorPairList();
	public List<RoomInstance> GetRooms();

}
