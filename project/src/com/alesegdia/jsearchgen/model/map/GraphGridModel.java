package com.alesegdia.jsearchgen.model.map;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.config.CacheType;
import com.alesegdia.jsearchgen.config.GenerationConfig;
import com.alesegdia.jsearchgen.fitness.cache.DpeAlwaysCache;
import com.alesegdia.jsearchgen.fitness.cache.DpeCacheRefresher;
import com.alesegdia.jsearchgen.fitness.cache.DpeDummyCache;
import com.alesegdia.jsearchgen.fitness.cache.IDpeFitnessCache;
import com.alesegdia.jsearchgen.fitness.solver.IFitnessSolver;
import com.alesegdia.jsearchgen.fitness.solver.MultiObjectiveFitness;
import com.alesegdia.jsearchgen.model.room.AInstanceManager;
import com.alesegdia.jsearchgen.model.room.Door;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
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
	
	private static final int SOLUTION_WIDTH = 500;
	private static final int SOLUTION_HEIGHT = 500;
	/**
	 * TileMap representing the entire map as a Matrix2D, with proper rooms placed
	 */
	public TileMap tilemap;
	public List<Door> opened = new LinkedList<Door>();
	public List<Door> closed = new LinkedList<Door>();
	public List<RoomInstance> added_rooms = new LinkedList<RoomInstance>();
	public List<DoorPairEntry> added_dpes = new LinkedList<DoorPairEntry>();
	public List<DoorPairEntry> all_feasible_dpes = new LinkedList<DoorPairEntry>();
	public RoomInstance initialRoom;
	
	public UpperMatrix2D<Float> graph_matrix;

	AInstanceManager imgr;

	public GraphGridModel( AInstanceManager imgr, int rows, int cols )
	{
		this.imgr = imgr;
		tilemap = new TileMap(rows, cols, TileType.FREE);
	}

	GraphGridModel( AInstanceManager imgr, GraphGridModel other )
	{
		//this(imgr);
		tilemap = new TileMap(other.tilemap);
	}

	static int a = 0;
	public GraphGridModel(AInstanceManager imgr, int width, int height, boolean insert_first) throws Exception {
		this(imgr, height, width);
		if( insert_first ) {
			try {
				// insertamos la primera elegida de forma aleatoria
				RoomInstance selected = imgr.PopRandomAvailableRoom();
				InsertFirstRoom(selected);
			} catch(IndexOutOfBoundsException e) {
				System.err.println("remaining_rooms list empty!");
			}
		}
	}

	private void InsertFirstRoom(RoomInstance selected) throws Exception {
		graph_matrix = new UpperMatrix2D<Float>(imgr.NumRemainingRooms()+1, imgr.NumRemainingRooms()+1, Float.MAX_VALUE);
		selected.globalPosition.Set(30, 10);
		AttachRoom(selected, 30, 10);
		this.ggbd.initial_room = selected;
	}

	public GraphGridModel(AInstanceManager imgr, boolean insert_first) throws Exception {
		this(imgr, SOLUTION_WIDTH, SOLUTION_HEIGHT, insert_first);
	}

	public GraphGridModel(AInstanceManager imgr) throws Exception {
		this(imgr, SOLUTION_WIDTH, SOLUTION_HEIGHT, true);
	}

	public void AttachRoom(RoomInstance room, int r, int c)
	{
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
	
	public List<DoorPairEntry> ComputeAllFeasibleDPE() {
		// precompute if needed
		
		// extract feasible doors for each room
		List<DoorPairEntry> feasible_door_pairs = new LinkedList<DoorPairEntry>();
		for( Iterator<RoomInstance> it = imgr.RemainingInstanceslIterator(); it.hasNext(); )
		{
			RoomInstance room = it.next();
			if( imgr.IsThereAvailableInstances(room) ){
				List<DoorPairEntry> l = this.GetFeasibleDoorsForRoom(room);
				feasible_door_pairs.addAll(l);
				//System.out.println("ROOM " + l.size());
	
				all_feasible_dpes.addAll(l);
			}
		}
		return feasible_door_pairs;
	}
	
	void ComputeFitness(DoorPairEntry dpe) throws Exception {
		RoomInstance r1 = dpe.this_door.ri_owner;
		int r2_id = imgr.GetLastModelIDForInstance(dpe.other_door.ri_owner);
		if( this.graph_matrix.GetUpper(r1.id, r2_id) != Float.MAX_VALUE ) {
			throw new Exception("el enlace " + r1.id + ", " + r2_id + " estaba creado " + this.graph_matrix.GetUpper(r1.id, r2_id));
		} else {
			RoomInstance test = dpe.other_door.ri_owner;
			Vec2 prevPos = test.globalPosition;
			test.globalPosition = dpe.relativeToSolutionMap;
			this.added_rooms.add(test);
			this.graph_matrix.SetUpper(r1.id, r2_id, r1.globalPosition.distance(dpe.relativeToSolutionMap));
			
			dpe.fitness = ComputeFitness(r2_id, dpe.relativeToSolutionMap);
			
			this.graph_matrix.SetUpper(r1.id, r2_id, Float.MAX_VALUE);
			test.globalPosition = prevPos;
			this.added_rooms.remove(test);
		}
	}
	public void SetFitnessSolver(IFitnessSolver solver) {
		this.fitnessSolver = solver;
	}

	IFitnessSolver fitnessSolver;
	
	private MultiObjectiveFitness ComputeFitness(int riID, Vec2 relativeToSolutionMap) {
		return fitnessSolver.ComputeFitness(this, riID, relativeToSolutionMap);
	}
	
	public static long fitness_time = 0;
	IDpeFitnessCache fitness_cache = new DpeDummyCache();
	
	public DoorPairEntry GetBestDPE(List<DoorPairEntry> feasible_door_pairs) {
		DoorPairEntry best = null;
		if( !feasible_door_pairs.isEmpty() ) {
			best = feasible_door_pairs.get(0);
			for( DoorPairEntry dpe : feasible_door_pairs ) {
				DoorPairEntry cached_dpe = fitness_cache.Precached(dpe);
				if( cached_dpe == null ) {
					try {
						long t1 = System.nanoTime();
						ComputeFitness(dpe);
						long t2 = System.nanoTime();
						long solve_time = t2 - t1;
						fitness_time += solve_time;
						fitness_cache.Cache(dpe);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					dpe.fitness = cached_dpe.fitness;
				}
				if( dpe.fitness.total_fitness > best.fitness.total_fitness ) {
					best = dpe;
				}
			}
		}
		fitnessSolver.NotifySelected(best.fitness);
		fitness_cache.NotifyStep();
		return best;
	}

	public DoorPairEntry GetRandomDPE(List<DoorPairEntry> feasible_door_pairs) {
		DoorPairEntry dpe = null;
		if( !feasible_door_pairs.isEmpty() ) {
			int door_index = RNG.rng.nextInt(feasible_door_pairs.size());
			dpe = feasible_door_pairs.get(door_index);
		}
		return dpe;
	}

	public void ConnectDPE(DoorPairEntry dpe) {
		RoomInstance room_to_attach = this.imgr.PopInstanceFromModel(dpe.other_door.ri_owner);
		this.AttachRoom(room_to_attach, dpe.relativeToSolutionMap.x, dpe.relativeToSolutionMap.y);
		Door door = new Door();
		door.ri_owner = room_to_attach;
		door.localPosition.Set(dpe.other_door.localPosition);
		door.type = dpe.other_door.type;
		dpe.other_door = door;
		this.Connect(door, dpe.this_door);
		this.closed.add(dpe.other_door);
		this.closed.add(dpe.this_door);
		this.opened.remove((Object)dpe.other_door);
		this.opened.remove((Object)dpe.this_door);
		//this.remaining_rooms.remove(dpe.other_door.ri_owner);
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
					feasible_entries.add(fde);
				}
			}
		}
		return feasible_entries;
	}
	
	Vec2 offset = new Vec2(0,0);
	private Vec2 CheckInsert(Door door_other, Door door_this, int dr, int dc, Door.Type doortype)
	{
		Vec2 ret = null;
		if( door_other.type == door_this.type )
		{
			Vec2 this_global = door_this.GetGlobalPosition();
			if( door_other.type == doortype )
			{
				int dx, dy;
				dy = this_global.y + dr;
				dx = this_global.x + dc;
				if( this.tilemap.Get(dy, dx) == TileType.FREE )
				{
					offset.Set(dy - door_other.localPosition.y, dx - door_other.localPosition.x);
					if( !this.tilemap.CollideWith(door_other.ri_owner.prefab.map, offset.x, offset.y) )
					{
						ret = new Vec2(0,0);
						ret.Set(offset);
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
		return CreateTileMapWithDoors(false, false, false, false);
	}
	
	public TileMap CreateTileMapWithDoors( boolean show_closed, boolean show_opened, boolean show_dpes, boolean show_room_feasible_doors )
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
		}
		
		if( show_room_feasible_doors ) {
			for( RoomInstance ri : this.added_rooms ) {
				for( Door door : ri.doors ) {
					tm.Set(door.localPosition.y + ri.globalPosition.y, door.localPosition.x + ri.globalPosition.x, TileType.OPENED);
				}
			}
		}

		return tm;
	}

	public boolean IsComplete() {
		return this.imgr.RemainingRoomsEmpty();
	}

	public List<RoomInstance> GetRooms() {
		return this.added_rooms;
	}

	public void SetupCache(GenerationConfig cfg) {
		if( cfg.cache_type == CacheType.NO_CACHE ) {
			this.fitness_cache = new DpeDummyCache();
		} else if ( cfg.cache_type == CacheType.ALWAYS ) {
			this.fitness_cache = new DpeAlwaysCache();
		} else if( cfg.cache_type == CacheType.REFRESHER ) {
			this.fitness_cache = new DpeCacheRefresher(cfg.refresher_n);
		}
		
	}

}
