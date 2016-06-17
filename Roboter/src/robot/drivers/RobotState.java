package robot.drivers;


/**
 * 
 * @author msuccetti
 *
 * this class represents all static robot state information
 */
public class RobotState {

	//represents the starting position of the robot according to the border it is started at
	public enum StartPosition 
	{
		START_POS_NOT_ACQUIRED_YET, //starting position not yet known
		START_POS_RIGHT, //starts at the right border
		START_POS_LEFT //starts at the left border
	}

	//stores the actual starting position of the robot according to the border it is started at
	private static StartPosition startPosition;
	
	//channels of IR sensors
	static final int IR_SENSOR_CHASSIS_RIGHT_CHANNEL=0;
	static final int IR_SENSOR_CHASSIS_LEFT_CHANNEL=1;
	
	//create IR sensors
	public final static IrSensor ChassisRight=new IrSensor(IR_SENSOR_CHASSIS_RIGHT_CHANNEL);
	public final static IrSensor ChassisLeft=new IrSensor(IR_SENSOR_CHASSIS_LEFT_CHANNEL);

	//getter for the actual starting position
	public static StartPosition getStartPosition()
	{
		return startPosition;
	}
	
	//setter for the actual starting position
	public static void setStartPosition(StartPosition inStartPosition)
	{
		startPosition=inStartPosition;
	}
	

	//true -> all prerequisites for starting the robot are fulfilled, false -> otherwise
	public boolean prerequisitesForStartFulfilled()
	{
		if (StartPosition.START_POS_NOT_ACQUIRED_YET==startPosition)
		{//we don't know where we start yet
			return false;
		}
		
	
		//all prerequisites for starting the robot are fulfilled
		return true;
	}

}
