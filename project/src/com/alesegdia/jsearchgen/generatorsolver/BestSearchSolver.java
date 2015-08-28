package com.alesegdia.jsearchgen.generatorsolver;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.DoorPairEntry;
import com.alesegdia.jsearchgen.util.RNG;

public class BestSearchSolver implements IMapGenSolver {

	private float dpe_divisor;

	public BestSearchSolver( float bestsearch_dpe_divisor ) {
		this.dpe_divisor = 1f-bestsearch_dpe_divisor;
	}
	
	@Override
	public boolean Step(GraphGridModel ggm)
	{
		List<DoorPairEntry> feasible_door_pairs = ggm.ComputeAllFeasibleDPE();
		shuffle(feasible_door_pairs);
		List<DoorPairEntry> aux = new LinkedList<DoorPairEntry>();
		int k = (int) (feasible_door_pairs.size() * dpe_divisor);
		if( k <= 0 ) {
			k = 1;
		} else if( k >= feasible_door_pairs.size() ) {
			k = feasible_door_pairs.size()-1;
		}
		//System.out.println("K: " + k + ", sz: " + feasible_door_pairs.size());
		for( int i = 0; i < feasible_door_pairs.size(); i++ ) {
			if( i % k == 0 ) {
				aux.add(feasible_door_pairs.get(i));
			}
		}
		shuffle(aux);
		DoorPairEntry random = ggm.GetBestDPE(aux);
		ggm.ConnectDPE(random);
		return random != null;
	}

	private void shuffle(List<DoorPairEntry> l) {
		for( int i = 0; i < l.size(); i++ ) {
			int k = RNG.rng.nextInt(l.size());
			DoorPairEntry tmp = l.get(k);
			l.set(k, l.get(i));
			l.set(i, tmp);
		}
	}


}
