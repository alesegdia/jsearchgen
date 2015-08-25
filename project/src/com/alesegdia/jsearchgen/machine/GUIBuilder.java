package com.alesegdia.jsearchgen.machine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.alesegdia.jsearchgen.config.CacheType;
import com.alesegdia.jsearchgen.config.CombinatorType;
import com.alesegdia.jsearchgen.config.DoorGenType;
import com.alesegdia.jsearchgen.config.GenerationConfig;
import com.alesegdia.jsearchgen.config.ManagerType;
import com.alesegdia.jsearchgen.config.SolverType;
import com.alesegdia.jsearchgen.fitness.solver.MultiObjectiveFitness;
import com.alesegdia.jsearchgen.model.room.PrefabManager;

public class GUIBuilder extends JPanel {
	
	private Dimension dimension;
	
	private GenerationConfig genConfig;
	
	private JTextField jtf_seed = new JTextField();
	private JComboBox<SolverType> jcb_solverType = new JComboBox<SolverType>();
    private JComboBox<ManagerType> jcb_managerType = new JComboBox<ManagerType>();
    private JComboBox<DoorGenType> jcb_doorGenType = new JComboBox<DoorGenType>();
    private JComboBox<CacheType> jcb_cacheType = new JComboBox<CacheType>();
    private JTextField jtf_prefabInstances[];
    private JComboBox<CombinatorType> jcb_combinatorType = new JComboBox<CombinatorType>();
    private JTextField jtf_combinatorDecay = new JTextField();
    private JTextField jtf_combinatorAttack = new JTextField();
    private JTextField jtf_fitnesses_params[] = new JTextField[MultiObjectiveFitness.NUM_OBJECTIVES];
    private JTextField jtf_solver_params[] = new JTextField[MultiObjectiveFitness.NUM_OBJECTIVES];
    private JButton jbtn_generate = new JButton("Generate!");

	private PrefabManager pmgr;
    
	public GUIBuilder () {
		super(new GridLayout(0,2,10,10));

		this.dimension = new Dimension(500, 400);

		this.AddElement("Seed", jtf_seed);
		
		this.AddElement("Solver type", jcb_solverType);
		jcb_solverType.addItem(SolverType.RANDOM);
		jcb_solverType.addItem(SolverType.BEST_SEARCH);
		
		this.AddElement("Manager type", jcb_managerType);
		jcb_managerType.addItem(ManagerType.BRUTE_FORCE);
		jcb_managerType.addItem(ManagerType.PREFAB_MODEL);
		
        this.AddElement("DoorGen type", jcb_doorGenType);
		jcb_doorGenType.addItem(DoorGenType.ALL);
		jcb_doorGenType.addItem(DoorGenType.RANDOM);
		jcb_doorGenType.addItem(DoorGenType.CUSTOM_NOT_IMPL);
        
		this.AddElement("Manager type", jcb_cacheType);
		jcb_cacheType.addItem(CacheType.NO_CACHE);
		jcb_cacheType.addItem(CacheType.ALWAYS);
		jcb_cacheType.addItem(CacheType.INTERVAL_NOT_IMPL);
		jcb_cacheType.addItem(CacheType.ADAPTATIVE_NOT_IMPL);
		
		pmgr = new PrefabManager();
		pmgr.AddPrefab("rooms/room0.json");
		pmgr.AddPrefab("rooms/room1.json");

		genConfig = new GenerationConfig(pmgr.numPrefabs());
		
		this.jtf_prefabInstances = new JTextField[pmgr.numPrefabs()];
		
		for( int i = 0; i < pmgr.numPrefabs(); i++ ) {
			jtf_prefabInstances[i] = new JTextField();
			this.AddElement("Num prefabs for type " + i, jtf_prefabInstances[i]);
		}
		
		jcb_combinatorType.addItem(CombinatorType.PARAMETRIZED);
		jcb_combinatorType.addItem(CombinatorType.ADAPTATIVE_PARAMETRIZED);

		this.AddElement("Combinator type", jcb_combinatorType);
		this.AddElement("Combinator decay (if adaptative)", jtf_combinatorDecay);
		this.AddElement("Combinator attack (if adaptative)", jtf_combinatorAttack);

		String[] fitnesses_labels = {
				"MainPathLength start param",
				"AltPathLength start param",
				"AltPathBranching start param",
				"RoomCondensation start param",
		};
		
		for( int i = 0; i < MultiObjectiveFitness.NUM_OBJECTIVES; i++ ) {
			jtf_fitnesses_params[i] = new JTextField();
			this.AddElement(fitnesses_labels[i], jtf_fitnesses_params[i]);
		}
        
		
		
		final GUIBuilder guib = this;
		jbtn_generate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				guib.GenerateMap();
			}
		});
		this.AddElement("Click to generate", jbtn_generate);
	}
	
	protected void GenerateMap() {
		DungeonMachine dm = new DungeonMachine();
		try {
			GenerationConfig gc = this.genConfig;
			gc.random_seed = 					Integer.parseInt(this.jtf_seed.getText());
			gc.cloned_rooms = 					false;
			for( int i = 0; i < pmgr.numPrefabs(); i++ ) {
				gc.num_instances_per_prefab[i] = Integer.parseInt(this.jtf_prefabInstances[i].getText());
			}
			gc.doorgen_type = 					this.jcb_doorGenType.getItemAt(jcb_doorGenType.getSelectedIndex());
			gc.manager_type = 					this.jcb_managerType.getItemAt(jcb_managerType.getSelectedIndex());
			gc.cache_type = 					this.jcb_cacheType.getItemAt(jcb_cacheType.getSelectedIndex());
			gc.solver_type = 					this.jcb_solverType.getItemAt(jcb_solverType.getSelectedIndex());
			gc.combinator_type =				this.jcb_combinatorType.getItemAt(jcb_combinatorType.getSelectedIndex());
			gc.combinator_decay = 				Float.parseFloat(this.jtf_combinatorDecay.getText());
			gc.combinator_attack = 				Float.parseFloat(this.jtf_combinatorAttack.getText());

			for( int i = 0; i < pmgr.numPrefabs(); i++ ) {
				gc.fitnesses_params[i] = Float.parseFloat(this.jtf_fitnesses_params[i].getText());
			}
			
			dm.Reset(gc, pmgr);
			dm.Run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void AddElement(String text, JComponent jcomp) {
		JLabel label = new JLabel(text);
		add(label);
		add(jcomp);
	}
	
	public Dimension getPreferredSize() {
		return this.dimension;
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

	public void Show()
	{
		JFrame mainFrame = new JFrame("Map renderer");
		mainFrame.getContentPane().add(this);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

}
