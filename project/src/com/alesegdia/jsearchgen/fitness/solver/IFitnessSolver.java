package com.alesegdia.jsearchgen.fitness.solver;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;
import com.alesegdia.jsearchgen.util.Vec2;

public interface IFitnessSolver {
	
	public MultiObjectiveFitness ComputeFitness( GraphGridModel graphGridModel, int riID, Vec2 relativeToSolutionMap );

	public void NotifySelected(MultiObjectiveFitness fitness);

}
