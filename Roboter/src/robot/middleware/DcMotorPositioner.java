package robot.middleware;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import robot.drivers.QuadratureEncoder;
import common.Consts.DcMotors;

public class DcMotorPositioner extends Task
{
	public enum States
	{
		FORWARD,
		BACKWARD,
		REACHED,
	}

	// Settings
	private boolean enabled = false;// true->positioner active, false otherwise
	private float timeInterval;// time interval of controller
	private DcMotorSpeedPID pidCtrl;
	private float targetPosition;
	private float targetSpeed;

	// local values
	private float actualPosition;
	private float deltaV; // possible change in speed for one iteration of the
							// action() method
	private float accel;// maximum acceleration [mm/s^2]
	
	private States state;//states of the positioner

	// constructor
	public DcMotorPositioner(float inPeriod, DcMotorSpeedPID inPid, float inAccel)
	{
		accel = inAccel;
		pidCtrl = inPid;
		actualPosition= inPid.getPosition();
		state=States.REACHED;
		period = (int) ((float) inPeriod * 1000.0);
		Task.install(this);
		timeInterval = (float) 1.0 / ((float) inPeriod);
		deltaV = accel / timeInterval;// calculate possible speed change
	}
	
	//set new target values
	public void setTarget(float inTargetPosition, float inTargetSpeed)
	{
		targetPosition = inTargetPosition;
		targetSpeed = inTargetSpeed;
		//determione direction of travel
		if(0>inTargetPosition-actualPosition)
		{
			state=States.BACKWARD;
		}else
		{
			state=States.FORWARD;
		}
	}

	public void printStatus()
	{
		System.out.print("POS tp:");
		System.out.print(targetPosition);
		System.out.print(", ts:");
		System.out.print(targetSpeed);
		System.out.print(", ta:");
		System.out.print(actualPosition);
		System.out.print(", dv:");
		System.out.print(deltaV);
		System.out.print(", st:");
		System.out.print(state.toString());
		System.out.print(", en:");
		System.out.println(enabled);
	}

	// true->positioner active, false otherwise
	public void enable(boolean inEnable)
	{
		enabled = inEnable;
		pidCtrl.enable(inEnable);
	}
	
	public float getTarget()
	{
		return targetPosition;
	}
	
	public boolean isReached()
	{
		if(States.REACHED==state)
		{
			return true;
		}else
		{
			return false;
		}
	}

	public void action()
	{
		if ((!enabled) || (States.REACHED==state))
		{// positioner inactive
			return;
		}

		float positionDifference = targetPosition - actualPosition;

		actualPosition = pidCtrl.getPosition();
		// System.out.print("p:"); System.out.print(actualPosition);
		// System.out.print("d:"); System.out.print(positionDifference);

		float nextTargetSpeed = 0;
		float actualSpeed = pidCtrl.getTargetSpeed();
		boolean backward;

		// System.out.print("s:"); System.out.print(actualSpeed);
		// test travel direction
		if (0 <= positionDifference)
		{
			backward = false;
			if(States.BACKWARD==state)
			{
//				printStatus();
//				pidCtrl.printStatus();
				state=States.REACHED;
				pidCtrl.setTargetSpeed(0);
				return;
			}
		}
		else
		{
			backward = true;
			if(States.FORWARD==state)
			{
//				printStatus();
//				pidCtrl.printStatus();
				state=States.REACHED;
				pidCtrl.setTargetSpeed(0);
				return;
			}
		}

		actualSpeed = Math.abs(actualSpeed);
		// System.out.print(", b:"); System.out.print(backward);

		// This is the speed we would like to have
		nextTargetSpeed = targetSpeed;

		// try to accelerate
		if (actualSpeed < targetSpeed)
		{
			nextTargetSpeed = actualSpeed + deltaV;
			if (targetSpeed < nextTargetSpeed)
			{
				nextTargetSpeed = targetSpeed;
			}
		}

		float vMax = (float) Math
				.sqrt(2 * Math.abs(positionDifference * accel));
		if (vMax < nextTargetSpeed)
		{// we need to brake
			nextTargetSpeed = vMax;
		}

		if (backward)
		{
			nextTargetSpeed = -nextTargetSpeed;
		}
		
		pidCtrl.setTargetSpeed(nextTargetSpeed);
	}
}
