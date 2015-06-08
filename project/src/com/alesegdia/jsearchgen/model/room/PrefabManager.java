package com.alesegdia.jsearchgen.model.room;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.TileMap;
import com.alesegdia.jsearchgen.model.room.Door.Type;
import com.alesegdia.jsearchgen.util.RNG;

public final class PrefabManager {

	public List<RoomPrefab> prefabs;
	private int nextID = 0;
	
	public PrefabManager() {
		prefabs = new ArrayList<RoomPrefab>();
		AddPrefab("rooms/room0.json");
		AddPrefab("rooms/room1.json");
	}
	
	private void AddPrefab(String string) {
		RoomPrefab rp = new RoomPrefab(new TileMap(string));
		rp.id = nextID; nextID++;
		prefabs.add(rp);
	}

}
