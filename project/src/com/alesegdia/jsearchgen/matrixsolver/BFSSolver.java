package com.alesegdia.jsearchgen.matrixsolver;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;
import com.alesegdia.jsearchgen.util.Vec2;

public class BFSSolver {

	private boolean[] visited;
	private UpperMatrix2D<Float> upper_matrix;

	List<Integer> path = new ArrayList<Integer>();
	
	public List<Integer> GetPath() {
		return path;
	}

	public void Solve(GraphGridModel ggm, int riID, Vec2 relativeToSolutionMap) {

		UpperMatrix2D<Float> upper_matrix = ggm.graph_matrix;
		this.visited = new boolean[upper_matrix.cols];
		this.upper_matrix = upper_matrix;
		
		for( int i = 0; i < this.visited.length; i++ ) {
			visited[i] = false;
		}

		bfs(riID);
		
	}

	private void bfs(int riID) {
		visited[riID] = true;
		this.path.add(riID);
		
		for( int i = 0; i < visited.length; i++ ) {
			if( upper_matrix.GetUpper(riID, i) != Float.MAX_VALUE && !visited[i]) {
				bfs(i);
			}
		}
	}

}
