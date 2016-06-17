package robot.drivers;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131;
import ch.ntb.inf.deep.runtime.mpc555.driver.RN131Config;
import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.inf.deep.runtime.util.CmdInt;
import common.Consts;

/**
 * class dealing with wlan in a high level way
 *
 */
public class Wlan extends Task
{
	public enum States
	{
		START,
		INITIALISING,
		CONNECTED,
	}
	
	/**
	 * the wlan configuration
	 */
	private RN131Config config;

	//state f state machine
	private States state=States.START;
	
	/**
	 * the wlan interface
	 */
	private RN131 wifi;

	public Wlan()
	{
		SCI sci2 = SCI.getInstance(SCI.pSCI2);
		sci2.start(115200, SCI.NO_PARITY, (short) 8);

		config = new RN131Config();
		config.in = sci2.in;
		config.out = sci2.out;
		config.reset = new MPIOSM_DIO(Consts.Wlan.WLAN_RESET_PIN, true);

		config.configure = true;
		config.ssid = Consts.Wlan.SSID;
		config.localIP = Consts.Wlan.IP_LOCAL;
		config.remoteIP = Consts.Wlan.IP_REMOTE;
		config.apMode = Consts.Wlan.MASTER;
		config.autoConnect = !Consts.Wlan.MASTER;
		config.useExternalAntenna = true;

		try
		{
			wifi = new RN131(config);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		this.reset();
		period = Consts.Wlan.PERIOD;
		Task.install(this);
	}

	public void reset()
	{
		wifi.reset();
		state=States.INITIALISING;
	}

	public WlanMessage readMessage()
	{
		WlanMessage message = new WlanMessage();
		boolean bufferEmpty = false;
		
		//if not connected we can't read
		if (States.CONNECTED != state)
		{
			return message;
		}
		
		do
		{
			CmdInt.Type type = wifi.cmd.readCmd();
			message.header = wifi.cmd.getHeader();
			message.content = wifi.cmd.getInt();
/*
			System.out.print(wifi.getState().toString());
			System.out.print(", ");
			// get last package
			System.out.print(type.toString());
			System.out.print(", ");
			Integer header = message.header;
			System.out.print(header.toString());
			System.out.print(", ");
			Integer content = message.content;
			System.out.print(content.toString());
			System.out.println();
*/
			// determine package type
			switch (type)
			{
				case Cmd:// we have a command
					message.type = WlanMessage.Type.COMMAND;
					message.valid = true;
					return message;
				case Code:// we have a code
					message.type = WlanMessage.Type.CODE;
					message.valid = true;
					return message;
				case None:// the package is is empty
					message.type = WlanMessage.Type.EMPTY;
					message.valid = true;
					return message;
				case Illegal:// the package not valid
					message.type = WlanMessage.Type.ILLEGAL;
					message.valid = true;
					bufferEmpty = true;
					return message;
				case Unknown:// the header was not a known one
					message.header = wifi.cmd.getHeader();
					return message;
			}
		}
		while (bufferEmpty != true);
		bufferEmpty = false;
		// case not recognised-> return invalid message
		message.valid = false;
		return message;
	}

	public void sendCmd(int command)
	{
		if (connected())
		{
			wifi.cmd.writeCmd(command);
		}
	}

	public void sendCode(int code)
	{
		if (connected())
		{
			wifi.cmd.writeCmd(CmdInt.Type.Code, code);
		}
	}

	public void sendOther(int type, int content)
	{
		if (connected())
		{
			wifi.cmd.writeCmd((byte) type, content);
		}
	}

	/**
	 * test, if the wifi is connected
	 * 
	 * @return
	 */
	public boolean connected()
	{
		return wifi.connected();
	}
	
	
	//wLan monitoring
	public void action()
	{
		//try to reconnect if connection is broken
		if (!this.connected() && States.INITIALISING != state)
		{
			this.reset();
			state=States.INITIALISING;
		}
	}
}
