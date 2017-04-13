public class TestMain
{
	public static void main(String[] args)
	{
		Thread t1 = new Thread(new PrintOne("one"));
		Thread t2 = new Thread(new PrintOne("two"));
		Thread t3 = new Thread(new PrintOne("three"));
		Thread t4 = new Thread(new PrintOne("four"));
		Thread t5 = new Thread(new PrintOne("five"));
		
		
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
		
		
	}
}
