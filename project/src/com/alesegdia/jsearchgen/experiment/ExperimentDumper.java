package com.alesegdia.jsearchgen.experiment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.alesegdia.jsearchgen.experiment.ExperimentSuite.TestCase;

public class ExperimentDumper {

	public static void main(String[] args) throws Exception {
		String[] experiments = {
				"bs-alwayscache-compare",
				"bs-dpediv085f",
				"bs-dpediv095f",
				"bs-dpediv75f",
				"bs-nocache-compare",
				"bs-opt-compare",
				"bs-opt-medium-sample",
				"bs-opt-real-sample",
				"bs-opt-variabilty-sample",
				"bs-opt-variabilty-sample-fixed",
				"bs-randoors04f",
				"bs-randoors06f",
				"bs-randoors08f",
				"bs-refresher10",
				"bs-refresher2",
				"bs-refresher5",
		};

		for( String experiment : experiments ) {
			FileInputStream fos = new FileInputStream("../experiments/" + experiment);
			ObjectInputStream oos = new ObjectInputStream(fos);
			TestCase tc = (TestCase) oos.readObject();
			oos.close();
			fos.close();		
			System.out.println(tc);
		}

	}
	
}
