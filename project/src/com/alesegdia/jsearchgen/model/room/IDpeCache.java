package com.alesegdia.jsearchgen.model.room;

public interface IDpeCache {
	
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
