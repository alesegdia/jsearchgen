package com.alesegdia.jsearchgen.generator;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.view.GraphGridSolutionRenderer;


public class MapGenerator {

	protected GraphGridModel ggm;
	GraphGridSolutionRenderer ggsr;

	MapGenerator(GraphGridModel ggm) {
		this.ggm = ggm;
		this.ggsr = new GraphGridSolutionRenderer(ggm);
	}
	
	public void Generate() throws Exception {
		this.ggsr.Show();
		while(!this.ggm.IsComplete()){
			if(!this.Step()) {
				throw new Exception("ERROR: can't build a complete solution from this partial solution and this list of remaining rooms " + this.ggm);
			}
			this.ggsr.Update();
			//Thread.sleep(1000);
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
