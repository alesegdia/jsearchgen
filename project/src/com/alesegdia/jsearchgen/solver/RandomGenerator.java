package com.alesegdia.jsearchgen.solver;

import java.util.LinkedList;
import java.util.List;

import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.room.RoomInstance;
import com.alesegdia.jsearchgen.util.RNG;

public class RandomSolver implements ISolver {

	List<RoomInstance> startRoomList = new LinkedList<RoomInstance>();
	private GraphGridModel randomModel;

	public RandomSolver ( GraphGridModel first_solution ) {
		this.randomModel = first_solution;
	}

	@Override
	public void Solve()
	{
		while( !randomModel.IsComplete() )
		{
			if( !randomModel.InsertRandomFeasibleRoom() )
			{
				System.err.println("ERROR: can't build a complete solution from this partial solution and this list of remaining rooms: ");
				break;
			}
		}
	}

}
