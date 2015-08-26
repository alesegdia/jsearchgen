package com.alesegdia.jsearchgen.matrixsolver;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;
import com.alesegdia.jsearchgen.util.Vec2;

public class BFSSolver implements IGraphPathSolver {

	private boolean[] visited;
	private UpperMatrix2D<Float> upper_matrix;

	List<Integer> path = new ArrayList<Integer>();
	private int spawnRoom;
	
	@Override
	public List<Integer> GetPath() {
		return path;
	}

	public void Solve(GraphGridModel ggm, int riID, Vec2 relativeToSolutionMap) {

		this.spawnRoom = riID;
		
		UpperMatrix2D<Float> upper_matrix = ggm.graph_matrix;
		this.visited = new boolean[upper_matrix.cols];
		this.upper_matrix = upper_matrix;
		
		for( int i = 0; i < this.visited.length; i++ ) {
			visited[i] = false;
		}

		bfs(riID);
		
	}

	private void bfs(int current) {
		visited[current] = true;
		this.path.add(current);
		
		for( int i = 0; i < visited.length; i++ ) {
			if( upper_matrix.GetUpper(current, i) != Float.MAX_VALUE && !visited[i]) {
				bfs(i);
			}
		}
	}

	@Override
	public int GetSpawnRoom() {
		return spawnRoom;
	}

	@Override
	public int GetGoalRoom() {
		return path.get(path.size()-1);
	}

	@Override
	public float GetDistance() {
		return path.size();
	}

}
