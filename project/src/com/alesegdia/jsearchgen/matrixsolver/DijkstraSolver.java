package com.alesegdia.jsearchgen.matrixsolver;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;
import com.alesegdia.jsearchgen.util.Vec2;

public class DijkstraSolver implements IGraphPathSolver {

	private int spawnRoom;

	@Override
	public int GetSpawnRoom() {
		return spawnRoom;
	}

	@Override
	public int GetGoalRoom() {
		return goalRoom;
	}

	@Override
	public float GetDistance() {
		return path.size();
	}

	List<Integer> path = new LinkedList<Integer>();
	private int goalRoom;
	private float distance;
	
	@Override
	public List<Integer> GetPath() {
		return path;
	}

	@Override
	public void Solve(GraphGridModel ggm, int riID, Vec2 relativeToSolutionMap) {
		this.spawnRoom = riID;
		UpperMatrix2D<Float> matrix = ggm.graph_matrix;
		int source = spawnRoom;

		boolean[] visited = new boolean[ggm.graph_matrix.cols];
		for( int i = 0; i < visited.length; i++ ) {
			visited[i] = false;
		}
		
		visited[source] = true;
		List<Integer> used = new LinkedList<Integer>();
		used.add(source);
		
		// build distance vector
		float[] dist = new float[ggm.graph_matrix.cols];
		int[] prev = new int[ggm.graph_matrix.cols];
		for( int i = 0; i < ggm.graph_matrix.cols; i++ ) {
			dist[i] = ggm.graph_matrix.GetUpper(source, i);
			prev[i] = -1;
		}
		
		dist[source] = 0;

		while( used.size() != ggm.graph_matrix.cols ) {
			
			// get not visited node with min distance
			int vmin = 0;
			float dmin = Float.MAX_VALUE;
			for( int i = 0; i < dist.length; i++ ) {
				if( !visited[i] ) {
					float vf = dist[i];
					if( vf < dmin  ) {
						dmin = vf;
						vmin = i;
					}
				}
			}
			visited[vmin] = true;
			
			for( int i = 0; i < matrix.cols; i++ ) {
				float val = matrix.GetUpper(vmin, i);
				if( val != Float.MAX_VALUE ) {
					// it's successor
					if( dist[i] > dist[vmin] + val ) {
						dist[i] = dist[vmin] + val;
						prev[i] = vmin;
					}
				}
			}
			used.add(vmin);
		}
		

		int dest = (riID == 0 ? 1 : 0);
		for( int i = 0; i < prev.length; i++ ) {
			if( dist[dest] < dist[i] && i != source ) {
				dest = i;
			}
		}

		this.distance = dist[dest];

		
		this.goalRoom = dest;
		
		while( dest != source && dest != -1 ) {
			path.add(dest);
			dest = prev[dest];
		}
		
	}
}
