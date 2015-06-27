package com.alesegdia.jsearchgen.model.room;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.alesegdia.jsearchgen.util.RNG;

public class ProbabilityPrefabSelector implements IPrefabSelector {
	
	private float[] probs;

	public ProbabilityPrefabSelector( float probs[] ) {
		this.probs = probs;
	}

	@Override
	public Iterator<RoomInstance> GetPrefabIterator( List<RoomInstance> modelInstances ) {
		float p = RNG.rng.nextFloat();
		float acc = 0;
		RoomInstance selected = null;
		for( int i = 0; i < probs.length; i++ ) {
			acc += probs[i];
			if( p < acc ) {
				selected = modelInstances.get(i);
				break;
			}
		}
		List<RoomInstance> l = new ArrayList<RoomInstance>();
		l.add(selected);
		return l.iterator();
	}

}
