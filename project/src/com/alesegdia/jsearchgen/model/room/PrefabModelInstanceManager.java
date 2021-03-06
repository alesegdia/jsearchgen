package com.alesegdia.jsearchgen.model.room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.util.RNG;

public class PrefabModelInstanceManager extends AInstanceManager {

	public List<LinkedList<RoomInstance>> remainingInstancesPerPrefab;
	public List<RoomInstance> modelInstances;
	public List<RoomInstance> allRemainingRooms = new LinkedList<RoomInstance>();
	
	public PrefabModelInstanceManager(PrefabManager pm) {
		super(pm);
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
	
	@Override
	public RoomInstance PopInstanceFromModel(RoomInstance instance) {
		RoomInstance ri = this.remainingInstancesPerPrefab.get(instance.prefab.id).pollLast();
		this.allRemainingRooms.remove((Object)ri);
		return ri;
	}

	@Override
	public RoomInstance PopRandomAvailableRoom() {
		int room_index = RNG.rng.nextInt(this.allRemainingRooms.size());
		RoomInstance ri = allRemainingRooms.get(room_index);
		allRemainingRooms.remove((Object)ri);
		this.remainingInstancesPerPrefab.get(ri.prefab.id).remove((Object)ri);
		return ri;
	}

	@Override
	public int NumRemainingRooms() {
		return this.allRemainingRooms.size();
	}

	IPrefabSelector mainPrefabSelector = new CyclePrefabSelector();
	IPrefabSelector defaultDummyPrefabSelector = new DummyPrefabSelector();
	
	@Override
	public Iterator<RoomInstance> RemainingInstanceslIterator() {
		if( EnoughRoomsPerPrefab() ) {
			return this.mainPrefabSelector.GetPrefabIterator(this.modelInstances);
		} else {
			return this.defaultDummyPrefabSelector.GetPrefabIterator(this.modelInstances);
		}
	}

	private boolean EnoughRoomsPerPrefab() {
		for( List<RoomInstance> lri : this.remainingInstancesPerPrefab ) {
			if( lri.size() == 0 ) return false;
		}
		return true;
	}

	@Override
	public int GetLastModelIDForInstance(RoomInstance ri) {
		return this.remainingInstancesPerPrefab.get(ri.prefab.id).getLast().id;
	}

	@Override
	public boolean IsThereAvailableInstances(RoomInstance room) {
		return this.remainingInstancesPerPrefab.get(room.prefab.id).size() > 0;
	}
	
	@Override
	public boolean RemainingRoomsEmpty() {
		return this.allRemainingRooms.size() == 0;
	}

	@Override
	public void OnRoomInstanceCreated(RoomInstance ri) {
		this.remainingInstancesPerPrefab.get(ri.prefab.id).add(ri);
		allRemainingRooms.add(ri);
	}

	@Override
	public int getNumRooms() {
		return allRemainingRooms.size();
	}

	
}
