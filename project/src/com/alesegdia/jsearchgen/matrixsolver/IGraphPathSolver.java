package com.alesegdia.jsearchgen.matrixsolver;

import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.util.Vec2;

public interface IGraphPathSolver {

	public int GetSpawnRoom();
	
	public int GetGoalRoom();
	
	public float GetDistance();
	
	public List<Integer> GetPath();
	
	public void Solve(GraphGridModel ggm, int riID, Vec2 relativeToSolutionMap);

	
}
