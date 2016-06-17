package tests;

import java.io.PrintStream;

import robot.drivers.Switch;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;

/**
 * class to test the wlan adapter 
 *
 */
public class SwitchTester extends Task
{
	public static SwitchTester switchTester;
	
	private static final int switchAdress=12;//address of tested switch
	
	private static Switch testedSwitch=new Switch(switchAdress);

	public SwitchTester()
	{
		//configure console output
		SCI sci1 = SCI.getInstance(SCI.pSCI1);
		sci1.start(9600, SCI.NO_PARITY, (short) 8);
		System.out = new PrintStream(sci1.out);
		System.err = new PrintStream(sci1.out);
		System.out.println("Testing class Wlan");
	}
	
	public void action()
	{
		if (testedSwitch.get())
		{
			System.out.println("true");
		}else
		{
			System.out.println("true");
		}
	}
	
	static
	{
		try
		{
			//create new tester
			switchTester = new SwitchTester();
			switchTester.period = 1000;
			Task.install(switchTester);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
