package com.alesegdia.jsearchgen.solver;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;


public class MapGenerator {

	protected GraphGridModel ggm;

	MapGenerator(GraphGridModel ggm) {
		this.ggm = ggm;
	}
	
	public void Generate() throws Exception {
		while(!this.ggm.IsComplete()){
			if(!this.Step()) {
				throw new Exception("ERROR: can't build a complete solution from this partial solution and this list of remaining rooms " + this.ggm.remaining_rooms);
			}
		}
	}

	/**
	 * Ejecuta un paso del generador
	 * @return informa de si se pudo realizar la inserción o o
	 * @throws Exception
	 */
	protected boolean Step() throws Exception {
		throw new Exception("NOT IMPLEMENTED");
	}

}
