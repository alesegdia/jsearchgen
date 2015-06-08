package com.alesegdia.jsearchgen.model.room;

import com.alesegdia.jsearchgen.util.Vec2;

public class PotentialDoorEntry {
	public RoomPrefab prefab;
	public Vec2 localPosition = new Vec2(0,0);
	public Door.Type type;
	public String toString()
	{
		return "<" + localPosition.x + ", " + localPosition.y + " type " + (type==Door.Type.HORIZONTAL?"HOR":"VER") + ">";
	}
}