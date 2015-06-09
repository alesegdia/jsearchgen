package com.alesegdia.jsearchgen.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import com.alesegdia.jsearchgen.matrixsolver.FloydWarshallSolver;
import com.alesegdia.jsearchgen.model.map.GraphGridModel;
import com.alesegdia.jsearchgen.model.map.TileMap;
import com.alesegdia.jsearchgen.model.map.TileType;
import com.alesegdia.jsearchgen.model.room.RoomInstance;

public class GraphGridSolutionRenderer extends JComponent implements KeyListener {
	
	public static boolean blocked = true;
	private GraphGridModel ggs;
	private TileMap map;
	private Dimension dimension;
	int r_start = 0;
	int r_end = 1;
	public static int TILE_SIZE = 5;
	
	public GraphGridSolutionRenderer(final GraphGridModel ggs)
	{
		this.ggs = ggs;
		blocked = true;
		this.map = ggs.CreateTileMapWithDoors();
		FloydWarshallSolver fws = new FloydWarshallSolver();
		fws.Solve(ggs.graph_matrix.Clone());
		r_start = fws.GetSpawnRoom();
		r_end = fws.GetGoalRoom();
		this.dimension = new Dimension(map.cols * TILE_SIZE, map.rows * TILE_SIZE);
		this.setFocusable(true);
		this.addKeyListener(this);
	}

    Font f = new Font("Sans", Font.BOLD, 10);
	private String current_mode = "DPES";
	private Font fb = new Font("Sans", Font.BOLD, 15);
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for( int i = 0; i < this.map.cols; i++ )
		{
			for( int j = 0; j < this.map.rows; j++ )
			{
				Color c = Color.magenta;
				switch( map.Get(j, i) )
				{
				case TileType.FREE:	c = Color.white; 			break;
				case TileType.DOORL: 	c = Color.cyan; 			break;
				case TileType.DOORH: 	c = Color.yellow;			break;
				case TileType.USED: 	c = Color.lightGray; 		break;
				case TileType.WALL: 	c = Color.green; 			break;
				case TileType.DPES: 	c = Color.red; 				break;
				case TileType.OPENED: 	c = Color.blue; 			break;
				}
				g.setColor(c);
				g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
			}
		}
	     g.setFont(f); 

		for( RoomInstance ri : ggs.added_rooms ) {
			if( ri.id == r_start ) {
			     g.setFont(fb ); 
			     g.setColor(new Color(128,0,0));
			} else if( ri.id == r_end ) {
			     g.setFont(fb); 
			     g.setColor(new Color(0,0,128));
			} else {
			     g.setFont(f); 
		     g.setColor(new Color(255,0,0));
			}
			g.drawString(Integer.toString(ri.id), ri.globalPosition.x * TILE_SIZE + 30, ri.globalPosition.y * TILE_SIZE + 30);
		}
	     g.setColor(new Color(0,0,0));
		g.drawString(current_mode , 20, 20);
	}

	public Dimension getPreferredSize() {
		return this.dimension;
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	public void Show()
	{
		JFrame mainFrame = new JFrame("Map renderer");
		mainFrame.getContentPane().add(this);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_F1 ) {
			System.out.println("F1");
			map = ggs.CreateTileMapWithDoors(true, false, false, false);
			this.current_mode = "CLOSED";
		} else if( e.getKeyCode() == KeyEvent.VK_F2 )	{
			System.out.println("F2");
			map = ggs.CreateTileMapWithDoors(false, true, false, false);
			this.current_mode = "OPENED";
		} else if( e.getKeyCode() == KeyEvent.VK_F3 )	{
			System.out.println("F3");
			map = ggs.CreateTileMapWithDoors(false, false, true, false);
			this.current_mode = "DPES";
		} else if( e.getKeyCode() == KeyEvent.VK_F4 )	{
			System.out.println("F4");
			map = ggs.CreateTileMapWithDoors(false, false, false, true);
			this.current_mode = "OPENEDDOORS";
		} else if( e.getKeyCode() == KeyEvent.VK_F5 )	{
			System.out.println("BEFORE: " + blocked);
			blocked = false;
			System.out.println("AFTER: " + blocked);
		}
		this.repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void Update() {
		blocked = true;
		this.repaint();
		map = ggs.CreateTileMapWithDoors(false,false,true,false);
	}

}
