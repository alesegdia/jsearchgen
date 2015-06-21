package com.alesegdia.jsearchgen.model.map;

import java.util.Iterator;

import com.alesegdia.jsearchgen.util.UpperMatrix2D;

public class GraphMatrixLinksIterator implements Iterator<Integer> {
	
	private int room;
	private int currentLinkIndex;
	private UpperMatrix2D<Float> graphMatrix;
	private int nextLink;
	
	public final static float NO_LINK = Float.MAX_VALUE;
	
	public GraphMatrixLinksIterator( UpperMatrix2D<Float> graph_matrix, int room ) {
		this.room = room;
		this.currentLinkIndex = 0;
		this.graphMatrix = graph_matrix;
	}

	@Override
	public boolean hasNext() {
		nextLink = -1;
		while( currentLinkIndex < graphMatrix.cols && nextLink == -1 ) {
			if( graphMatrix.GetUpper(room, currentLinkIndex) != NO_LINK ) {
				nextLink = currentLinkIndex;
			}
			currentLinkIndex++;
		}
		return nextLink != -1;
	}

	@Override
	public Integer next() {
		return nextLink;
	}

	@Override
	public void remove() {
		System.err.println("REMOVE METHOD NOT IMPLEMENTED IN ITERATOR");
	}

}
