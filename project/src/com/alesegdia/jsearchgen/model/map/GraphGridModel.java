package com.alesegdia.jsearchgen.model.map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.room.Door;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.solver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.util.RNG;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;
import com.alesegdia.jsearchgen.util.Vec2;
import com.alesegdia.jsearchgen.view.TileMapRenderer;

/**
 * Class that will represent a solution as a list of rooms inside,
 * and a TileMap representing
 *
 */
public class GraphGridModel {
	
	public enum State {
		UNINITIALISED,		// antes de introducir siquiera una habitación
		UNFINISHED,		// aún no se han introducido todas las habitaciones
		FINISHED 			// ya se introdujeron todas las habitaciones
	}

	private static final int SOLUTION_WIDTH = 100;
	private static final int SOLUTION_HEIGHT = 200;
	/**
	 * TileMap representing the entire map as a Matrix2D, with proper rooms placed
	 */
	public TileMap tilemap;
	public List<Door> opened = new LinkedList<Door>();
	public List<Door> closed = new LinkedList<Door>();
	public List<RoomInstance> remaining_rooms = new LinkedList<RoomInstance>();
	public List<RoomInstance> added_rooms = new LinkedList<RoomInstance>();
	public List<DoorPairEntry> added_dpes = new LinkedList<DoorPairEntry>();
	public List<DoorPairEntry> all_feasible_dpes = new LinkedList<DoorPairEntry>();
	public RoomInstance initialRoom;
	public State state = State.UNINITIALISED;
	
	public UpperMatrix2D<Float> graph_matrix;

	public GraphGridModel( int rows, int cols )
	{
		tilemap = new TileMap(rows, cols, TileType.FREE);
	}

	GraphGridModel( GraphGridModel other )
	{
		tilemap = new TileMap(other.tilemap);
	}

	static int a = 0;
	public GraphGridModel(List<RoomInstance> remaining_rooms, int width, int height, boolean insert_first) throws Exception {
		this(height, width);
		this.remaining_rooms = remaining_rooms;
		if( insert_first ) {
			try {
				// insertamos la primera elegida de forma aleatoria
				int room_index = RNG.rng.nextInt(0, remaining_rooms.size()-1);
				RoomInstance selected = remaining_rooms.get(room_index);
				InsertFirstRoom(selected);
			} catch(IndexOutOfBoundsException e) {
				System.err.println("remaining_rooms list empty!");
			}
		}
	}

	private void InsertFirstRoom(RoomInstance selected) throws Exception {
		graph_matrix = new UpperMatrix2D<Float>(this.remaining_rooms.size(), this.remaining_rooms.size(), Float.MAX_VALUE);
		selected.globalPosition.Set(30, 10);
		AttachRoom(selected, 30, 10);
		this.ggbd.initial_room = selected;
	}

	public GraphGridModel(List<RoomInstance> remaining_rooms, boolean insert_first) throws Exception {
		this(remaining_rooms, SOLUTION_WIDTH, SOLUTION_HEIGHT, insert_first);
	}

	public GraphGridModel(List<RoomInstance> remaining_rooms) throws Exception {
		this(remaining_rooms, SOLUTION_WIDTH, SOLUTION_HEIGHT, true);
	}

	public void AttachRoom(RoomInstance room, int r, int c)
	{
		remaining_rooms.remove(room);
		//System.out.println("Attach at r:" + r + ", c:" + c);
		this.tilemap.Apply(room.prefab.map, r, c);
		room.globalPosition.x = c;
		room.globalPosition.y = r;
		opened.addAll(room.doors);
		added_rooms.add(room);
	}

	public void Render() {
		tilemap.Render();
		System.out.println("Opened doors: " + this.opened);
		System.out.println("Closed doors: " + this.closed);
	}

	private GraphGridBuildData ggbd = new GraphGridBuildData();
	
	public GraphGridBuildData CloneBuildData() {
		return new GraphGridBuildData(this.ggbd);
	}
	
