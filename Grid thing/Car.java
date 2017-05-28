public class Car
{
	public Car(int x, int y, byte direction, byte colour)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.colour = 0;
	}
	
	private int x;
	private int y;
	private byte direction;
	/* 0 - north
	   1 - east
	   2 - south
	   3 - west */
	private byte colour;
	
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
	
	public boolean move(Map map, Gui gui)
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
		
		if (map.getGrid()[nextX][nextY].getIsOccupied() == false)//if the next tile is empty
		{
			if(map.getTileType(x, y) == 'l')//if the car is on a light tile
			{
				/*if the car is on a light tile, and the next tile is empty,
				 we always want to move to the next tile (even if the light is
				 amber or red) or else the car gets stuck on the intersection,
				 blocking other lanes and increasing the chance of gridlock*/
				map.getGrid()[x][y].setIsOccupied(false);
				x = nextX;
				y = nextY;
				map.getGrid()[x][y].setIsOccupied(true);
			}
			else if (map.getTileType(nextX, nextY) == 'l')
			{
				if (direction == 0 || direction == 2)
				{
					if (Light.getVerticalPhase() == (byte) 2)
					{
						map.getGrid()[x][y].setIsOccupied(false);
						x = nextX;
						y = nextY;
						map.getGrid()[x][y].setIsOccupied(true);
					}
				}
				else
				{
					if (Light.getHorizontalPhase() == (byte) 2)
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
			gui.updateCar(x, y, (int) direction, map);
		}
		return true;
	}
}
