import java.util.ArrayList;
import java.util.Random;

public class Map
{
	public Map(int intersectionColumns, int intersectionRows)
	{
		
		this.cars = new ArrayList<Car>();
		
		this.intersectionColumns = intersectionColumns;
		this.intersectionRows = intersectionRows;
		
		columns = calculateDimensions(intersectionColumns);
		rows = calculateDimensions(intersectionRows);
		
		this.grid = new Tile[columns][rows];
		
		/*Add Roads*/
		for(int i=0; i<columns; i++)
		{
			if((i % 12 == 5)|(i % 12 == 6))
			{
				for(int j=0; j<rows; j++)
				{
					this.grid[i][j] = new Road();
				}
			}
			else
			{
				for(int j=5; j<rows; j++)
				{
					this.grid[i][j] = new Road();
					
					if(j % 2 == 0)
					{
						j+=10;
					}
				}
			}
		}
		
		/*Add Lights*/
		for(int i=5; i<columns; i++)
		{
			for(int j=5; j<rows; j++)
			{
				this.grid[i][j] = new Light(2, 0);
				
				if(j % 2 == 0)
				{
					j+=10;
				}
			}
			
			if(i % 2 == 0)
			{
				i+=10;
			}
		}
	}
	
	private Tile[][] grid;
	private ArrayList<Car> cars;
	
	private int columns;
	private int rows;
	
	private int intersectionColumns;
	private int intersectionRows;
	int carsDeleted = 0;
	
	private int calculateDimensions(int x)
	{
		/*For each x, where x is the number of roads, north to south or east to west,
		  the length of the road perpendicular is 5 on each side of the road (+10), + 10 between
		  each intersection (10*(x-1)), while each intersection takes up 2 units (x*2)
		*/
		return (x * 2) + 10 * (x - 1) + 10;
	}
	
	public void print()
	{
		/*Print road configuration*/
		System.out.print("|  |");
		for(int i=0; i<columns; i++)
		{
			if(i<10)
			{
				System.out.print(" ");
			}
			System.out.print(i + "|");
		}
		System.out.println();
		
		for(int j=0; j<rows; j++)
		{
			if(j<10)
			{
				System.out.print("| " + j + "|");
			}
			else
			{
				System.out.print("|" + j + "|");
			}
			
			for(int i=0; i<columns; i++)
			{
				if(this.grid[i][j] instanceof Road)
				{
					System.out.print("Rd|");
				}
				else if(this.grid[i][j] instanceof Light)
				{
					System.out.print("Li|");
				}
				else
				{
					System.out.print("  |");
				}
			}
			System.out.println();
		}
	}
	
	public int getColumns()
	{
		return this.columns;
	}
	
	public int getRows()
	{
		return this.rows;
	}
	
	public int getIntersectionColumns()
	{
		return this.intersectionColumns;
	}
	
	public int getIntersectionRows()
	{
		return this.intersectionRows;
	}
	
	public char getTileType(int x, int y)
	{
		if(this.grid[x][y] instanceof Road)
		{
			return 'r';
		}
		else if(this.grid[x][y] instanceof Light)
		{
			return 'l';
		}
		else return 'b';
	}
	
	public Tile[][] getGrid()
	{
		return this.grid;
	}
	
	public Tile getTile(int x, int y)
	{	
		return this.grid[x][y];
	}
	
	public ArrayList<Car> getCars()
	{
		return this.cars;
	}
	
	public void addCar(int x, int y, byte direction, byte colour)
	{
		this.cars.add(new Car(x, y, direction, colour));
	}
	
	public void deleteCars(Gui gui)
	{
		for (int i = 0; i < cars.size(); i++)
		{
			if (cars.get(i).getX() == 0 || cars.get(i).getX() == columns-1 || cars.get(i).getY() == 0 || cars.get(i).getY() == rows-1)
			{
				int x = cars.get(i).getX();
				int y = cars.get(i).getY();
				cars.remove(i);
				this.grid[x][y].setIsOccupied(false);//make the coordinate empty
				gui.removeCarIcon(x, y); //delete car image
				this.carsDeleted++;
				i = 0;
			}
		}
	}
	
	public void displayLeftCars(){
		System.out.println("Number of Cars Passed: " + carsDeleted);
	}
	
	/*
	public void forceLightRed(){
		for(int i=0; i<columns; i++)
		{
			for(int j=0; j<rows; j++)
			{
				if (this.grid[i][j] instanceof Light){
					Light light = (Light) this.grid[i][j];
					light.setPhase((byte) 0, (byte) 0);
				}
			}
			
		}
	}
	*/
	
