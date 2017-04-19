/*import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Container;*/
import javax.swing.*;
import java.awt.*;

public class Gui// extends JFrame
{
	//JFrame f;
	
	public Gui(int columns, int rows, Map map)
	{
		this.columns = columns;
		this.rows = rows;
		
		this.f = new JFrame();
		
		JButton[][] squares = new JButton[columns][rows];
		for(int i=0; i<columns; i++)
		{
			for(int j=0; j<rows; j++)
			{
				char c = map.getTileType(i, j);
				
				drawBackground(squares, c, i, j, map);
				
			}
		}
		
		for(int j=0; j<rows; j++)
		{
			for(int i=0; i<columns; i++)
			{
				this.f.add(squares[i][j]);
			}
		}
		
		this.f.setLayout(new GridLayout(rows, columns));
		
		this.squares = squares;
		
		this.f.setSize(800, 800);
		this.f.setVisible(true);
		this.f.setResizable(true);
		this.f.setDefaultCloseOperation(f.EXIT_ON_CLOSE);
		
		//this.frame = f;
	}
	
	//private JFrame frame;
	private JFrame f;
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
	
	/*make background at squares[i][j] match type of that of map.grid[i][j]
	 * shows initial traffic light images*/
	public void drawBackground(JButton[][] squares, char c, int i, int j, Map map)
	{
		if(c == 'b')
		{	
			squares[i][j] = new JButton("");
			squares[i][j].setBackground(Color.GREEN);
			
		}
		else if(c == 'r')
		{
			squares[i][j] = new JButton("");
			squares[i][j].setBackground(new Color(102, 102, 102));
		}
		else
		{
			squares[i][j] = new JButton("", getLightImage(i, j, map));
		}
	}
	
	/*
	public void test2()
	{
		Icon ic = new ImageIcon("resources/lights/20.png");
		this.squares[5][5].setIcon(ic);
		//this.f.setVisible(false);
		//this.f.setVisible(true);
		System.out.println("BLAH!");
		
		// crap
		//JButton lel = new JButton("toplel");
		//this.f.add(lel);
		//JButton top = new JButton("top");
		//this.f.add(top);
		//this.f.setVisible(false);
		//this.f.setVisible(true);
		
	}
	*/
	
	//public void update(int x, int y, byte direction, byte colour, Map map)
	public void update(Car car, Map map)
	{
		int x = car.getX();
		int y = car.getY();
		int temp = (int) car.getDirection();
		byte colour = car.getColour();
		
		char previousTile;
		
		/*At the previous tile:
		 *if it is a light, change image to that of the light
		 *otherwise, just remove the car image
		 */
		switch(temp)
		{
			case 0:
				previousTile = map.getTileType(x, y+1);
				if(previousTile == 'l')
				{
					this.squares[x][y+1] = new JButton("", getLightImage(x, y+1, map));
				}
				else
				{
					this.squares[x][y+1] = new JButton("");
				}
				break;
			case 1:
				previousTile = map.getTileType(x-1, y);
				if(previousTile == 'l')
				{
					this.squares[x-1][y] = new JButton("", getLightImage(x+1, y, map));
				}
				else
				{
					this.squares[x][y+1] = new JButton("");
				}
				break;
			case 2:
				previousTile = map.getTileType(x, y-1);
				if(previousTile == 'l')
				{
					this.squares[x][y-1] = new JButton("", getLightImage(x, y-1, map));
				}
				else
				{
					this.squares[x][y+1] = new JButton("");
				}
				break;
			case 3:
				previousTile = map.getTileType(x+1, y);
				if(previousTile == 'l')
				{
					this.squares[x+1][y] = new JButton("", getLightImage(x+1, y, map));
				}
				else
				{
					this.squares[x+1][y] = new JButton("");
				}
				break;
			default:
				break;	
		}
		
		Icon iconCar = new ImageIcon("resources/car.png");
		this.squares[x][y] = new JButton("", iconCar);
	}
	
	public void update(int x, int y, Light light)
	{
		byte horizontalPhase = light.getHorizontalPhase();
		byte verticalPhase = light.getVerticalPhase();
		String lightCombination = (horizontalPhase + "" + verticalPhase);
		getLightImage(lightCombination);
		
		Icon ic = getLightImage(lightCombination);
		this.squares[x][y].setIcon(ic);
		//this.squares[x][y] = null;
		//this.squares[x][y] = new JButton("", getLightImage(lightCombination));
	}
	
	public Icon getLightImage(String lightCombination)
	{
		Icon img = null;
		if (lightCombination.equals("00"))
		{
			img = new ImageIcon("resources/lights/00.png");
		}
		else if(lightCombination.equals("01"))
		{
			img = new ImageIcon("resources/lights/01.png");
		}
		else if(lightCombination.equals("02"))
		{
			img = new ImageIcon("resources/lights/02.png");
		}
		else if(lightCombination.equals("10"))
		{
			img = new ImageIcon("resources/lights/10.png");
		}
		else if(lightCombination.equals("20"))
		{
			img = new ImageIcon("resources/lights/20.png");
		}
		
		return img;
	}
	
	public Icon getLightImage(int x, int y, Map map)
	{	
		Light light = (Light) map.getTile(x, y);
		byte horizontalPhase = light.getHorizontalPhase();
		byte verticalPhase = light.getVerticalPhase();
		String temp = (horizontalPhase + "" + verticalPhase);
		
		Icon img = null;
		if(temp.equals("00"))
		{
			img = new ImageIcon("resources/lights/00.png");
		}
		else if(temp.equals("01"))
		{
			img = new ImageIcon("resources/lights/01.png");
		}
		else if(temp.equals("02"))
		{
			img = new ImageIcon("resources/lights/02.png");
		}
		else if(temp.equals("10"))
		{
			img = new ImageIcon("resources/lights/10.png");
		}
		else if(temp.equals("20"))
		{
			img = new ImageIcon("resources/lights/20.png");
		}
		
		return img;
	}
	
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
