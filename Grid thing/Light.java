package traffic;

public class Light extends Tile
{
	public Light(int horizontalPhase, int verticalPhase)
	{
		super();
		horizontalPhase = (byte) horizontalPhase;
		verticalPhase = (byte) verticalPhase;
	}
	
	private static byte horizontalPhase;
	private static byte verticalPhase;
	/* 0 - Red
	   1 - Amber
	   2 - Green */
	
	public boolean canMove(byte direction)
	{
		if(this.getIsOccupied())
		{
			return false;
		}
		else
		{
			if (direction == 0 || direction == 2)
			{
				if (verticalPhase == 2)
				{
					return true;
				}
				else
				{
					return false;
				}
			} 
			else
			{
				if (horizontalPhase == 2)
				{
					return true;
				}
				else
				{
					return false;
				}
			}
		}
	}
	
	public static void setPhase(byte horizontalPhase, byte verticalPhase)
	{
		Light.horizontalPhase = horizontalPhase;
		Light.verticalPhase = verticalPhase;
	}
	
	public static byte getHorizontalPhase()
	{
		return horizontalPhase;
	}
	
	public static byte getVerticalPhase()
	{
		return verticalPhase;
	}
}
