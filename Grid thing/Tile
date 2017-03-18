public abstract class Tile
{
	public Tile()
	{
		this.isOccupied = false;
	}
	
	private boolean isOccupied;
	
	public boolean getIsOccupied()
	{
		return this.isOccupied;
	}
	
	public void setIsOccupied(boolean x)
	{
		if(x)
		{
			this.isOccupied = true;
		}
		else
		{
			this.isOccupied = false;
		}
	}
	
	public abstract boolean canMove(byte direction);
	
}
