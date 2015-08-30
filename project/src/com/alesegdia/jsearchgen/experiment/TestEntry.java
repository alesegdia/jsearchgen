package com.alesegdia.jsearchgen.experiment;

import java.io.Serializable;
import java.util.Locale;

public class TestEntry implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int size;
	int numModels;
	int numRoomsPerModel;
	float time;
	
	public String toString() {
		return size + " & " + numModels + " & " + numRoomsPerModel + " & " + numModels * numRoomsPerModel + " & " + String.format(Locale.ENGLISH, "%.4f", time) + " \\\\ \n";
	}
}