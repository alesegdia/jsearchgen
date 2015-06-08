package com.alesegdia.jsearchgen.model.room;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.TileMap;
import com.alesegdia.jsearchgen.model.room.Door.Type;
import com.alesegdia.jsearchgen.util.RNG;

public final class Prefabs {

	public static List<RoomPrefab> prefabs;
	private static int nextID = 0;
	
	public static void Initialize() {
		prefabs = new ArrayList<RoomPrefab>();
		AddPrefab("rooms/room0.json");
		AddPrefab("rooms/room1.json");
	}
	
	private static void AddPrefab(String string) {
		RoomPrefab rp = new RoomPrefab(new TileMap(string));
		rp.id = nextID; nextID++;
		prefabs.add(rp);
	}

	public static List<RoomInstance> GenerateSampleRoomList()
	{
		List<RoomInstance> initial_rooms = new LinkedList<RoomInstance>();
		RoomInstance room1 = new RoomInstance(Prefabs.prefabs.get(0));
		RoomInstance room2 = new RoomInstance(Prefabs.prefabs.get(0));
		RoomInstance room3 = new RoomInstance(Prefabs.prefabs.get(1));
		room1.GenerateRandomDoors(RNG.rng, 10);
		room2.GenerateRandomDoors(RNG.rng, 10);
		room3.GenerateRandomDoors(RNG.rng, 10);
		room1.name = "room1";
		room2.name = "room2";
		room3.name = "room3";
		initial_rooms.add(room1);
		initial_rooms.add(room2);
		initial_rooms.add(room3);
		return initial_rooms;
	}
	
	public static List<RoomInstance> GenerateFixedDoorRoomList()
	{
		List<RoomInstance> initial_rooms = new LinkedList<RoomInstance>();
		RoomInstance room1 = new RoomInstance(Prefabs.prefabs.get(0));
		RoomInstance room2 = new RoomInstance(Prefabs.prefabs.get(1));
		room1.AddDoor(12, 14, Type.HORIZONTAL);
		room2.AddDoor(3, 1, Type.HORIZONTAL);
		room1.name = "room1";
		room2.name = "room2";
		initial_rooms.add(room1);
		initial_rooms.add(room2);
		return initial_rooms;
	}
	
	public static List<RoomInstance> GenerateALot()
	{
		List<RoomInstance> ret = new LinkedList<RoomInstance>();
		ret.addAll(GenerateSetWithAllDoors(Prefabs.prefabs.get(1), 10));
		ret.addAll(GenerateSetWithAllDoors(Prefabs.prefabs.get(0), 10));
		return ret;
	}
	
	public static List<RoomInstance> GenerateSetWithRandomDoors( RoomPrefab prefab, int num_rooms )
	{
		List<RoomInstance> retlist = new LinkedList<RoomInstance>();
		for( int i = 0; i < num_rooms; i++ )
		{
			RoomInstance ri = new RoomInstance(prefab);
			ri.GenerateRandomDoors(RNG.rng, 10);
			retlist.add(ri);
		}
		return retlist;
	}
	
	public static List<RoomInstance> GenerateSetWithAllDoors( RoomPrefab prefab, int num_rooms )
	{
		List<RoomInstance> retlist = new LinkedList<RoomInstance>();
		for( int i = 0; i < num_rooms; i++ )
		{
			RoomInstance ri = new RoomInstance(prefab);
			ri.GenerateAllDoors(RNG.rng);
			retlist.add(ri);
		}
		return retlist;
	}
	
	public static List<RoomInstance> GenerateSetWithSameRandomDoors( RoomPrefab prefab, int num_rooms )
	{
		List<RoomInstance> retlist = new LinkedList<RoomInstance>();
		RoomInstance sample = new RoomInstance(prefab);
		sample.GenerateRandomDoors(RNG.rng, 10);
		retlist.add(sample);
		for( int i = 0; i < num_rooms - 1; i++ )
		{
			RoomInstance ri = new RoomInstance(sample);
			System.out.println(ri.doors.size());
			retlist.add(ri);
		}
		return retlist;
	}


}