	public List<DoorPairEntry> CloneBuildPath() {
		List<DoorPairEntry> build_path = new LinkedList<DoorPairEntry>();
		build_path.addAll(ggbd.build_path);
		return build_path;
	}
	
	public boolean InsertRandomFeasibleRoom() {
		// precompute if needed 
		// extract feasible doors for each room
		List<DoorPairEntry> feasible_door_pairs = new LinkedList<DoorPairEntry>();
		for( Iterator<RoomInstance> it = remaining_rooms.iterator(); it.hasNext(); )
		{
			RoomInstance room = it.next();
			List<DoorPairEntry> l = this.GetFeasibleDoorsForRoom(room);
			feasible_door_pairs.addAll(l);
			all_feasible_dpes.addAll(l);
		}

		if( !feasible_door_pairs.isEmpty() )
		{
			int door_index = RNG.rng.nextInt(feasible_door_pairs.size());
			DoorPairEntry fde = feasible_door_pairs.get(door_index);
			ConnectDPE(fde);
			return true;
		}
		else return false;
	}

	public void ConnectDPE(DoorPairEntry dpe) {
		this.AttachRoom(dpe.other_door.ri_owner, dpe.relativeToSolutionMap.x, dpe.relativeToSolutionMap.y);
		this.Connect(dpe.other_door, dpe.this_door);
		this.closed.add(dpe.other_door);
		this.closed.add(dpe.this_door);
		this.opened.remove((Object)dpe.other_door);
		this.opened.remove((Object)dpe.this_door);
		this.remaining_rooms.remove(dpe.other_door.ri_owner);
		this.added_dpes.add(dpe);
		if( ++a < 10 ) this.ggbd.build_path.add(dpe);
	}

	public void BuildFromPath(GraphGridBuildData ggbd) throws Exception {
		this.InsertFirstRoom(ggbd.initial_room);
		for( DoorPairEntry dpe : ggbd.build_path ) {
			ConnectDPE(dpe);
		}
	}

	private void Connect(Door other_door, Door this_door) {
		other_door.connected_room 	= this_door.ri_owner;
		this_door.connected_room 	= other_door.ri_owner;
		other_door.connected_door = this_door;
		this_door.connected_door = other_door;
		graph_matrix.SetUpper(other_door.connected_room.id, this_door.connected_room.id, other_door.connected_room.globalPosition.distance(this_door.connected_room.globalPosition));
	}

