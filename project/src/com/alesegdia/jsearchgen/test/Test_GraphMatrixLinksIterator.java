package com.alesegdia.jsearchgen.test;

import com.alesegdia.jsearchgen.model.map.GraphMatrixLinksIterator;
import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public class Test_GraphMatrixLinksIterator {

	public static void main (String args[]) {
		UpperMatrix2D<Float> test_matrix = new UpperMatrix2D<Float>(10,10,Float.MAX_VALUE);
		test_matrix.SetUpper(0, 1, 1f);
		test_matrix.SetUpper(0, 4, 1f);
		test_matrix.SetUpper(0, 8, 1f);
		test_matrix.SetUpper(3, 4, 1f);
		test_matrix.SetUpper(3, 7, 1f);
		test_matrix.SetUpper(6, 2, 1f);
		test_matrix.SetUpper(8, 3, 1f);
		test_matrix.SetUpper(8, 9, 1f);
		
		for( int i = 0; i < 10; i++ ) {
			GraphMatrixLinksIterator it = new GraphMatrixLinksIterator(test_matrix, i);
			System.out.print("Links for " + i + ": ");
			while( it.hasNext() ) {
				System.out.print(it.next() + ", ");
			}
			System.out.println();
		}
	}
		
}
