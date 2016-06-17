package robot.middleware;

import common.Consts;
import tests.AllObjects;

/**
 * Drives the robot for certain distances in certain directions or turns a
 * certain angle
 *
 */

public class DriveInDirections
{
	public enum States
	{
		FORWARD, // forward
		FORWARD_RIGHT, // forward with right component
		FORWARD_LEFT, // forward with left component
		BACKWARD, // backward
		BACKWARD_RIGHT, // backward with right component
		BACKWARD_LEFT, // backward with left component
		LEFT_FORWARD, // left with forward component
		LEFT_BACKWARD, // left with backward component
		LEFT, // left
		RIGHT, // right
		RIGHT_FORWARD, // right with forward component
		RIGHT_BACKWARD, // right with backward component
		TURN_RIGHT, // turn right
		TURN_LEFT,// turn left
		STOP//HALT
	}
	
	private final static float SIDEWAYS_COMPONENT = (float)0.5;

	private final static int INDEX_VL = 0; // local index of VL motor pid ctrl
	private final static int INDEX_VR = 1; // local index of VR motor pid ctrl
	private final static int INDEX_HL = 2; // local index of HL motor pid ctrl
	private final static int INDEX_HR = 3; // local index of HR motor pid ctrl

	DcMotorSpeedPID drives[] =
	{ AllObjects.pidControllers[Consts.DcMotorSpeedPID.PID_VL],// VL
			AllObjects.pidControllers[Consts.DcMotorSpeedPID.PID_VR],// VR
			AllObjects.pidControllers[Consts.DcMotorSpeedPID.PID_HL],// HL
			AllObjects.pidControllers[Consts.DcMotorSpeedPID.PID_HR] // HR
	};

	public DriveInDirections()
	{
		enable(false);//stop all drives
	};
	
	public void enable(boolean inEnable)
	{
		for (int driveCount = 0; 4 > driveCount; driveCount++)
		{
			drives[driveCount].enable(inEnable);
		}
	}

	public void setDrive(DriveInDirections.States inState, float inSpeed)
	{
		switch (inState)
		{
			case BACKWARD_LEFT:
				setBL(inSpeed);
				break;
			case BACKWARD_RIGHT:
				setBR(inSpeed);
				break;
			case FORWARD_LEFT:
				setFL(inSpeed);
				break;
			case FORWARD_RIGHT:
				setFR(inSpeed);
				break;
			case LEFT:
				setL(inSpeed);
				break;
			case LEFT_FORWARD:
				setLF(inSpeed);
				break;
			case LEFT_BACKWARD:
				setLB(inSpeed);
				break;
			case RIGHT:
				setR(inSpeed);
				break;
			case RIGHT_FORWARD:
				setRF(inSpeed);
				break;
			case RIGHT_BACKWARD:
				setRB(inSpeed);
				break;
			case STOP:
				stopDrive();
				break;
			case TURN_LEFT:
				setTL(inSpeed);
				break;
			case TURN_RIGHT:
				setTR(inSpeed);
				break;
			case BACKWARD:
				setB(inSpeed);
				break;
			case FORWARD:
				setF(inSpeed);
				break;
		}
	}
	
	public void stopDrive()
	{
		//stop all drives
		for (int driveCount = 0; 4 > driveCount; driveCount++)
		{
			drives[driveCount].enable(false);
		}
	}
	
	//drive forward
	private void setF(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(inSpeed);
		drives[INDEX_VR].setTargetSpeed(inSpeed);
		drives[INDEX_HL].setTargetSpeed(inSpeed);
		drives[INDEX_HR].setTargetSpeed(inSpeed);
	}

	//drive forward with right component
	private void setFR(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_VR].setTargetSpeed(inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HL].setTargetSpeed(inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HR].setTargetSpeed(inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
	}

	//drive forward with left component
	private void setFL(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_VR].setTargetSpeed(inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HL].setTargetSpeed(inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HR].setTargetSpeed(inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
	}

	//drive backwards
	private void setB(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(-inSpeed);
		drives[INDEX_VR].setTargetSpeed(-inSpeed);
		drives[INDEX_HL].setTargetSpeed(-inSpeed);
		drives[INDEX_HR].setTargetSpeed(-inSpeed);
	}

	//drive backwards with right component
	private void setBR(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(-inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_VR].setTargetSpeed(-inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HL].setTargetSpeed(-inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HR].setTargetSpeed(-inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
	}

	//drive backwards with left component
	private void setBL(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(-inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_VR].setTargetSpeed(-inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HL].setTargetSpeed(-inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HR].setTargetSpeed(-inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
	}

	//drive left
	private void setL(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(-inSpeed);
		drives[INDEX_VR].setTargetSpeed(inSpeed);
		drives[INDEX_HL].setTargetSpeed(inSpeed);
		drives[INDEX_HR].setTargetSpeed(-inSpeed);
	}

	//drive left with forward component
	private void setLF(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(-inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_VR].setTargetSpeed(inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HL].setTargetSpeed(inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HR].setTargetSpeed(-inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
	}

	//drive left with backwards forward component
	private void setLB(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(-inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_VR].setTargetSpeed(inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HL].setTargetSpeed(inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HR].setTargetSpeed(-inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
	}

	//drive right
	private void setR(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(inSpeed);
		drives[INDEX_VR].setTargetSpeed(-inSpeed);
		drives[INDEX_HL].setTargetSpeed(-inSpeed);
		drives[INDEX_HR].setTargetSpeed(inSpeed);
	}

	//drive right with forward component
	private void setRF(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_VR].setTargetSpeed(-inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HL].setTargetSpeed(-inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HR].setTargetSpeed(inSpeed+(inSpeed*SIDEWAYS_COMPONENT));
	}

	//drive right with backwards forward component
	private void setRB(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_VR].setTargetSpeed(-inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HL].setTargetSpeed(-inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
		drives[INDEX_HR].setTargetSpeed(inSpeed+(-inSpeed*SIDEWAYS_COMPONENT));
	}

	//turn left
	private void setTL(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(-inSpeed);
		drives[INDEX_VR].setTargetSpeed(inSpeed);
		drives[INDEX_HL].setTargetSpeed(-inSpeed);
		drives[INDEX_HR].setTargetSpeed(inSpeed);
	}

	//turn right
	private void setTR(float inSpeed)
	{
		drives[INDEX_VL].setTargetSpeed(inSpeed);
		drives[INDEX_VR].setTargetSpeed(-inSpeed);
		drives[INDEX_HL].setTargetSpeed(inSpeed);
		drives[INDEX_HR].setTargetSpeed(-inSpeed);
	}
}
