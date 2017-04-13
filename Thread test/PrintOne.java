import java.util.Random;

public class PrintOne implements Runnable
{
	public PrintOne(String a)
	{
		name = a;
		time = 50;//r.nextInt(999);
	}
	
	String name;
	int time;
	//Random r;
	Random r = new Random();
	
	public void run()
	{
		try
		{
			while(true)
			{
				System.out.println(name + " is sleeping for " + time + " ms");
				Thread.sleep(time);
			}
		}
		catch(Exception e)
		{
			
		}
	}
}
