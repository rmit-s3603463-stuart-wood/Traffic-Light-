import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.Random;


public class Main
{

	public static void main(String[] args) throws InputMismatchException
	{	
		Scanner keyboard = new Scanner(System.in);
		
		int intersectionColumns = 0;
		int intersectionRows = 0;
		int frequency = 0;
		int greenLightTime = 0;
		
		while(!(intersectionColumns >= 1 && intersectionColumns <= 10))
		{
			System.out.print("Enter the number of columns of intersections:");
			try
			{
				intersectionColumns = keyboard.nextInt();
				
				if(!(intersectionColumns >= 1 && intersectionColumns <= 10))
				{
					System.out.println("Please enter an integer from 1 to 10\n");
				}
			}
			catch(InputMismatchException e)
			{
				System.out.println("Invalid input - enter an integer from 1 to 10\n");
				keyboard.next();
			}
		}
		
		while(!(intersectionRows >= 1 && intersectionRows <= 10) | (intersectionColumns * intersectionRows > 10))
		{
			System.out.print("\nEnter the number of rows of intersections:");
			try
			{
				intersectionRows = keyboard.nextInt();
				
				if(!(intersectionRows >= 1 && intersectionRows <= 10))
				{
					System.out.println("Please enter an integer from 1 to 10\n");
				}
				else if(intersectionColumns * intersectionRows > 10)
				{
					System.out.println("Number of intersections cannot exceed 10,");
					System.out.println("Enter a smaller number of rows");
				}
			}
			catch(InputMismatchException e)
			{
				System.out.println("Invalid input - enter an integer from 1 to 10");
				keyboard.next();
			}
		}
		
		while(greenLightTime < 1)
		{
			System.out.print("\nEnter the duration for green lights:");
			try
			{
				greenLightTime = keyboard.nextInt();
				
				if(greenLightTime < 1)
				{
					System.out.println("Duration must be 1 second or greater");
				}
			}
			catch(InputMismatchException e)
			{
				System.out.println("Invalid input - enter an integer greater than 0");
				keyboard.next();
			}
		}
		
		Map map = new Map(intersectionColumns, intersectionRows);
		map.print();
		
		Gui test = new Gui(map.getColumns(), map.getRows(), map);
		
		//test.testTiles();
		boolean lastGreenLightHorizontal = true;
	 	int timeTaken = 0;
	 	final int AMBER_LIGHT_TIME = 2;
		//timeTaken%frequency: every (frequency) seconds, call this method
		do
		{
			//spawn car every carFrequency seconds
			if (timeTaken%carFrequency == 0)
			{
				map.carSpawn();
			}
			//change lights to amber every "greenlighttime - amberlighttime" seconds
			if ((timeTaken+AMBER_LIGHT_TIME)%lightFrequency == 0)
			{
				map.greenToAmber();
				test.updateLights(map);
			}
			//change lights from green cycle to red cycle and vice versa
			if (timeTaken%lightFrequency == 0)
			{
				lastGreenLightHorizontal = map.lightSwitch(lastGreenLightHorizontal);
				test.updateLights(map);
			}
			//move cars
			try
			{
				for (int i = 0; i < map.getCars().size(); i++)
				{
					if (map.getCars().get(i).move(map))
					{
						System.out.println("Car " + i + " moved.");
					}
					else
					{
						System.out.println("Car " + i + " has left road.");
						map.deleteCars();
					}
				}

			} catch (IndexOutOfBoundsException e)
			{
				System.out.println(e);
			}
			//make one second pass
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeTaken ++;
			System.out.println(timeTaken + " seconds.");
			//when first car is deleted, save timeTaken and print at end with statistics
		}
		while(true); //while user does not cancel simulation
		
	}
	
	/*
	public static void carSpawn(Map map, Gui gui)
	{
		int entrances = (map.getIntersectionColumns() + map.getIntersectionRows()) * 2;
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
	*/

	public static void lightCycle(int greenLightTime, Map map, Gui test)
	{
		int rows = map.getRows();
		int columns = map.getColumns();
		
		while(true)
		{
			//green, red
			for(int i=0; i<columns; i++)
			{
				for(int j=0; j<rows; j++)
				{
					if (map.getGrid()[i][j] instanceof Light)
					{
						System.out.println("GREEN-RED " + i +", " + j);
						
						Light light = (Light) map.getGrid()[i][j];
						light.setPhase((byte) 2,(byte) 0);
						test.update(i, j, light);
					}
				}
			}
			try
			{
				Thread.sleep(greenLightTime * 1000);
			}
			catch(Exception e)
			{
				System.out.println("Exception");
			}
			//amber, red
			for(int i=0; i<columns; i++)
			{
				for(int j=0; j<rows; j++)
				{
					if (map.getGrid()[i][j] instanceof Light)
					{
						System.out.println("AMBER-RED " + i +", " + j);
						
						Light light = (Light) map.getGrid()[i][j];
						light.setPhase((byte) 1,(byte) 0);
						test.update(i, j, light);
					}
				}
			}
			try
			{
				Thread.sleep(5000);
			}
			catch(Exception e)
			{
				System.out.println("Exception");
			}
			//red, red
			for(int i=0; i<columns; i++)
			{
				for(int j=0; j<rows; j++)
				{
					if (map.getGrid()[i][j] instanceof Light)
					{
						System.out.println("RED-RED " + i +", " + j);
						
						Light light = (Light) map.getGrid()[i][j];
						light.setPhase((byte) 0,(byte) 0);
						test.update(i, j, light);
					}
				}
			}
			try
			{
				Thread.sleep(5000);
			}
			catch(Exception e)
			{
				System.out.println("Exception");
			}
			//red, green
			for(int i=0; i<columns; i++)
			{
				for(int j=0; j<rows; j++)
				{
					if (map.getGrid()[i][j] instanceof Light)
					{
						System.out.println("RED-GREEN " + i +", " + j);
						
						Light light = (Light) map.getGrid()[i][j];
						light.setPhase((byte) 0,(byte) 2);
						test.update(i, j, light);
					}
				}
			}
			try
			{
				Thread.sleep(greenLightTime * 1000);
			}
			catch(Exception e)
			{
				System.out.println("Exception");
			}
			//red, amber
			for(int i=0; i<columns; i++)
			{
				for(int j=0; j<rows; j++)
				{
					if (map.getGrid()[i][j] instanceof Light)
					{
						System.out.println("RED-AMBER " + i +", " + j);
						
						Light light = (Light) map.getGrid()[i][j];
						light.setPhase((byte) 0,(byte) 1);
						test.update(i, j, light);
					}
				}
			}
			try
			{
				Thread.sleep(5000);
			}
			catch(Exception e)
			{
				System.out.println("Exception");
			}
			//red, red
			for(int i=0; i<columns; i++)
			{
				for(int j=0; j<rows; j++)
				{
					if (map.getGrid()[i][j] instanceof Light)
					{
						System.out.println("RED-RED " + i +", " + j);
						
						Light light = (Light) map.getGrid()[i][j];
						light.setPhase((byte) 0,(byte) 0);
						test.update(i, j, light);
					}
				}
			}
			try
			{
				Thread.sleep(5000);
			}
			catch(Exception e)
			{
				System.out.println("Exception");
			}
			
		}
	}
}
