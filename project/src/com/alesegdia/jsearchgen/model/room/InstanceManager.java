package com.alesegdia.jsearchgen.model.room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.room.Door.Type;
import com.alesegdia.jsearchgen.util.RNG;

public class InstanceManager {

	public List<LinkedList<RoomInstance>> remainingInstancesPerPrefab;
	public List<RoomInstance> modelInstances;
	private PrefabManager prefabMgr;
	private int nextID = 0;
	public List<RoomInstance> allRemainingRooms = new LinkedList<RoomInstance>();
	
	public InstanceManager(PrefabManager pm) {
		this.prefabMgr = pm;
		remainingInstancesPerPrefab = new ArrayList<LinkedList<RoomInstance>>();
		modelInstances = new ArrayList<RoomInstance>();
		
		int i = 1;
		for( RoomPrefab prefab : prefabMgr.prefabs ) {
			RoomInstance ri = new RoomInstance(prefab);
			remainingInstancesPerPrefab.add(new LinkedList<RoomInstance>());
			ri.GenerateAllDoors(RNG.rng);
			ri.id = i * 1000;
			i++;
			modelInstances.add(ri);
		}
	}
	
	public RoomInstance CreateRoomInstance(RoomPrefab prefab) {
		RoomInstance ri = new RoomInstance(prefab);
		ri.id = nextID;
		nextID++;
		this.remainingInstancesPerPrefab.get(prefab.id).add(ri);
		allRemainingRooms.add(ri);
		return ri;
	}
	
	public RoomInstance CreateRoomInstance(RoomInstance instance) {
		RoomInstance ri = new RoomInstance(instance);
		ri.id = nextID;
		nextID++;
		this.remainingInstancesPerPrefab.get(ri.prefab.id).add(ri);
		return ri;
	}
	
	public void GenerateALot(int num_instances_per_prefab[])
	{
		int i = 0;
		for( RoomPrefab prefab : prefabMgr.prefabs ) {
			GenerateSetWithAllDoors(prefab, num_instances_per_prefab[i]);
			i++;
		}
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

	public RoomInstance PopInstanceOf(RoomPrefab prefab) {
		RoomInstance ri = this.remainingInstancesPerPrefab.get(prefab.id).pollLast();
		this.allRemainingRooms.remove((Object)ri);
		return ri;
	}

	public RoomInstance PopRandomRoom() {
		int room_index = RNG.rng.nextInt(this.allRemainingRooms.size());
		RoomInstance ri = allRemainingRooms.get(room_index);
		allRemainingRooms.remove((Object)ri);
		this.remainingInstancesPerPrefab.get(ri.prefab.id).remove((Object)ri);
		return ri;
	}

	public int NumRooms() {
		return this.allRemainingRooms.size();
	}

	public Iterator<RoomInstance> PrefabModelIterator() {
		return this.modelInstances.iterator();
	}

	public int GetLastIDForPrefab(RoomPrefab prefab) {
		return this.remainingInstancesPerPrefab.get(prefab.id).getLast().id;
	}

}
