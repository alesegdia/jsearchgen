package com.alesegdia.jsearchgen.generatorsolver;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;

public class MixedSearchRandomSolver implements IMapGenSolver {

	RandomSolver rs;
	BestSearchSolver bss;
	int i = 0;
	
	public MixedSearchRandomSolver() {
		rs = new RandomSolver();
		bss = new BestSearchSolver();
	}
	
	@Override
	public boolean Step(GraphGridModel ggm) throws Exception {
		if( i % 10 == 0 ) {
			return rs.Step(ggm);
		} else {
			return bss.Step(ggm);
		}
	}

}
