package com.alesegdia.jsearchgen.model.room;

import com.alesegdia.jsearchgen.util.Vec2;

public class DoorPairEntry {
	public Door other_door;
	public Vec2 relativeToSolutionMap;
	public Door this_door;
	public float fitness;
	
	@Override
	public String toString() {
		return "\t<" + other_door.ri_owner.id + " " + other_door.GetGlobalPosition() + ", " + this_door.ri_owner.id + " " + this_door.GetGlobalPosition() + ">"; 
	}
}
