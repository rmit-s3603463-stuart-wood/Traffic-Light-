import java.util.Scanner;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.InputMismatchException;


public class Main
{
	static boolean user_cancel = false;

	public static void main(String[] args) throws InputMismatchException
	{	
		Scanner keyboard = new Scanner(System.in);
		int intersectionColumns = 0;
		int intersectionRows = 0;
		int carFrequency = 0;
		int lightFrequency = 0;
		
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
			try{
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
		
		while(lightFrequency < 6) //minimum three as amber lights take 2 seconds: must have minimum 1 second of green lights
		{
			System.out.print("\nEnter the duration for green lights in seconds (> 5 seconds):");
			try
			{
				lightFrequency = keyboard.nextInt();
				
				if(lightFrequency < 1)
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
		
		while (carFrequency < 1)
		{
			System.out.print("\nEnter the frequency of car appearances: ");
			try
			{
				carFrequency = keyboard.nextInt();
				
				if (carFrequency < 1)
				{
					System.out.println("Cars cannot spawn more than once per second.");
				}
			}
			catch (InputMismatchException e)
			{
				System.out.println("Invalid input - enter an input greater than 0.");
				keyboard.next();
			}
		}
		
		Map map = new Map(intersectionColumns, intersectionRows);
		map.print();
		
		Gui gui = new Gui(map.getColumns(), map.getRows(), map);
		keyboard.close();
		boolean lastGreenLightHorizontal = true;
		boolean lastSwitchToRed = true;
	 	int timeTaken = 1;
	 	final int AMBER_LIGHT_TIME = 5;
	 	lightFrequency+=AMBER_LIGHT_TIME;
	 	final int RED_LIGHT_TIME = 3;
	 	//timeTaken%frequency: every (frequency) seconds, call this method
		do
		{
			//spawn car every carFrequency seconds
			if (timeTaken%carFrequency == 0)
			{
				map.carSpawn();
			}
			//change lights from green cycle to red cycle and vice versa
			if((timeTaken-RED_LIGHT_TIME)%lightFrequency == 0 && !lastSwitchToRed)
			{
				lastGreenLightHorizontal = map.lightSwitch(lastGreenLightHorizontal);
				gui.updateLights(map);
				lastSwitchToRed = true;
			}
			if (timeTaken%lightFrequency == 0 && lastSwitchToRed)
			{
				lastGreenLightHorizontal = map.lightSwitch(lastGreenLightHorizontal);
				gui.updateLights(map);
				lastSwitchToRed = false;
			}
			//change lights to amber every "greenlighttime - amberlighttime" seconds
			if ((timeTaken+AMBER_LIGHT_TIME)%lightFrequency == 0)
			{
				map.greenToAmber();
				gui.updateLights(map);
			}
			//move cars
			try
			{
				for (int i = 0; i < map.getCars().size(); i++)
				{
					if (map.getCars().get(i).move(map, gui))
					{
						//System.out.println("Car " + i + " moved.");
					}
					else
					{
						System.out.println("Car " + i + " has left road.");
						map.deleteCars(gui);
					}
				}
				//gui.updateCars(map);

			} catch (IndexOutOfBoundsException e)
			{
				e.printStackTrace();
			}
			//make one second pass
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			timeTaken ++;
			//System.out.println(timeTaken + " seconds.");
			//when first car is deleted, save timeTaken and print at end with statistics
		}
		while(user_cancel != true); //while user does not cancel simulation
		
		if(user_cancel == false){
			System.out.println("Time lapsed: " + timeTaken);
			map.displayLeftCars();
		}
	}
	
	public static void IsKeyPressed (KeyEvent e){
		e.getKeyCode();
	}
	
	
}
