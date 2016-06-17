package tests;

import java.io.PrintStream;

import robot.drivers.Wlan;
import robot.drivers.WlanMessage;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

/**
 * class to test the wlan adapter 
 *
 */
public class WlanTester extends Task
{
	private static Wlan wlan;//wlan adapter
	private static int content;//for changing messages to wlan

	public WlanTester() throws Exception
	{
		content=0;
		//initialise wlan
		wlan = new Wlan();
		
		//configure console output
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Testing class Wlan");
	}
	
	public void action()
	{
		if (!wlan.connected())
		{//wlan not connected
			System.out.println("(not connected)");
			return;
		}
		//connected -> get message
		WlanMessage message=wlan.readMessage();
		switch (message.type)
		{//evaluate type of message
			case COMMAND:
				System.out.print("Command: ");
				break;
			case CODE:
				System.out.print("Code: ");
				break;
			case EMPTY:
				System.out.print("empty message: ");
				break;
			default:
				System.out.print("Unknown or illegal: header:");
				break;
		}
		Integer msgContent=message.content;
		System.out.println(msgContent.toString());
		//send message
		wlan.sendCode(content);
		wlan.sendCmd(content++);
	}

	/**
	 * initialises the tester
	 */
	static
	{
		WlanTester tester;
		content=0;
		try
		{
			//create new tester
			tester = new WlanTester();
			tester.period = 1000;
			Task.install(tester);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
