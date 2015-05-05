package com.alesegdia.jsearchgen.generator.mapgen.model;

import com.alesegdia.jsearchgen.core.map.room.RoomInstance;

public interface IMapGenSolution {

	public void Render();

	public boolean IsComplete();

	public void RenderCanvas();


}
