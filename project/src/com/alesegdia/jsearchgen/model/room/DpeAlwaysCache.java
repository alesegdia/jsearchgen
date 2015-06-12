package com.alesegdia.jsearchgen.model.room;

import java.util.LinkedList;
import java.util.List;

public class DpeAlwaysCache implements IDpeCache {

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
