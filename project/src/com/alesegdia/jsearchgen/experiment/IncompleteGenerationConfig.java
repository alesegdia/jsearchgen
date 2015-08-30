package com.alesegdia.jsearchgen.experiment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alesegdia.jsearchgen.config.CacheType;
import com.alesegdia.jsearchgen.config.CombinatorType;
import com.alesegdia.jsearchgen.config.DoorGenType;
import com.alesegdia.jsearchgen.config.GenerationConfig;
import com.alesegdia.jsearchgen.config.SolverType;

public class IncompleteGenerationConfig extends GenerationConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;

	public IncompleteGenerationConfig(String string) {
		this(0);
		combinator_type = CombinatorType.PARAMETRIZED;
		combinator_attack = 0;
		combinator_decay = 0;
		fitnesses_params[0] = 1;
		fitnesses_params[1] = 1;
		fitnesses_params[2] = 1;
		fitnesses_params[3] = 1;

		this.name = string;
	}
	
	public IncompleteGenerationConfig(int num_prefabs) {
		super(num_prefabs);
	}
	
	HashMap<String, String> propsMap = new HashMap<String, String>();
	
	public void buildPropsMap() {
		
		/* SOLVER TYPE **********************************************/
		if( this.solver_type == SolverType.BEST_SEARCH ) {
			propsMap.put("SolverType", "BestSearch");
			propsMap.put("BestSearch DPE Divisor", String.valueOf(this.bestsearch_dpe_divisor));
		} else if( this.solver_type == SolverType.RANDOM ) {
			propsMap.put("SolverType", "Random");
		} else if( this.solver_type == SolverType.MIXED ) {
			propsMap.put("SolverType", "Mixed");
			propsMap.put("BestSearch DPE Divisor", String.valueOf(this.bestsearch_dpe_divisor));
		}
		/************************************************************/
		
		/* CACHE TYPE ***********************************************/
		if( this.cache_type == CacheType.NO_CACHE ) {
			propsMap.put("CacheType", "NO CACHE");
		} else if( this.cache_type == CacheType.ALWAYS ) {
			propsMap.put("CacheType", "ALWAYS");
		} else if( this.cache_type == CacheType.REFRESHER ) {
			propsMap.put("CacheType", "REFRESHER");
			propsMap.put("Refresher cache divisor", String.valueOf(this.refresher_n));
		}
		/************************************************************/

		if( this.doorgen_type == DoorGenType.ALL ) {
			propsMap.put("DoorGenType", "ALL");
		} else if( this.doorgen_type == DoorGenType.RANDOM ) {
			propsMap.put("DoorGenType", "RANDOM");
			propsMap.put("Random doors param", ""+this.doorgen_random_doors);
		} else if( this.doorgen_type == DoorGenType.DIVISOR ) {
			propsMap.put("DoorGenType", "DIVISOR");
			propsMap.put("Divisor doors param", "" + this.doorgen_divisor);
		}
	}
	
	public String toString() {
		if( propsMap.size() == 0 ) {
			this.buildPropsMap();
		}
		String s = "=============\n" +
					this.name + "\n";
		s += "\\begin{center}\n" +
				   "	\\begin{tabular}{ | c | c | }\n\\hline\n" +
				   " 		Property & Value \\\\ \\hline\n";
		for( Map.Entry<String, String> entry : propsMap.entrySet() ) {
			s += entry.getKey() + " & " + entry.getValue() + " \\\\ \n";
		}
		s += "\\hline\n" +
			 "	\\end{tabular}\n" +
			 "\\end{center}\n";
		return s;
	}
	
}