package com.alesegdia.jsearchgen.fitness.solver;

import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphMatrixLinksIterator;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public class FloodFillGraphMatrixSolver {
	
	private List<Integer> main_path;
	public static UpperMatrix2D<Boolean> visited;
	private UpperMatrix2D<Float> graph_matrix;
	private float branching_fitness = 0;
	private float alt_path_length_fitness = 0;
	
	public void Solve(UpperMatrix2D<Float> matrix, List<Integer> main_path ) {
		
		for( int i = 0; i < matrix.cols; i++ ) {
			for( int j = 0; j < matrix.rows; j++ ) {
				visited.SetUpper(i, j, false);
			}
		}
		//this.visited = new UpperMatrix2D<Boolean>(matrix.cols, matrix.cols, false);
		this.main_path = main_path;
		this.graph_matrix = matrix;
		
		RecursiveFloodFill(main_path.get(0), 1);
		
	}
	
	private void RecursiveFloodFill( int current_room, int from_alt ) {
		GraphMatrixLinksIterator it = new GraphMatrixLinksIterator( this.graph_matrix, current_room );
		int num_neighboors = 0;
		while( it.hasNext() ) {
			int neighboor = it.next();
			num_neighboors++;
			if( num_neighboors > 2 ) {
				this.branching_fitness += num_neighboors;
			}
			if( !this.visited.GetUpper( current_room, it.next() ) ) {
				this.visited.SetUpper(current_room, neighboor, true);
				if( !this.main_path.contains(neighboor) ) {
					this.alt_path_length_fitness += 1+from_alt;
					from_alt++;
				} else {
					from_alt = 1;
				}
				RecursiveFloodFill(neighboor, from_alt);
			}
		}
	}
	
	public float GetBranchingFitness() {
		return branching_fitness;
	}
	
	public float GetAltPathLengthFitness() {
		return alt_path_length_fitness;
	}

}
