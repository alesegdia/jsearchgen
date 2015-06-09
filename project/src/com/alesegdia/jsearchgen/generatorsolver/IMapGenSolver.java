package com.alesegdia.jsearchgen.generatorsolver;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;

public interface IMapGenSolver {

	public boolean Step(GraphGridModel ggm) throws Exception;
	
}
