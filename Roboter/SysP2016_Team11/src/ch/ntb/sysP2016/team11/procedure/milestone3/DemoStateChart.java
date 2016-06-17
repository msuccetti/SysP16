package ch.ntb.sysP2016.team11.procedure.milestone3;

import ch.ntb.sysP2016.team11.Sys;
import ch.ntb.sysP2016.team11.drivers.DcMotor;
import ch.ntb.sysP2016.team11.utilities.Logger;
import ch.ntb.sysP2016.team11.utilities.statemachine.Condition;
import ch.ntb.sysP2016.team11.utilities.statemachine.State;
import ch.ntb.sysP2016.team11.utilities.statemachine.StateChart;
import ch.ntb.sysP2016.team11.utilities.statemachine.Transition;

/**
 * A statechart for demonstrational use. See 
 * demo_statechart_M3.dia i the UML documentation
 * for further reference
 * 
 * @author joel
 *
 */
public class DemoStateChart extends StateChart {

	private final Sys mSystem;
	
	public DemoStateChart(Sys aSystem, int aTaskPeriod) {
		super("SC_demo");
		mSystem = aSystem;
		start();
		installOwnTask(aTaskPeriod);
	}

	@Override
	protected Transition populate() {
		Logger.log(Logger.STATE_INFO, "Populating State chart ", mIdentifier);
		
		/*****************states******************************/
		State stIdle = new State("ST_idle", this);
		State stMotor = new State("ST_motor", this);
		State stServo = new State("ST_servo", this);

		/*****************conditions**************************/
		Condition cond1 = new Condition("Condition_1", this);
		
		/*****************transitions*************************/
		new Transition(stIdle, cond1) {
			public boolean trigger(){return mSystem.TEST_SWITCH_0.get()==true;}
		};
		
		new Transition(cond1, stMotor) {
			public boolean guard(){return mSystem.TEST_SWITCH_1.get()==true;}
			public void action(){
				Logger.log(Logger.STATE_INFO, "ACTION: turning Motor on");
				mSystem.TEST_MOTOR.setSpeed((short)(Short.MAX_VALUE/10));}
		};

		new Transition(stMotor, stIdle) {
			public boolean trigger(){return mSystem.TEST_SWITCH_0.get()==false;}
			public void action(){
				Logger.log(Logger.STATE_INFO, "ACTION: turning Motor off");
				mSystem.TEST_MOTOR.setSpeed((short)0);}
		};
		
		new Transition(cond1, stServo) {
			public boolean guard(){return mSystem.TEST_SWITCH_1.get()==false;}
		};
		
		new Transition(stServo, stIdle){
			public boolean trigger(){return mSystem.TEST_SWITCH_0.get()==false;}
		};

		new Transition(stMotor, stMotor){
			public boolean trigger(){return mSystem.TEST_ENCODER.getRotations()>=5;}
			public boolean guard(){return mSystem.TEST_MOTOR.getDirection()==DcMotor.FORWARD;}
			public void action(){
				Logger.log(Logger.STATE_INFO, "ACTION: setting motor direction REVERSE");
				mSystem.TEST_MOTOR.setDirection(DcMotor.REVERSE);}
		};
		
		new Transition(stMotor, stMotor){
			public boolean trigger(){return mSystem.TEST_ENCODER.getRotations()<=0;}
			public boolean guard(){return mSystem.TEST_MOTOR.getDirection()==DcMotor.REVERSE;}
			public void action(){
				Logger.log(Logger.STATE_INFO, "ACTION: setting motor direction FORWARD");
				mSystem.TEST_MOTOR.setDirection(DcMotor.FORWARD);}
		};
		
		new Transition(stServo, stServo){
			public boolean trigger(){return mSystem.TEST_SWITCH_2.get()==true;}
			public boolean guard(){return mSystem.SERVO.isRetracted();}
			public void action(){
				Logger.log(Logger.STATE_INFO, "ACTION: extend servo");
				mSystem.SERVO.extend();}
		};
		
		new Transition(stServo, stServo){
			public boolean trigger(){return mSystem.TEST_SWITCH_2.get()==false;}
			public boolean guard(){return mSystem.SERVO.isExtended();}
			public void action(){
				Logger.log(Logger.STATE_INFO, "ACTION: retract servo");
				mSystem.SERVO.retract();}
		};
		
		
		new Transition(stMotor, stMotor){
			public boolean trigger(){return mSystem.TEST_SWITCH_3.get()==true;}
			public boolean guard(){return mSystem.TEST_MOTOR.getPwmUptime()==(short)(Short.MAX_VALUE/10);}
			public void action(){
				Logger.log(Logger.STATE_INFO, "ACTION: fast speed");
				mSystem.TEST_MOTOR.setSpeed((short)(Short.MAX_VALUE/5));}
		};

		new Transition(stMotor, stMotor){
			public boolean trigger(){return mSystem.TEST_SWITCH_3.get()==false;}
			public boolean guard(){return mSystem.TEST_MOTOR.getPwmUptime()==(short)(Short.MAX_VALUE/5);}
			public void action(){
				Logger.log(Logger.STATE_INFO, "ACTION: low speed");
				mSystem.TEST_MOTOR.setSpeed((short)(Short.MAX_VALUE/10));}
		};
		
		/*****************default transition******************/
		return(new Transition(this, stIdle){});
	}
}
