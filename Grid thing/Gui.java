package traffic;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Gui// extends JFrame
{
	//JFrame f;
	
	public Gui(int columns, int rows, Map map)
	{
		this.columns = columns;
		this.rows = rows;
		
		this.f = new JFrame();
		try {
	        UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
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
		this.f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
			squares[i][j].setOpaque(true);
			
		}
		else if(c == 'r')
		{
			squares[i][j] = new JButton("");
			squares[i][j].setBackground(new Color(102, 102, 102));
			squares[i][j].setOpaque(true);
			
		}
		else
		{
			squares[i][j] = new JButton("", getLightImage());
			squares[i][j].setOpaque(true);
		}
	}
	
	//public void update(int x, int y, byte direction, byte colour, Map map)
	public void updateCars(Map map)
	{
		for (int i = 0; i < map.getCars().size(); i++)
		{
			;
			int x = map.getCars().get(i).getX();
			int y = map.getCars().get(i).getY();
			int direction = (int) map.getCars().get(i).getDirection();
			byte colour = map.getCars().get(i).getColour();
			char previousTile;
			int prevX = -1;
			int prevY = -1;
			Image carImage = null;
		
		/*At the previous tile:
		 *if it is a light, change image to that of the light
		 *otherwise, just remove the car image
		 */
			switch(direction)
			{
				case 0:
					prevX = x;
					prevY = y+1;
					break;
				case 1:
					prevX = x+1;
					prevY = y;
					break;
				case 2:
					prevX = x;
					prevY = y-1;
					break;
				case 3:
					prevX = x-1;
					prevY = y;
					break;
				default:
					break;	
			}
			
			previousTile = map.getTileType(prevX, prevY);
			
			if (previousTile == 'l')
			{
				this.squares[prevX][prevY].setIcon(getLightImage());
			}
			else
			{
				this.squares[prevX][prevY].setIcon(null);
			}
			
			try {
				carImage = ImageIO.read(getClass().getResource("resources/lights/car.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Icon iconCar = new ImageIcon(carImage);
			this.squares[x][y].setIcon(iconCar);
		}
	}
	
	public void updateLights(Map map)
	{
		
		Icon ic = getLightImage();
		
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				if (map.getTileType(i,j) == 'l')
				{
					squares[i][j].setIcon(ic);
				}
			}
		}
	}
	
	public Icon getLightImage()
	{	
		int horizontalPhase = (int) Light.getHorizontalPhase();
		int verticalPhase = (int)Light.getVerticalPhase();
		String temp = (horizontalPhase + "" + verticalPhase);
		String filename = null;
		Image file = null;
		Icon img = null;
		
		if(temp.equals("00"))
		{
			filename = "resources/lights/00.png";
		}
		else if(temp.equals("01"))
		{
			filename = "resources/lights/01.png";
		}
		else if(temp.equals("02"))
		{
			filename = "resources/lights/02.png";
		}
		else if(temp.equals("10"))
		{
			filename = "resources/lights/10.png";
		}
		else if(temp.equals("20"))
		{
			filename = "resources/lights/20.png";
		}
		
		try {
			file = ImageIO.read(getClass().getResource(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		img = new ImageIcon(file);
		
		return img;
	}
}
