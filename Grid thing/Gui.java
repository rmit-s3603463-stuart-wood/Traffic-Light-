/*import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Container;*/
import javax.swing.*;
import java.awt.*;

public class Gui// extends JFrame
{
	JFrame f;
	
	public Gui(int columns, int rows, Map map)
	{
		this.columns = columns;
		this.rows = rows;
		
		f = new JFrame();
		
		JButton[][] squares = new JButton[columns][rows];
		for(int i=0; i<columns; i++)
		{
			for(int j=0; j<rows; j++)
			{
				char c = map.getTileType(i, j);
				
				drawBackground(squares, c, i, j);
				
			}
		}
		
		for(int j=0; j<rows; j++)
		{
			for(int i=0; i<columns; i++)
			{
				f.add(squares[i][j]);
			}
		}
		
		f.setLayout(new GridLayout(rows, columns));
		
		this.squares = squares;
		
		f.setSize(800, 800);
		f.setVisible(true);
		f.setResizable(true);
		f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
	}
	
	private int rows;
	private int columns;
	private JButton[][] squares;
	
	public void testTiles()
	{
		for(int j=0; j<this.rows; j++)
		{
			for(int i=0;i<this.columns; i++)
			{
				this.squares[i][j].setBackground(Color.RED);
				try
				{
					Thread.sleep(10);
				}
				catch(Exception e)
				{
					
				}
			}
		}
	}
	
	/*make background at squares[i][j] match type of that of map.grid[i][j]*/
	public void drawBackground(JButton[][] squares, char c, int i, int j)
	{
		if(c == 'b')
		{
			squares[i][j] = new JButton("");
			squares[i][j].setBackground(Color.GREEN);
		}
		else if(c == 'r')
		{
			squares[i][j] = new JButton("");
			squares[i][j].setBackground(Color.GRAY);
		}
		else
		{
			squares[i][j] = new JButton("T");
			squares[i][j].setBackground(Color.YELLOW);
		}
	}
	
	/*
	public void update(int x, int y, byte direction, byte colour, Map map)
	{
		int temp = (int) direction;
		char previousTile;
		switch(temp)
		{
			case 0:
				
				//if the previous tile was a traffic light, change the icon to the icon for the traffic light
				//otherwise change the icon to null and it will revert to the background
				
				previousTile = map.getTileType(x+1, y);
				drawBackground(this.squares, previousTile, x+1, y); //revert image to background at previous tile
				Icon iconCar = new ImageIcon("resources/car.png");
				squares[i][j] = new JButton("", iconCar);
				
				
		}
	}
	*/
	
	
	/*
	public Gui()
	{
		f = new JFrame();
		
		JButton b1 = new JButton("1");
		b1.setBackground(Color.BLACK);
		JButton b2 = new JButton("2");
		JButton b3 = new JButton("3");
		JButton b4 = new JButton("4");
		
		f.add(b1);
		f.add(b2);
		f.add(b3);
		f.add(b4);
		
		f.setLayout(new GridLayout(2, 2));
		
		f.setSize(800, 600);
		f.setVisible(true);
		f.setResizable(true);
		
	}
	*/
	
	/*
	private Container contents;
	
	//private JButton[][] squares;
	
	public Gui(int columns, int rows, Map map)
	{
		super("Traffic Simulator");
		setSize(800, 600);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
		this.contents = getContentPane();
		this.contents.setLayout(new GridLayout(rows, columns));
		
		JButton[][] squares = new JButton[columns][rows];
		for(int i=0; i<columns; i++)
		{
			for(int j=0; j<rows; j++)
			{
				squares[j][i] = new JButton();
			}
		}
		
		for(int i=0; i<columns; i++)
		{
			for(int j=0; i<rows; j++)
			{
				System.out.println(map.getTileType(i, j));
				
				char c = map.getTileType(i, j);
				
				if(c == 'b')
				{
					squares[j][i].setBackground(Color.GREEN);
				}
				else if(c == 'r')
				{
					squares[j][i].setBackground(Color.LIGHT_GRAY);
				}
				else if(c == 'l')
				{
					squares[j][i].setBackground(Color.YELLOW);
				}
				this.contents.add(squares[j][i]);
			}
		}
	}
	*/
	
	/*
	public Gui(int rows, int columns, Map map)
	{
		super("Traffic Simulator");
		setSize(1024, 768);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.squares = new JButton[columns][rows];
		
		setLayout(new GridLayout(rows, columns));
		
		for(int i=0; i<columns; i++)
		{
			for(int j=0; i<rows; j++)
			{
				char c = map.getTileType(i, j);
				
				if(c == 'b')
				{
					this.squares[i][j].setBackground(Color.GREEN);
				}
				else if(c == 'r')
				{
					this.squares[i][j].setBackground(Color.LIGHT_GRAY);
				}
				else if(c == 'l')
				{
					this.squares[i][j].setBackground(Color.YELLOW);
				}
				//Gui.add
			}
		}
		
		//JButton startButton = new JButton("Start");
		//add(startButton);
	}
	private Container contents;
	
	private JButton[][] squares;
	*/
}
