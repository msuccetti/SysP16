package robot.middleware;

import common.Consts;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import robot.drivers.DcMotor;
import robot.drivers.QuadratureEncoder;

/*
 * Control a motor speed using a PID algorithm
 */
public class DcMotorSpeedPID extends Task
{
	private boolean enabled = false;// true->pid active, false otherwise
	private float position = 0;// position of motor
	private boolean positionInitialised = true;// true-> no speed measurement
												// has been performed yet
	private float timeInterval;// time interval between controller runs [s]
	private float ctrlP = 0;// p-value of the controller
	private float ctrlI = 0;// i-value of the controller
	private float ctrlD = 0;// d-value of the controller
	private DcMotor dcMotor = null;// motor to be controlled
	private QuadratureEncoder encoder = null;// encoder used
	private float targetSpeed = 0;// target speed
	private float actualSpeed = 0;// measured speed
	private float errorIntegral = 0;// integral of error
	private float errorLast = 0;// last error
	private float error; //error
	private float errorDerivative;//change of error
	private float incPWM; //new PWM increment
	private float newPWM; //new PWM value
	
	public DcMotorSpeedPID(Consts.DcMotorSpeedPIDSettings inSettings)
	{
		period = inSettings.period;
		dcMotor = inSettings.motor;
		encoder = inSettings.encoder;
		ctrlP=inSettings.P;
		ctrlI=inSettings.I;
		ctrlD=inSettings.D;
		initCtrl();
	}
	private void initCtrl()
	{
		timeInterval = (((float)period)/1000);
		Task.install(this);
	}

	// true->pid active, false otherwise
	public void enable(boolean inEnable)
	{
		enabled = inEnable;
		dcMotor.setSpeed((short)0);
	}

	// reset the position
	public void resetPosition()
	{
		System.out.println("PID position reset");
		position = 0;
		positionInitialised = true;
	}

	// set target speed
	public void setTargetSpeed(float inSpeed)
	{
		targetSpeed = inSpeed;
	}

	// get target speed
	public float getTargetSpeed()
	{
		return targetSpeed;
	}

	// get position
	public float getPosition()
	{
		return position;
	}

	// set target speed
	public float getActualSpeed()
	{
		return actualSpeed;
	}

	// set error
	public float getActualError()
	{
		return errorLast;
	}

	public void printStatus()
	{
		System.out.print("PID ts:");
		System.out.print(targetSpeed);
		System.out.print(", as:");
		System.out.print(actualSpeed);
		System.out.print(", p:");
		System.out.print(position);
		System.out.print(", e:");
		System.out.print(error);
		System.out.print("\ti:");
		System.out.print(errorIntegral);
		System.out.print(", d:");
		System.out.print(errorDerivative);
		System.out.print(", pi:");
		System.out.print(incPWM);
		System.out.print(", pv:");
		System.out.println(newPWM);
		System.out.print("    pc:");
		System.out.print(ctrlP);
		System.out.print(", ic:");
		System.out.print(ctrlI);
		System.out.print(", dc:");
		System.out.println(ctrlD);
	}

	public void action()
	{
		if (!enabled)
		{// pid off
			return;
		}
		// derive speed
		if (!positionInitialised)
		{
			position = encoder.getPosition();
			positionInitialised = true;
		}
		float oldPosition = position;
		position = encoder.getPosition();// acquire new position
		actualSpeed = (position - oldPosition) / timeInterval;
		// encoder.resetDistance();
		// derive error
		error = targetSpeed - actualSpeed;
		// track error over time, scaled to the timer interval
		errorIntegral = errorIntegral + (error / timeInterval);
		if (Float.isNaN(errorIntegral))
		{
			printStatus();
			encoder.printStatus();
			dcMotor.printStatus();
		}
		// determine the amount of change from the last time checked
		errorDerivative = (error - errorLast) * timeInterval;
		// calculate how much to drive the output in order to get to the
		// desired setpoint.
		incPWM = (ctrlP * error) + (ctrlI * errorIntegral)
				+ (ctrlD * errorDerivative);
		newPWM=dcMotor.getSpeed() + incPWM;
		// remember the error for the next time around.
		errorLast = error;
		// limit speed output
		if (Short.MAX_VALUE < newPWM)
		{
/*			printStatus();
			encoder.printStatus();
			dcMotor.printStatus();
*/			newPWM = Short.MAX_VALUE;
		}
		if (Short.MIN_VALUE > newPWM)
		{
/*			printStatus();
			encoder.printStatus();
			dcMotor.printStatus();
*/			newPWM = Short.MIN_VALUE;
		}
		// dcMotor.setSpeed((short)setPWM);
		dcMotor.setSpeed((short) newPWM);
	}
}
