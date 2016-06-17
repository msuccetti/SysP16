package ch.ntb.sysP2016.team11;

import java.io.PrintStream;

import ch.ntb.inf.deep.runtime.mpc555.driver.SCI;
import ch.ntb.sysP2016.team11.definitions.Consts;
import ch.ntb.sysP2016.team11.definitions.PinMap;
import ch.ntb.sysP2016.team11.drivers.DcMotor;
import ch.ntb.sysP2016.team11.drivers.FourWheelDriveController;
import ch.ntb.sysP2016.team11.drivers.IrSensor;
import ch.ntb.sysP2016.team11.drivers.IrSensorController;
import ch.ntb.sysP2016.team11.drivers.Led;
import ch.ntb.sysP2016.team11.drivers.QuadratureEncoder;
import ch.ntb.sysP2016.team11.drivers.ServoMotor;
import ch.ntb.sysP2016.team11.drivers.Switch;
import ch.ntb.sysP2016.team11.utilities.Logger;
import ch.ntb.sysP2016.team11.utilities.PeriodicTriggerObserver;

public class Sys {
	
//	public final DcMotor MOTOR_VL;
//	public final DcMotor MOTOR_VR;
//	public final DcMotor MOTOR_HL;
//	public final DcMotor MOTOR_HR;
//	public final DcMotor MOTOR_SM;
//	public final DcMotor MOTOR_HM;
	
//	public final QuadratureEncoder ENCODER_VL;
//	public final QuadratureEncoder ENCODER_VR;
//	public final QuadratureEncoder ENCODER_HL;
//	public final QuadratureEncoder ENCODER_HR;
//	public final QuadratureEncoder ENCODER_SM;
//	public final QuadratureEncoder ENCODER_HM;
	
//	public final FourWheelDriveController MOVEMENT;
	
	public final ServoMotor SERVO;
	
	public final Switch SWITCH_START;
	public final Switch SWITCH_STOP;
	public final Switch SWITCH_RESET;

	public final IrSensor IR_SENSOR_LEFT;
	public final IrSensor IR_SENSOR_RIGHT;
	public final IrSensor IR_SENSOR_BOTTOM;
	
//	public final LaserSensor LASER_SENSOR_LEFT;
//	public final LaserSensor LASER_SENSOR_RIGHT;

	public final SCI SERIAL_INTERFACE;
	public final PrintStream SERIAL_PRINT_STREAM;

	public final Led TEST_LED_1;
	public final Led TEST_LED_2;
	public final Led TEST_LED_3;
	
	public final Switch TEST_SWITCH_0;
	public final Switch TEST_SWITCH_1;
	public final Switch TEST_SWITCH_2;
	public final Switch TEST_SWITCH_3;
	
	public final QuadratureEncoder TEST_ENCODER;
	
	public final DcMotor TEST_MOTOR;

	
	private static Sys mInstance = null;
	
