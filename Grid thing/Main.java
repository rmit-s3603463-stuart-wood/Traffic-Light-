import java.util.Scanner;
import java.util.InputMismatchException;


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
		
		Map map = new Map(intersectionColumns, intersectionRows);
		map.print();
		
		Gui test = new Gui(map.getColumns(), map.getRows(), map);
		
		//test.setVisible(true);
	}
}
