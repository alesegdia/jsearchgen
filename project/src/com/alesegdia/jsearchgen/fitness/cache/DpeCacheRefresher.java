package com.alesegdia.jsearchgen.fitness.cache;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.room.DoorPairEntry;

public class DpeCacheRefresher implements IDpeFitnessCache {

	List<DoorPairEntry> cached_dpes = new LinkedList<DoorPairEntry>();
	float param;
	private int numStepsToRefresh;
	int currentStep = 0;
	
	public DpeCacheRefresher ( int numStepsToRefresh ) {
		this.numStepsToRefresh = numStepsToRefresh;
	}
	
	/**
	 * Ask if DPE fitness is already calculated 
	 * @param other dpe
	 * @return dpe if cached, null otherwise
	 */
	@Override
	public DoorPairEntry Precached(DoorPairEntry other) {
		for( DoorPairEntry dpe : cached_dpes ) {
			if( dpe.Equals(other) ) {
				return dpe;
			}
		}
		return null;
	}

	@Override
	public void Cache(DoorPairEntry dpe) {
		cached_dpes.add(dpe);
	}

	@Override
	public void NotifyStep() {
		currentStep++;
		if( currentStep >= this.numStepsToRefresh ) {
			currentStep = 0;
			cached_dpes.clear();
		}
	}
	
}
