package com.alesegdia.jsearchgen.algo.mapgen;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.core.data.DoorPairEntry;
import com.alesegdia.jsearchgen.core.data.RoomInstance;

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
