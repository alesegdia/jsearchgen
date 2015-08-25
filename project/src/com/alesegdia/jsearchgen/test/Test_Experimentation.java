package com.alesegdia.jsearchgen.test;

import com.alesegdia.jsearchgen.model.map.TileMap;
import com.alesegdia.jsearchgen.model.map.TileType;
import com.alesegdia.jsearchgen.model.room.PrefabManager;
import com.alesegdia.jsearchgen.model.room.RoomPrefab;
import com.alesegdia.jsearchgen.util.RNG;
import com.alesegdia.jsearchgen.util.Vec2;

public class Test_Experimentation {

	public static PrefabManager makeRandomModels( int numModels, Vec2 sizeRange ) {
		PrefabManager pm = new PrefabManager();
		for( int i = 0; i < numModels; i++ ) {
			int w = RNG.rng.nextInt(sizeRange.x, sizeRange.y);
			int h = RNG.rng.nextInt(sizeRange.x, sizeRange.y);
			TileMap tm = new TileMap(h+2, w+2, TileType.FREE);
			for( int c = 1; c < tm.cols-1; c++ ) {
				for( int r = 1; r < tm.rows-1; r++ ) {
					tm.Set(r, c, TileType.USED);
				}
			}
			for( int c = 1; c < tm.cols-1; c++ ) {
				tm.Set(1, c, TileType.WALL );
				tm.Set(h, c, TileType.WALL );
			}
			for( int r = 1; r < tm.rows-1; r++ ) {
				tm.Set(r, 1, TileType.WALL );
				tm.Set(r, w, TileType.WALL );
			}
			tm.Render();
			pm.AddPrefab(tm);
		}
		return pm;
	}
	
	public static void main(String[] args) {
		RNG.rng = new RNG();
		RNG.rng.setSeed(1234);
		makeRandomModels(5, new Vec2(5,10));
	}
	
}
