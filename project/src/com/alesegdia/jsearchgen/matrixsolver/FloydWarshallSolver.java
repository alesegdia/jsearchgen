package com.alesegdia.jsearchgen.matrixsolver;

import com.alesegdia.jsearchgen.util.UpperMatrix2D;


public class FloydWarshallSolver {


	public FloydWarshallSolver() {
	}

	UpperMatrix2D<Float> min_distances;
	private int spawn_room;
	private int goal_room;
	private float distance;
	
	public void Solve(UpperMatrix2D<Float> matrix) {
		min_distances = matrix;

		for( int i = 0; i < matrix.cols; i++ ) {
			min_distances.Set(i, i, 0.f);
		}
		for( int k = 0; k < matrix.cols; k++ ) {
			for( int i = 0; i < matrix.cols; i++ ) {
				for( int j = 0; j < matrix.cols; j++ ) {
					if( min_distances.GetUpper(i, j) > min_distances.GetUpper(i, k) + min_distances.GetUpper(k, j)) {
						float newvalue =              min_distances.GetUpper(i, k) + min_distances.GetUpper(k, j);
						min_distances.SetUpper(i, j, newvalue);
					}
				}
			}
		}
		int biggest_r1 = 0, biggest_r2 = 0;
		float biggest_value = Float.MIN_VALUE;
		for( int i = 0; i < matrix.cols; i++ ) {
			for( int j = 0; j < matrix.cols; j++ ) {
				float val = min_distances.GetUpper(i, j);
				if( val > 0.1f && val < 100000000f && val > biggest_value ) {
					biggest_r1 = i;
					biggest_r2 = j;
					biggest_value = val;
				}
			}
		}
		this.spawn_room = biggest_r1;
		this.goal_room = biggest_r2;
		this.distance = biggest_value;
	}

	public int GetSpawnRoom() {
		return spawn_room;
	}

	public int GetGoalRoom() {
		return goal_room;
	}

	public float GetDistance() {
		return distance;
	}

}
