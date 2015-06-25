package com.alesegdia.jsearchgen.fitness.cache;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.room.DoorPairEntry;

public class DpeAlwaysCache implements IDpeFitnessCache {

	List<DoorPairEntry> cached_dpes = new LinkedList<DoorPairEntry>();
	float param;
	
	public DpeAlwaysCache () {
		
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
	
}