	public boolean lightSwitch(boolean lastGreenLightHorizontal)
	{
		//cycle through tiles until a light is reached
		for(int i=0; i<columns; i++)
		{
			for(int j=0; j<rows; j++)
			{
				//if this light is amber-red (1,0) or red-amber (0,1), switch to red-red (0,0)
				//if this light is red-red (0,0), switch to red-green (0,2), or green-red (2,0) as appropriate
				if (this.grid[i][j] instanceof Light)
				{
					Light light = (Light) this.grid[i][j];
					if (light.getHorizontalPhase() == (byte) 1 && light.getVerticalPhase() == (byte) 0)
					{
						light.setPhase((byte) 0, (byte) 0);
						System.out.println("Light: [" + i + "][" + j + "]: Phase: " + light.getHorizontalPhase() + ", " + light.getVerticalPhase());
						return true;
					} 
					else if (light.getHorizontalPhase() == (byte) 0 && light.getVerticalPhase() == (byte) 1)
					{
						light.setPhase((byte) 0, (byte) 0);
						System.out.println("Light: [" + i + "][" + j + "]: Phase: " + light.getHorizontalPhase() + ", " + light.getVerticalPhase());
						return false;
					}
					else if (light.getHorizontalPhase() == (byte) 0 && light.getVerticalPhase() == (byte) 0)
					{
						if (lastGreenLightHorizontal)
						{
							light.setPhase((byte) 0, (byte) 2);
							System.out.println("Light: [" + i + "][" + j + "]: Phase: " + light.getHorizontalPhase() + ", " + light.getVerticalPhase());
							return false;
						}
						else
						{
							light.setPhase((byte) 2, (byte) 0);
							System.out.println("Light: [" + i + "][" + j + "]: Phase: " + light.getHorizontalPhase() + ", " + light.getVerticalPhase());
							return true;
						}
						
					}
				}
			}
		}
		return lastGreenLightHorizontal;
	}
	
	//switch green lights to amber lights
	public void greenToAmber()
	{
		for (int i = 0; i < columns; i++)
		{
			for (int j = 0; j < rows; j++)
			{
				if (grid[i][j] instanceof Light)
				{
					Light light = (Light) grid[i][j];
					if (light.getHorizontalPhase() == (byte) 2)
					{
						light.setPhase((byte) 1, (byte) 0); 
						System.out.println("Light: [" + i + "][" + j + "]: Phase: " + light.getHorizontalPhase() + ", " + light.getVerticalPhase());
					}
					else if (light.getVerticalPhase() == (byte) 2)
					{
						light.setPhase((byte) 0, (byte) 1);
						System.out.println("Light: [" + i + "][" + j + "]: Phase: " + light.getHorizontalPhase() + ", " + light.getVerticalPhase());
					}
				}
			}
		}
	}
	public void carSpawn()
	{
		//seed the random number generator with another random number
		Random seed = new Random();
		int entrances = (intersectionColumns + intersectionRows) * 2;
		Random rng = new Random(seed.nextLong());
		int columns = this.columns;
		int rows = this.rows;
		
		byte direction = -1;
		int startPoint, startX = -1, startY = -1;
		int southEntrances = intersectionColumns;
		int westEntrances = intersectionColumns + intersectionRows;
		int northEntrances = (2 * intersectionColumns) + intersectionRows;
		int eastEntrances = entrances;
		
		
			try
			{
				startPoint = rng.nextInt(entrances);

				if (startPoint < southEntrances)
				{
					direction = 0; //car will be heading north on this road
					System.out.println("Car spawning at south entrance: " + startPoint);
					startPoint = (12 *(startPoint)) + 5;
					startX = startPoint;
					startY = rows - 1;
				}
				else if (startPoint < westEntrances)
				{
					direction = 1; //car will be heading east on this road
					startPoint -= southEntrances;
					System.out.println("Car spawning at west entrance: " + startPoint);
					startPoint = (12 *(startPoint)) + 5;
					startX = 0;
					startY = startPoint;
				}
				else if (startPoint < northEntrances)
				{
					direction = 2; //car will be heading south on this road
					startPoint -= westEntrances;
					System.out.println("Car spawning at north entrance: " + startPoint);
					startPoint = (12 *(startPoint)) + 6; //6 cause its driving on the other side of the road
					startX = startPoint;
					startY = 0;
				}
				else if (startPoint <= eastEntrances)
				{
					direction = 3; //car will be heading west on this road
					startPoint -= northEntrances;
					System.out.println("Car spawning at east entrance: " + startPoint);
					startPoint = (12 *(startPoint)) + 6; //6 cause its driving on the other side of the road
					startX = columns - 1;
					startY = startPoint;
				}
				
				if (grid[startX][startY].getIsOccupied() == false)
				{
					addCar(startX,startY,direction,(byte)2);
					grid[startX][startY].setIsOccupied(true);
				}
				else
				{
					System.out.println("Car Spawn Error: road is probably full at this entrance");
					System.out.println("Entrance: [" + startX + "][" + startY + "]");
				}
				
			}
			catch(Exception e)
			{
				System.out.println(e);
				System.out.println("Dimensions: " + columns + ", " + rows);
				System.out.println("Start point: " +startX + ", " + startY);
			}
	}
}
