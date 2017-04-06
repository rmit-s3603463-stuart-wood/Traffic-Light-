public class Map
{
	public Map(int intersectionColumns, int intersectionRows)
	{
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
	
	private int columns;
	private int rows;
	
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
		for(int j=0; j<rows; j++)
		{
			for(int i=0; i<columns; i++)
			{
				if(this.grid[i][j] instanceof Road)
				{
					System.out.print("R");
				}
				else if(this.grid[i][j] instanceof Light)
				{
					System.out.print("T");
				}
				else
				{
					System.out.print(" ");
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
}
