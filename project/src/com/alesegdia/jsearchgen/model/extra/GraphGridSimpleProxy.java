package com.alesegdia.jsearchgen.model.extra;

import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.RoomInstance;

public class GraphGridSimpleProxy implements IMapGenProxy {

	GraphGridModel ggs;

	public GraphGridSimpleProxy( GraphGridModel ggs ) {
		this.ggs = ggs;
	}

	@Override
	public List<DoorPairEntry> GetDoorPairList() {
		return ggs.added_dpes;
	}

	@Override
	public List<RoomInstance> GetRooms() {
		return ggs.added_rooms;
	}

}
