package com.alesegdia.jsearchgen.model.map;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.RoomInstance;

public class GraphGridBuildData {

	public GraphGridBuildData(GraphGridBuildData ggbd) {
		this();
		this.initial_room = ggbd.initial_room;
		this.build_path.addAll(ggbd.build_path);
	}
	
	public GraphGridBuildData() {
		build_path = new LinkedList<DoorPairEntry>();
	}
	
	public List<DoorPairEntry> build_path;
	public RoomInstance initial_room;

}
