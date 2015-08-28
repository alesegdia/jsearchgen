package com.alesegdia.jsearchgen.model.room;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.config.DoorGenType;
import com.alesegdia.jsearchgen.config.GenerationConfig;
import com.alesegdia.jsearchgen.util.RNG;

public abstract class AInstanceManager {

	private int nextID = 0;
	protected PrefabManager prefabMgr;

	public AInstanceManager(PrefabManager pm) {
		this.prefabMgr = pm;
	}

	protected void AssignNextID( RoomInstance ri ) {
		ri.id = this.nextID;
		this.nextID++;
	}
	
	public abstract void OnRoomInstanceCreated(RoomInstance ri);
	public abstract int NumRemainingRooms();
	public abstract Iterator<RoomInstance> RemainingInstanceslIterator();
	public abstract RoomInstance PopRandomAvailableRoom();
	public abstract boolean IsThereAvailableInstances(RoomInstance room);
	public abstract RoomInstance PopInstanceFromModel(RoomInstance instance);
	public abstract boolean RemainingRoomsEmpty();
	public abstract int GetLastModelIDForInstance(RoomInstance ri);

	public void GenRooms(GenerationConfig gc) {
		int i = 0;
		int num_instances_per_prefab[] = gc.num_instances_per_prefab;
		for( RoomPrefab prefab : prefabMgr.prefabs ) {
			int n = num_instances_per_prefab[i];
			if( gc.doorgen_type == DoorGenType.RANDOM && gc.cloned_rooms ) {
				this.GenerateSetWithSameRandomDoors(prefab, n, gc.doorgen_random_doors);
			} else if ( gc.doorgen_type == DoorGenType.RANDOM && !gc.cloned_rooms ) {
				this.GenerateSetWithRandomDoors(prefab, n, gc.doorgen_random_doors);
			} else if ( gc.doorgen_type == DoorGenType.ALL ) {
				this.GenerateSetWithAllDoors(prefab, n);
			} else if ( gc.doorgen_type == DoorGenType.DIVISOR ) {
				this.GenerateSetWithDivisorDoors(prefab, n, gc.doorgen_divisor );
			}
			i++;
		}
	}
	
	private List<RoomInstance> GenerateSetWithDivisorDoors(RoomPrefab prefab, int num_rooms, float divisor_k) {
		List<RoomInstance> retlist = new LinkedList<RoomInstance>();
		for( int i = 0; i < num_rooms; i++ )
		{
			RoomInstance ri = CreateRoomInstance(prefab);
			ri.GenerateDivisorDoors(RNG.rng, divisor_k);
			retlist.add(ri);
		}
		return retlist;
	}

	public void GenerateWithAllDoors(int num_instances_per_prefab[])
	{
		int i = 0;
		for( RoomPrefab prefab : prefabMgr.prefabs ) {
			GenerateSetWithAllDoors(prefab, num_instances_per_prefab[i]);
			i++;
		}
	}
	
	public List<RoomInstance> GenerateSetWithRandomDoors( RoomPrefab prefab, int num_rooms, float num_doors_k )
	{
		List<RoomInstance> retlist = new LinkedList<RoomInstance>();
		for( int i = 0; i < num_rooms; i++ )
		{
			RoomInstance ri = CreateRoomInstance(prefab);
			ri.GenerateRandomDoors(RNG.rng, num_doors_k);
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
	
	public List<RoomInstance> GenerateSetWithSameRandomDoors( RoomPrefab prefab, int num_rooms, float num_doors_k )
	{
		List<RoomInstance> retlist = new LinkedList<RoomInstance>();
		RoomInstance sample = CreateRoomInstance(prefab);
		sample.GenerateRandomDoors(RNG.rng, num_doors_k);
		retlist.add(sample);
		for( int i = 0; i < num_rooms - 1; i++ )
		{
			RoomInstance ri = CreateRoomInstance(sample);
			//System.out.println(ri.doors.size());
			retlist.add(ri);
		}
		return retlist;
	}
	
	protected RoomInstance CreateRoomInstance(RoomPrefab prefab) {
		RoomInstance ri = new RoomInstance(prefab);
		AssignNextID(ri);
		OnRoomInstanceCreated(ri);
		return ri;
	}
	
	protected RoomInstance CreateRoomInstance(RoomInstance instance) {
		RoomInstance ri = new RoomInstance(instance);
		AssignNextID(ri);
		OnRoomInstanceCreated(ri);
		return ri;
	}

	public abstract int getNumRooms();

	


}
