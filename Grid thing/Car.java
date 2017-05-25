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
	
	public boolean move(Map map)
	{
		int nextX = 0;
		int nextY = 0;
		switch (direction)
		{
		//north: to head up, y = y-1
		case 0:
			nextX = x;
			nextY = y - 1;
			break;
		//east: to head right, x =x+1
		case 1:
			nextX = x + 1;
			nextY = y;
			break;
		//south: to head down, y = y+1
		case 2:
			nextX = x;
			nextY = y + 1;
			break;
		//west: to head left, x = x-1
		case 3:
			nextX = x - 1;
			nextY = y;
			break;
		default: 
			break;
		}
		
		if (nextX < 0 || nextX >= map.getColumns() || nextY < 0 || nextY >= map.getRows())
		{
			return false;
		}
		
		if (map.getGrid()[nextX][nextY].getIsOccupied() == false)
		{
			if (map.getTileType(nextX, nextY) == 'l')
			{
				if (direction == 0 || direction == 2)
				{
					if (Light.getHorizontalPhase() == (byte) 2 || Light.getHorizontalPhase() == (byte) 1)
					{
						map.getGrid()[x][y].setIsOccupied(false);
						x = nextX;
						y = nextY;
						map.getGrid()[x][y].setIsOccupied(true);
					}
				}
				else
				{
					if (Light.getVerticalPhase() == (byte) 2 || Light.getVerticalPhase() == (byte) 1)
					{
						map.getGrid()[x][y].setIsOccupied(false);
						x = nextX;
						y = nextY;
						map.getGrid()[x][y].setIsOccupied(true);
					}
				}
			}
			else
			{
				map.getGrid()[x][y].setIsOccupied(false);
				x = nextX;
				y = nextY;
				map.getGrid()[x][y].setIsOccupied(true);
			}
		}
		return true;
	}
}
