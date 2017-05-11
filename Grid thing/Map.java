import java.util.ArrayList;

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
	
	public void deleteCar(Car c)
	{
		int temp = this.cars.indexOf(c);
		this.cars.remove(temp);
		this.cars.trimToSize();
	}
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
	
	public static void carSpawn(Map map, Gui gui)
	{
		int entrances = (map.getIntersectionColumns() + map.getIntersectionRows()) * 2 - 1; //-1 added as random.nextInt() has an inclusive range (adds 1)
		Random rng = new Random();
		int columns = map.getColumns();
		int rows = map.getRows();
		int a;
		int b;
		
		while(true)
		{
			try
			{
				a = rng.nextInt(entrances);
				b = -1;
				
				while(b!=a)
				{
					//left
					for(int i=0; i<1; i++)
					{
						for(int j=5; j>=rows; j+=12)
						{
							b++;
							if(a==b)
							{
								if(map.getGrid()[i][j].getIsOccupied() == false)
								{
									map.addCar(i, j, (byte) 1, (byte) 0);
									//draw car
									break;
								}
								//spawn car here heading east
								//break
							}
						}
					}
					
					
				}
				
				
			}
			catch(Exception e)
			{
				System.out.println("Car Spawn Error");
			}
		}
		
	}
}
