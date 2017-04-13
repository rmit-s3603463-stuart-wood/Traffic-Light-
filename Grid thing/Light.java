public class Light extends Tile
{
	public Light(int horizontalPhase, int verticalPhase)
	{
		super();
		this.horizontalPhase = (byte) horizontalPhase;
		this.verticalPhase = (byte) verticalPhase;
	}
	
	private byte horizontalPhase;
	private byte verticalPhase;
	/* 0 - Red
	   1 - Amber
	   2 - Green
	   Vertical phase is north-to-south light colour
	   horizontal phase is east-to-west light colour */
	
	/* Check traffic light in each direction and whether the tile is occupied. 
	   Return true if a car can move here */
	public boolean canMove(byte direction)
	{
		if(this.getIsOccupied())
		{
			return false;
		}
		else
		{
			switch(direction)
			{
				case 0:
				case 2: if(this.verticalPhase == 2)
						{
							return true;
						}
						else return false;
					
				case 1:
				case 3: if(this.horizontalPhase == 2)
						{
							return true;
						}
						else return false;
				default: return false;
			}
		}
	}
	
	public void setPhase(byte horizontalPhase, byte verticalPhase)
	{
		this.horizontalPhase = horizontalPhase;
		this.verticalPhase = verticalPhase;
	}
	
	
	
	public byte getHorizontalPhase()
	{
		return this.horizontalPhase;
	}
	
	public byte getVerticalPhase()
	{
		return this.verticalPhase;
	}
}
