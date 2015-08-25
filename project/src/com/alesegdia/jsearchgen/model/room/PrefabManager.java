package com.alesegdia.jsearchgen.model.room;

import java.util.ArrayList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.TileMap;

public final class PrefabManager {

	public List<RoomPrefab> prefabs;
	private int nextID = 0;
	
	public PrefabManager() {
		prefabs = new ArrayList<RoomPrefab>();
	}
	
	public void AddPrefab(String string) {
		RoomPrefab rp = new RoomPrefab(new TileMap(string));
		rp.id = nextID; nextID++;
		prefabs.add(rp);
	}
	
	public void AddPrefab(TileMap tm) {
		RoomPrefab rp = new RoomPrefab(tm);
		rp.id = nextID; nextID++;
		prefabs.add(rp);
	}

}
