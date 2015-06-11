package com.alesegdia.jsearchgen.model.room;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

import com.alesegdia.jsearchgen.util.RNG;

public class BruteInstanceManager extends AInstanceManager {
	
	private List<RoomInstance> remaining_rooms = new LinkedList<RoomInstance>();

	public BruteInstanceManager(PrefabManager pm) {
		super(pm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int NumRemainingRooms() {
		return remaining_rooms.size();
	}

	@Override
	public Iterator<RoomInstance> RemainingInstanceslIterator() {
		return remaining_rooms.iterator();
	}

	@Override
	public RoomInstance PopRandomAvailableRoom() {
		int index = RNG.rng.nextInt(remaining_rooms.size());
		RoomInstance ri = remaining_rooms.get(index);
		remaining_rooms.remove((Object)ri);
		return ri;
	}

	@Override
	public boolean IsThereAvailableInstances(RoomInstance room) {
		return true;
	}

	@Override
	public RoomInstance PopInstanceFromModel(RoomInstance instance) {
		remaining_rooms.remove((Object)instance);
		return instance;
	}

	@Override
	public boolean RemainingRoomsEmpty() {
		return remaining_rooms.isEmpty();
	}

	@Override
	public int GetLastModelIDForInstance(RoomInstance ri) {
		return ri.id;
	}

	@Override
	public void OnRoomInstanceCreated(RoomInstance ri) {
		remaining_rooms.add(ri);
	}

}
