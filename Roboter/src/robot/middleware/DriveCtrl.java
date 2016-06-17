package robot.middleware;

import common.Consts;
import tests.AllObjects;

/**
 * Drives the robot for certain distances in certain directions or turns a
 * certain angle
 *
 */

public class DriveCtrl
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
		TURN_LEFT, // turn left
		STOP// HALT
	}

	//conversion factor deg <-> rad
	private static float RAD_TO_DEG=(float)(180.0/Math.PI);
	
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

	public DriveCtrl()
	{
		enable(false);// stop all drives
	};

	public void enable(boolean inEnable)
	{
		for (int driveCount = 0; 4 > driveCount; driveCount++)
		{
			drives[driveCount].enable(inEnable);
		}
	}

	public void setMovement(int inDirection,// direction of travel [deg], (0..359)
			float inTranslationSpeed,// speed of travel
			float inRotationSpeed)// speed of turn (positive-> counterclockwise)
	{
		float dirRad=inDirection/RAD_TO_DEG; //to rad
		float speedVL = 0;
		float speedVR = 0;
		float speedHL = 0;
		float speedHR = 0;
		//translation
		if(0<=inDirection && 90>inDirection)
		{//forward or left
			speedVL=inTranslationSpeed;
			speedVR=(float)Math.cos(dirRad*2)*inDirection;
			speedHL=speedVL;
			speedHR=speedVR;
		}
		if(90<=inDirection && 180>inDirection)
		{//forward or left
			speedVL=-inTranslationSpeed;
			speedVR=-(float)Math.cos(dirRad*2)*inDirection;
			speedHL=speedVR;
			speedHR=speedVL;
		}
		if(180<=inDirection && 270>inDirection)
		{//forward or left
			speedVL=-(float)Math.cos(dirRad*2)*inDirection;
			speedVR=-inTranslationSpeed;
			speedHL=speedVR;
			speedHR=speedVL;
		}
		if(270<=inDirection && 360>inDirection)
		{//backwards or right
			speedVL=inTranslationSpeed;
			speedVR=(float)Math.cos(dirRad*2)*inDirection;
			speedHL=speedVR;
			speedHR=speedVL;
		}
		
		//rotation
		speedVL-=inRotationSpeed;
		speedVR+=inRotationSpeed;
		speedHL-=inRotationSpeed;
		speedHR+=inRotationSpeed;
		
		

		drives[INDEX_VL].setTargetSpeed(speedVL);
		drives[INDEX_VR].setTargetSpeed(speedVR);
		drives[INDEX_HL].setTargetSpeed(speedHL);
		drives[INDEX_HR].setTargetSpeed(speedHR);
	}

	public void stopDrive()
	{
		// stop all drives
		for (int driveCount = 0; 4 > driveCount; driveCount++)
		{
			drives[driveCount].enable(false);
		}
	}
}
