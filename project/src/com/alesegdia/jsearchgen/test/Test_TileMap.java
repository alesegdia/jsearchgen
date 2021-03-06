package com.alesegdia.jsearchgen.test;

import java.util.Arrays;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.TileMap;
import com.alesegdia.jsearchgen.model.room.PrefabManager;
import com.alesegdia.jsearchgen.view.TileMapRenderer;

public class Test_TileMap {
	
	public static void br() {
		System.out.println();
	}

	public static void main(String[] args) {

		Integer[] map_data = new Integer[] { 1, 1, 1, 1,
											 1, 3, 3, 1,
											 1, 1, 1, 1 };

		List<Integer> data = Arrays.asList(map_data);
		TileMap mini = new TileMap(data, 3, 4);
		TileMap output = new TileMap(15,30,0);

		mini.Render();
		br();
		output.Apply(mini, 10, 5);
		output.Apply(mini, -1, -1);
		output.Apply(mini, 13, 27);
		output.Render();

		// CONSOLE DEBUG ***************************
		br();
		PrefabManager pmgr = new PrefabManager();
		pmgr.prefabs.get(0).GetTileMap().Render();
		br();
		mini.Render();
		br();
		System.out.println(pmgr.prefabs.get(0).GetTileMap().CollideWith(mini, 0, 0));
		System.out.println(pmgr.prefabs.get(0).GetTileMap().CollideWith(mini, 9, 0));
		pmgr.prefabs.get(0).GetTileMap().Apply(mini, 1, 9);
		// *****************************************
		
		// GRAPHICAL DEBUG *************************
		TileMapRenderer mr = new TileMapRenderer(pmgr.prefabs.get(0).GetTileMap());
		mr.Show();
		(new TileMapRenderer(mini)).Show();
		// *****************************************
	}

}
