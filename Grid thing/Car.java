public class Car
{
	public Car(int x, int y, byte direction, byte colour)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.colour = colour;
	}
	
	private int x;
	private int y;
	private byte direction;
	/* 0 - north
	   1 - east
	   2 - south
	   3 - west */
	private byte colour;
	/* 0 - red
	   1 - blue
	   2 - green
	   3 - yellow
	   4 - pink
	   5 - purple */
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public byte getDirection()
	{
		return this.direction;
	}
	
	public byte getColour()
	{
		return this.colour;
	}
	
	public void move(Tile[][] grid, Map map, Gui gui)
	{
		switch (this.direction)
		{
			case 0: this.y--;
					grid[this.x][this.y].setIsOccupied(true);
					grid[this.x][this.y + 1].setIsOccupied(false);
					break;
			case 1: this.x++;
					grid[this.x][this.y].setIsOccupied(true);
					grid[this.x - 1][this.y].setIsOccupied(false);
					break;
			case 2: this.y++;
					grid[this.x][this.y].setIsOccupied(true);
					grid[this.x][this.y - 1].setIsOccupied(false);
					break;
			case 3: this.x--;
					grid[this.x][this.y].setIsOccupied(true);
					grid[this.x + 1][this.y].setIsOccupied(false);
					break;
			default: break;
		}
		
		gui.update(this, map);
	}
}
