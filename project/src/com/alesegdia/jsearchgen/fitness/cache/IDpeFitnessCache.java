package com.alesegdia.jsearchgen.fitness.cache;

import com.alesegdia.jsearchgen.model.room.DoorPairEntry;

public interface IDpeFitnessCache {
	
	/**
	 * Returns cached fitness if any. Returns 
	 * @param other
	 * @return cached fitness if present, -1f otherwise
	 */
	public DoorPairEntry Precached(DoorPairEntry other);
	
	/**
	 * Store-at-cache behaviour
	 * @param dpe to store
	 */
	public void Cache(DoorPairEntry dpe);
	
}
