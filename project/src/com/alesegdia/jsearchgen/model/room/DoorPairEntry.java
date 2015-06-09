package com.alesegdia.jsearchgen.model.room;

import com.alesegdia.jsearchgen.util.Vec2;

public class DoorPairEntry {
	public Door other_door;
	public Vec2 relativeToSolutionMap;
	public Door this_door;
	public float fitness;
	
	public boolean Equals(DoorPairEntry other) {
		return this.other_door.ri_owner.prefab == other.other_door.ri_owner.prefab &&
				this.this_door.ri_owner.prefab == other.this_door.ri_owner.prefab &&
				this.relativeToSolutionMap.x == other.relativeToSolutionMap.x &&
				this.relativeToSolutionMap.y == other.relativeToSolutionMap.y;
	}
	
}
