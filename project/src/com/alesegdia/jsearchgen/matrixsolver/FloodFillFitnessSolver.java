package com.alesegdia.jsearchgen.matrixsolver;

import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphMatrixLinksIterator;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public class FloodFillFitnessSolver {
	
	private List<Integer> main_path;
	private UpperMatrix2D<Boolean> visited;
	private UpperMatrix2D<Float> graph_matrix;
	private float branching_fitness = 0;
	private float alt_path_length_fitness = 0;

	public void Solve(UpperMatrix2D<Float> matrix, List<Integer> main_path ) {
		
		this.visited = new UpperMatrix2D<Boolean>(matrix.cols, matrix.cols, false);
		this.main_path = main_path;
		this.graph_matrix = matrix;
		
		RecursiveFloodFill(main_path.get(0));
		
	}
	
	private void RecursiveFloodFill( int current_room ) {
		GraphMatrixLinksIterator it = new GraphMatrixLinksIterator( this.graph_matrix, current_room );
		int i = 0;
		while( it.hasNext() ) {
			int neighboor = it.next();
			i++;
			if( !this.visited.GetUpper( current_room, it.next() ) ) {
				this.visited.SetUpper(current_room, neighboor, true);
				if( !this.main_path.contains(neighboor) ) {
					this.alt_path_length_fitness++;
				}
				RecursiveFloodFill(neighboor);
			}
		}
		if( i > 2 ) {
			this.branching_fitness++;
		}
	}
	
	public float GetBranchingFitness() {
		return branching_fitness;
	}
	
	public float GetAltPathLengthFitness() {
		return alt_path_length_fitness;
	}

}