	private Sys() {
//		ENCODER_VL = new QuadratureEncoder(PinMap.ENCODERS_TPUA, PinMap.ENCODER_VL_PWM, Consts.ENCODER_TICKS_PER_ROTATION, Consts.WHEEL_CIRCUMFERENCE);
//		ENCODER_VR = new QuadratureEncoder(PinMap.ENCODERS_TPUA, PinMap.ENCODER_VR_PWM, Consts.ENCODER_TICKS_PER_ROTATION, Consts.WHEEL_CIRCUMFERENCE);
//		ENCODER_HL = new QuadratureEncoder(PinMap.ENCODERS_TPUA, PinMap.ENCODER_HL_PWM, Consts.ENCODER_TICKS_PER_ROTATION, Consts.WHEEL_CIRCUMFERENCE);
//		ENCODER_HR = new QuadratureEncoder(PinMap.ENCODERS_TPUA, PinMap.ENCODER_HR_PWM, Consts.ENCODER_TICKS_PER_ROTATION, Consts.WHEEL_CIRCUMFERENCE);
//		ENCODER_SM = new QuadratureEncoder(PinMap.ENCODERS_TPUA, PinMap.ENCODER_SM_PWM, Consts.ENCODER_TICKS_PER_ROTATION);
//		ENCODER_HM = new QuadratureEncoder(PinMap.ENCODERS_TPUA, PinMap.ENCODER_HM_PWM, Consts.ENCODER_TICKS_PER_ROTATION);
		PeriodicTriggerObserver obs = new PeriodicTriggerObserver(50);
//		obs.registerObserver(ENCODER_VL);
//		obs.registerObserver(ENCODER_VR);
//		obs.registerObserver(ENCODER_HL);
//		obs.registerObserver(ENCODER_HR);
//		obs.registerObserver(ENCODER_SM);
//		obs.registerObserver(ENCODER_HM);
		
		
//		MOTOR_VL = new DcMotor(PinMap.MOTORS_TPUA, PinMap.MOTOR_VL_PWM1, PinMap.MOTOR_VL_PWM2, ENCODER_VL);
//		MOTOR_VR = new DcMotor(PinMap.MOTORS_TPUA, PinMap.MOTOR_VR_PWM1, PinMap.MOTOR_VR_PWM2, ENCODER_VR);
//		MOTOR_HL = new DcMotor(PinMap.MOTORS_TPUA, PinMap.MOTOR_HL_PWM1, PinMap.MOTOR_HL_PWM2, ENCODER_HL);
//		MOTOR_HR = new DcMotor(PinMap.MOTORS_TPUA, PinMap.MOTOR_HR_PWM1, PinMap.MOTOR_HR_PWM2, ENCODER_HR);
//		MOTOR_SM = new DcMotor(PinMap.MOTORS_TPUA, PinMap.MOTOR_SM_PWM1, PinMap.MOTOR_SM_PWM2, ENCODER_SM);
//		MOTOR_HM = new DcMotor(PinMap.MOTORS_TPUA, PinMap.MOTOR_HM_PWM1, PinMap.MOTOR_HM_PWM2, ENCODER_HM);
		
//		MOVEMENT = new FourWheelDriveController();
		
		
		SERVO = new ServoMotor(PinMap.SERVO_TPUA, PinMap.SERVO_PWM, Consts.SERVO_PWM_PERIOD, Consts.SERVO_RETRACTED_POS, Consts.SERVO_EXTENDED_POS);
		
		SWITCH_RESET=new Switch(PinMap.SWITCH_RESET);
		SWITCH_STOP=new Switch(PinMap.SWITCH_STOP);
		SWITCH_START=new Switch(PinMap.SWITCH_START);
		
		IR_SENSOR_LEFT = new IrSensor(IrSensorController.getInstance(), PinMap.IR_SENSOR_INDEX_LEFT);
		IR_SENSOR_RIGHT = new IrSensor(IrSensorController.getInstance(), PinMap.IR_SENSOR_INDEX_RIGHT);
		IR_SENSOR_BOTTOM = new IrSensor(IrSensorController.getInstance(), PinMap.IR_SENSOR_INDEX_BOTTOM);
		
		//TODO: check if left sensor is 1 and right sensor 2 or vice versa
//		LASER_SENSOR_LEFT = new LaserSensor(PinMap.LASER_SENSOR_OUT_1);
//		LASER_SENSOR_RIGHT = new LaserSensor(PinMap.LASER_SENSOR_OUT_2);
		
		
		// initialise serial interface
		SERIAL_INTERFACE = SCI.getInstance(SCI.pSCI1);
		SERIAL_INTERFACE.start(9600, SCI.NO_PARITY, (short)8);
		SERIAL_PRINT_STREAM = new PrintStream(SERIAL_INTERFACE.out);

		//initialise Logging output
		Logger.setLogLevel(Consts.LOG_LEVEL);
		Logger.setPrintStream(SERIAL_PRINT_STREAM);
		
		//TESTING PURPOSE
		TEST_LED_1 = new Led(PinMap.TEST_LED_1);
		TEST_LED_2 = new Led(PinMap.TEST_LED_2);
		TEST_LED_3 = new Led(PinMap.TEST_LED_3);
		
		TEST_ENCODER = new QuadratureEncoder(PinMap.ENCODERS_TPUA, PinMap.ENCODER_VL_PWM, Consts.TEST_ENCODER_TICKS_PER_ROTATION, 1F);
		obs.registerObserver(TEST_ENCODER);
		TEST_MOTOR = new DcMotor(PinMap.MOTORS_TPUA, PinMap.MOTOR_VL_PWM1, PinMap.MOTOR_VL_PWM2, TEST_ENCODER);
		
		TEST_SWITCH_0=SWITCH_RESET;
		TEST_SWITCH_1=SWITCH_STOP;
		TEST_SWITCH_2=SWITCH_START;
		TEST_SWITCH_3 = new Switch(PinMap.SWITCH_TEST);
		
		Logger.newLine(Logger.ERROR);
		Logger.newLine(Logger.ERROR);
		Logger.newLine(Logger.ERROR);
		Logger.newLine(Logger.ERROR);
		Logger.log(Logger.NOTE, "finished hardware initialisation");
		Logger.newLine(Logger.ERROR);
	}

	public static Sys getInstance(){
		if(mInstance==null){
			mInstance = new Sys();
		}
		return mInstance;
	}
}

