package com.alesegdia.jsearchgen.experiment;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TestCase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IncompleteGenerationConfig cfg;
	public List<TestEntry> entries = new LinkedList<TestEntry>();
	
	public String toString() {
		String s = cfg.toString() + "\n";
		s += "\\begin{center}\n" +
			 "	\\begin{tabular}{ | c | c | c | c | c | }\n\\hline\n";

		s += "Tamaño habitaciones & Número de modelos & Instancias/Modelo & Total habs. & Tiempo de ejecución \\\\ \\hline \n";
		for( TestEntry te : entries ) {
			s += te.toString();
		}
		
		s += "\\hline\n" +
				 "	\\end{tabular}\n" +
				 "\\end{center}\n";
				
		return s;
	}
}