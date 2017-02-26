package com.alesegdia.jsearchgen.experiment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.alesegdia.jsearchgen.experiment.ExperimentSuite.IncompleteGenerationConfig;
import com.alesegdia.jsearchgen.experiment.ExperimentSuite.TestCase;
import com.alesegdia.jsearchgen.experiment.ExperimentSuite.TestEntry;

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

		String[] dpedivs = {
		};
		
		String[] randoors =  {
		};
		
		String[] experiments2 = {

				"bs-dpediv75f",
				"bs-dpediv085f",
				"bs-dpediv095f",
				"dpedivs.png", 				// image

				"bs-randoors04f",
				"bs-randoors06f",
				"bs-randoors08f",
				"randoors.png",

				"bs-refresher2",
				"bs-refresher5",
				"bs-refresher10",
				"refresher.png",

				"bs-alwayscache-compare",
				"bs-nocache-compare",
				"comp-nc-alw.png", 			// image
				"comp-nc-alw-spl.png", 		// image
				
				"bs-opt-compare",
				"comp-nc-opt.png", 			// image
				"comp-nc-opt-spl.png", 			// image
				"comp-alw-opt.png",
				"comp-alw-opt-spl.png",
				"comp-all.png",
				"comp-all-spl.png",
				
				
				"bs-opt-real-sample",
				"opt-real.png",
				"opt-real-spl.png",
				
				"bs-opt-medium-sample",
				"opt-mid.png",
				"opt-mid-spl.png",
				
				"bs-opt-variabilty-sample",
				"opt-var.png",
				"opt-var-spl.png",
				
				"bs-opt-variabilty-sample-fixed",
				"opt-varfix.png",
				"opt-varfix-spl.png",
				
				"opt-varfix-cmp-spl.png",
				
		};
		
		for( String experiment : experiments2 ) {

			if( experiment.charAt(0) == 'b' && experiment.charAt(1) == 's' && experiment.charAt(2) == '-') {
				FileInputStream fos = new FileInputStream("../experiments/" + experiment);
				ObjectInputStream oos = new ObjectInputStream(fos);
				TestCase tc = (TestCase) oos.readObject();
				oos.close();
				fos.close();
				System.out.println(slide("Configuración " + experiment, CFGtoString(tc.cfg)));
				System.out.println(slide(TCtoString(tc)));
			} else {
				System.out.println(slide("<img src='./" + experiment + "' />"));
			}
		}
	}

	public static String slide(String header, String content) {
		String ret = "";
		ret += "<section data-state='no-toc-progress' id='fragments'>\n";
		ret += "<h3>" + header + "</h3>\n";
		ret += content;
		ret += "\n</section>\n";
		return ret;
	}
	
	public static String slide(String content) {
		String ret = "";
		ret += "<section data-state='no-toc-progress' id='fragments'>\n";
		ret += content;
		ret += "\n</section>\n";
		return ret;
	}
	
	public static String TCtoString(TestCase tc) {
		tc.cfg.buildPropsMap();
		HTMLTableMaker table = new HTMLTableMaker();
		table.initHead();
		table.headTitle("Tam. habs.");
		table.headTitle("Num. modelos");
		table.headTitle("Instancias/modelo");
		table.headTitle("Total habs.");
		table.headTitle("Tiempo ejec.");
		table.closeHead();
		
		table.initBody();
		for( TestEntry te : tc.entries ) {
			table.initRow();
			table.addCol(te.size+"");
			table.addCol(te.numModels+"");
			table.addCol(te.numRoomsPerModel+"");
			table.addCol(te.numModels * te.numRoomsPerModel+"");
			table.addCol(te.time+"");
			table.endRow();
		}
		
		table.endBody();
		
		return table.content;
	}

	
	public static String CFGtoString(IncompleteGenerationConfig cfg) {
		HTMLTableMaker table = new HTMLTableMaker();
		cfg.buildPropsMap();
		table.initHead();
		table.headTitle("Propiedad");
		table.headTitle("Valor");
		table.closeHead();
		
		table.initBody();
		for( Map.Entry<String, String> entry : cfg.propsMap.entrySet() ) {
			String s = entry.getKey() + " & " + entry.getValue() + " \\\\ \n";
		}

		for( Map.Entry<String, String> entry : cfg.propsMap.entrySet() ) {
			table.initRow();
			table.addCol(entry.getKey());
			table.addCol(entry.getValue());
			table.endRow();
		}
		table.endBody();

		return table.content;
	}

	
}