	/**
	 * Check all other room doors against all opened doors in this solution.
	 */
	private List<DoorPairEntry> GetFeasibleDoorsForRoom(RoomInstance room) {
		List<DoorPairEntry> feasible_entries = new LinkedList<DoorPairEntry>();
		for( Iterator<Door> it1 = room.doors.iterator(); it1.hasNext(); )
		{
			Door door_other = it1.next();
			for( Iterator<Door> it2 = this.opened.iterator(); it2.hasNext(); )
			{
				Door door_this = it2.next();
				Vec2 relative_to_this_map = IsPossibleDoorCombination( door_other, door_this );
				if( relative_to_this_map != null )
				{
					DoorPairEntry fde = new DoorPairEntry();
					fde.relativeToSolutionMap = relative_to_this_map;
					fde.other_door = door_other;
					fde.this_door = door_this;
					try {
						//ComputeFitness(fde);
						if( added_rooms.size() > 1 ) ComputeFitness(fde);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					feasible_entries.add(fde);
				}
			}
		}
		return feasible_entries;
	}
	
	float ComputeFitness(DoorPairEntry dpe) throws Exception {
		RoomInstance r1 = dpe.this_door.ri_owner;
		RoomInstance r2 = dpe.other_door.ri_owner;
		if( this.graph_matrix.GetUpper(r1.id, r2.id) != Float.MAX_VALUE ) {
			throw new Exception("el enlace " + r1.id + ", " + r2.id + " estaba creado " + this.graph_matrix.GetUpper(r1.id, r2.id));
		} else {
			this.graph_matrix.SetUpper(r1.id, r2.id, r1.globalPosition.distance(dpe.relativeToSolutionMap));
			float fitness = ComputeFitness();
			this.graph_matrix.SetUpper(r1.id, r2.id, Float.MAX_VALUE);
			return fitness;
		}
	}

	private float ComputeFitness() {
		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(new UpperMatrix2D<Float>(graph_matrix));
		return fws.GetDistance();
	}

	private Vec2 CheckInsert(Door door_other, Door door_this, int dr, int dc, Door.Type doortype)
	{
		Vec2 ret = null;
		if( door_other.type == door_this.type )
		{
			Vec2 this_global = door_this.GetGlobalPosition();
			if( door_other.type == doortype )
			{
				if( this.tilemap.Get(this_global.y + dr, this_global.x + dc) == TileType.FREE )
				{
					Vec2 offset = new Vec2(this_global.y + dr - door_other.localPosition.y, this_global.x + dc - door_other.localPosition.x);
					if( !this.tilemap.CollideWith(door_other.ri_owner.prefab.map, offset.x, offset.y) )
					{
						ret = offset;
					}
				}
			}
		}
		return ret;
	}

	public Vec2 IsPossibleDoorCombination(Door door_other, Door door_this) {
		Vec2 pos = null;  pos = CheckInsert(door_other, door_this,   1,   0,   Door.Type.HORIZONTAL);
		if( pos == null ) pos = CheckInsert(door_other, door_this,  -1,   0,   Door.Type.HORIZONTAL);
		if( pos == null ) pos = CheckInsert(door_other, door_this,   0,   1,   Door.Type.VERTICAL);
		if( pos == null ) pos = CheckInsert(door_other, door_this,   0,  -1,   Door.Type.VERTICAL);

		return pos;
	}

	public void RenderCanvas() {
		(new TileMapRenderer(new TileMap(this.CreateTileMapWithDoors()))).Show();
	}

	public TileMap CreateTileMapWithDoors( ) {
		return CreateTileMapWithDoors(false, false, false);
	}
	
	public TileMap CreateTileMapWithDoors( boolean show_closed, boolean show_opened, boolean show_dpes )
	{
		TileMap tm = new TileMap(this.tilemap);

		if( show_closed ) {
			for( Door door : this.closed )
			{
				if( door.type == Door.Type.HORIZONTAL) {
					tm.Set(door.GetGlobalPosition().y, door.GetGlobalPosition().x, TileType.DOORL);
				} else {
					tm.Set(door.GetGlobalPosition().y, door.GetGlobalPosition().x, TileType.DOORH);
				}
			}
		}
		
		if( show_opened ) {
			for( Door door : this.opened )
			{
				if( door.type == Door.Type.HORIZONTAL) {
					tm.Set(door.GetGlobalPosition().y, door.GetGlobalPosition().x, TileType.OPENED);
				} else {
					tm.Set(door.GetGlobalPosition().y, door.GetGlobalPosition().x, TileType.OPENED);
				}
			}
		}
		
		if( show_dpes ) {
			for( DoorPairEntry dpe : this.added_dpes ) {
				tm.Set(dpe.other_door.GetGlobalPosition().y, dpe.other_door.GetGlobalPosition().x, TileType.DPES);
				tm.Set(dpe.this_door.GetGlobalPosition().y, dpe.this_door.GetGlobalPosition().x, TileType.DPES);
			}
			for( DoorPairEntry dpe : this.all_feasible_dpes ) {
				tm.Set(dpe.other_door.GetGlobalPosition().y, dpe.other_door.GetGlobalPosition().x, TileType.OPENED);
				tm.Set(dpe.this_door.GetGlobalPosition().y, dpe.this_door.GetGlobalPosition().x, TileType.OPENED);
			}
		}

		return tm;
	}

	public List<RoomInstance> GetRemainingRooms() {
		return this.remaining_rooms;
	}

	public boolean IsComplete() {
		return remaining_rooms.size() == 0;
	}

	public List<RoomInstance> GetRooms() {
		return this.added_rooms;
	}

}
