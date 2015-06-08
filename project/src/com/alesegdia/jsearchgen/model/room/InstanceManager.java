package com.alesegdia.jsearchgen.model.room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.room.Door.Type;
import com.alesegdia.jsearchgen.util.RNG;

public class InstanceManager {

	public List<RoomInstance> instances;
	public List<RoomInstance> modelInstances;
	private PrefabManager prefabMgr;
	private int nextID = 0;

	public InstanceManager(PrefabManager pm) {
		this.prefabMgr = pm;
		instances = new ArrayList<RoomInstance>();
		modelInstances = new ArrayList<RoomInstance>();
		
		for( RoomPrefab prefab : prefabMgr.prefabs ) {
			RoomInstance ri = new RoomInstance(prefab);
			//modelInstances.add(ri);
		}
	}
	
	public Iterator<PotentialDoorEntry> GetPrefabDoorsIterator(int prefab_id) {
		return prefabMgr.prefabs.get(prefab_id).potentialDoors.iterator();
	}
	
	public Iterator<PotentialDoorEntry> GetInstancePrefabDoorsIterator(int prefab_id, int instance_id) {
		return prefabMgr.prefabs.get(instances.get(instance_id).prefab.id).potentialDoors.iterator();
	}
	
	public RoomInstance CreateRoomInstance(RoomPrefab prefab) {
		RoomInstance ri = new RoomInstance(prefab);
		ri.id = nextID;
		nextID++;
		return ri;
	}
	
	public RoomInstance CreateRoomInstance(RoomInstance instance) {
		RoomInstance ri = new RoomInstance(instance);
		ri.id = nextID;
		nextID++;
		return ri;
	}
	
	public List<RoomInstance> GenerateSampleRoomList()
	{
		List<RoomInstance> initial_rooms = new LinkedList<RoomInstance>();
		RoomInstance room1 = CreateRoomInstance(prefabMgr.prefabs.get(0));
		RoomInstance room2 = CreateRoomInstance(prefabMgr.prefabs.get(0));
		RoomInstance room3 = CreateRoomInstance(prefabMgr.prefabs.get(1));
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
	
	public List<RoomInstance> GenerateFixedDoorRoomList()
	{
		List<RoomInstance> initial_rooms = new LinkedList<RoomInstance>();
		RoomInstance room1 = CreateRoomInstance(prefabMgr.prefabs.get(0));
		RoomInstance room2 = CreateRoomInstance(prefabMgr.prefabs.get(1));
		room1.AddDoor(12, 14, Type.HORIZONTAL);
		room2.AddDoor(3, 1, Type.HORIZONTAL);
		room1.name = "room1";
		room2.name = "room2";
		initial_rooms.add(room1);
		initial_rooms.add(room2);
		return initial_rooms;
	}
	
	public List<RoomInstance> GenerateALot()
	{
		List<RoomInstance> ret = new LinkedList<RoomInstance>();
		ret.addAll(GenerateSetWithAllDoors(prefabMgr.prefabs.get(1), 10));
		ret.addAll(GenerateSetWithAllDoors(prefabMgr.prefabs.get(0), 10));
		return ret;
	}
	
	public List<RoomInstance> GenerateSetWithRandomDoors( RoomPrefab prefab, int num_rooms )
	{
		List<RoomInstance> retlist = new LinkedList<RoomInstance>();
		for( int i = 0; i < num_rooms; i++ )
		{
			RoomInstance ri = CreateRoomInstance(prefab);
			ri.GenerateRandomDoors(RNG.rng, 10);
			retlist.add(ri);
		}
		return retlist;
	}
	
	public List<RoomInstance> GenerateSetWithAllDoors( RoomPrefab prefab, int num_rooms )
	{
		List<RoomInstance> retlist = new LinkedList<RoomInstance>();
		for( int i = 0; i < num_rooms; i++ )
		{
			RoomInstance ri = CreateRoomInstance(prefab);
			ri.GenerateAllDoors(RNG.rng);
			retlist.add(ri);
		}
		return retlist;
	}
	
	public List<RoomInstance> GenerateSetWithSameRandomDoors( RoomPrefab prefab, int num_rooms )
	{
		List<RoomInstance> retlist = new LinkedList<RoomInstance>();
		RoomInstance sample = CreateRoomInstance(prefab);
		sample.GenerateRandomDoors(RNG.rng, 10);
		retlist.add(sample);
		for( int i = 0; i < num_rooms - 1; i++ )
		{
			RoomInstance ri = CreateRoomInstance(sample);
			System.out.println(ri.doors.size());
			retlist.add(ri);
		}
		return retlist;
	}



	
}
