package com.alesegdia.jsearchgen.model.extra;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.Door;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.util.Vec2;

public class GraphGridAllProxy implements IMapGenProxy {

	GraphGridModel ggs;

	public GraphGridAllProxy( GraphGridModel ggs ) {
		this.ggs = ggs;
	}

	@Override
	public List<DoorPairEntry> GetDoorPairList() {
		List<DoorPairEntry> feasible_dpes = new LinkedList<DoorPairEntry>();
		List<RoomInstance> rooms = GetRooms();
		for( int i = 0; i < rooms.size(); i++ ) {
			RoomInstance outer = rooms.get(i);
			for( int j = i+1; j < rooms.size(); j++ ) {
				RoomInstance inner = rooms.get(j);
				feasible_dpes.addAll(HandleRoomVsRoom(inner, outer));
			}
		}
		return feasible_dpes;
	}

	private List<DoorPairEntry> HandleRoomVsRoom(RoomInstance inner, RoomInstance outer) {
		List<DoorPairEntry> dpes = new LinkedList<DoorPairEntry>();
		for( Door inner_door : inner.doors ) {
			for( Door outer_door : outer.doors ) {
				int dx, dy;
				Vec2 inner_global = inner_door.GetGlobalPosition();
				Vec2 outer_global = outer_door.GetGlobalPosition();
				dx = Math.abs(inner_global.x - outer_global.x);
				dy = Math.abs(inner_global.y - outer_global.y);
				if( dx + dy == 1 ) {
					// creamos el par y lo metemos en la lista a devolver
					Vec2 map_offset = this.ggs.IsPossibleDoorCombination( inner_door, outer_door );
					if( map_offset != null ) {
						DoorPairEntry dpe = new DoorPairEntry();
						dpe.relativeToSolutionMap = map_offset;
						dpe.other_door = inner_door;
						dpe.this_door = outer_door;
						dpes.add(dpe);
					}
				}
			}
		}
		return dpes;
	}

	@Override
	public List<RoomInstance> GetRooms() {
		return ggs.GetRooms();
	}

}
