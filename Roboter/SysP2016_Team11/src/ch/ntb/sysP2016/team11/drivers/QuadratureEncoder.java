package ch.ntb.sysP2016.team11.drivers;



import ch.ntb.inf.deep.runtime.mpc555.driver.TPU_FQD;
import ch.ntb.sysP2016.team11.utilities.Observer;

public class QuadratureEncoder implements Observer {

	private TPU_FQD mTpu;
	
	private final float mTicksPerRotation; // defines how many increments result in a turn of the attached motor
	private final float mDistancePerRotation; // defines what kind of distance (mm) is done in one turn of the motor
	private long mDistance; // current driven distance in encoder increments, not mm! TODO: would integer suffice?
	
	/**
	 * @param aTpuA				define whether TPUA or TPUB is used
	 * @param aChannel			define the TPU's chanel number
	 * @param aTicksPerRotation		define how many increments result in one motor rotation
	 * @param aDistancePerRotation	define how much distance is done in one motor rotation
	 */
	public QuadratureEncoder(boolean aTpuA, int aChannel, float aTicksPerRotation, float aDistancePerRotation) {
		mTpu = new TPU_FQD(aTpuA, aChannel);
		mTicksPerRotation = aTicksPerRotation;
		mDistancePerRotation=aDistancePerRotation;
	}

	/**
	 * @return	how many rotations have been made since the last reset
	 */
	public float getRotations(){
		update();
		return (float)mDistance/mTicksPerRotation;

	}
	
	/**
	 * @return driven distance in millimeters
	 */
	public float getDistance(){
		return getRotations()*mDistancePerRotation;
	}
	
	/**
	 * set distance to zero
	 */
	public void resetDistance(){
		mDistance=0;
		mTpu.setPosition(0);
	}
	
	/* (non-Javadoc)
	 * @see ch.ntb.sysP2016.team11.utilities.Observer#update()
	 * 
	 * The quadrature encoder must be supervised in order to prevent 
	 * over or underflow of the counter. This would lead to serious
	 * irregularities. Therefore this method must be called regularly
	 */
	@Override
	public void update() {
		mDistance+=mTpu.getPosition();
		mTpu.setPosition(0);
	}

}
