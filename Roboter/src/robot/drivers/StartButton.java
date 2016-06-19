package robot.drivers;

import com.sun.org.apache.xpath.internal.axes.MatchPatternIterator;

import ch.ntb.inf.deep.runtime.mpc555.driver.MPIOSM_DIO;
import common.Consts;

public class StartButton extends MPIOSM_DIO
{
	public StartButton()
	{
		// call parent contructor
		MPIOSM_DIO(Consts.Buttons.IO_BUTTON_START, false);
	}

	public boolean isPressed()
	{
		return this.get() == true;
	}
}
