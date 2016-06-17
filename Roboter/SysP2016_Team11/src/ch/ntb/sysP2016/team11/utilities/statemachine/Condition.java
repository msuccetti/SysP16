package ch.ntb.sysP2016.team11.utilities.statemachine;

import ch.ntb.sysP2016.team11.utilities.Logger;
import ch.ntb.sysP2016.team11.utilities.deepLibraryExtension.ShittyArrayList;



/**
 * This is a node which is used if a transition splits according to a certain condition.
 * The class doesn't hold the condition itself (it is in the Transition classes guard() method).
 * It is just a way to connect a source transition with multiple target transitions.
 * 
 * @author joel
 *
 */
public class Condition extends StateChartElement {

	private ShittyArrayList<Transition> mTargets=new ShittyArrayList<Transition>();

	
	/**
	 * @param aIdentifier	used for debugging only
	 * @param aStateChart	reference of the parent state chart.
	 */
	public Condition(String aIdentifier, StateChart aStateChart){
		super(aIdentifier, aStateChart);
	}
	
	/**
	 * @param aTarget add a Transition to the targets
	 */
	public void addTarget(Transition aTarget){
		if (mParentStateChart!=aTarget.getParentStateChart()){
			Logger.log(Logger.ERROR, "tried to add transition from foreign statechart to Condition ",getIdentifier());
		}
		mTargets.add(aTarget);
	}
	
	
	/* (non-Javadoc)
	 * @see ch.ntb.sysP2016.team11.utilities.statemachine.StateChartElement#checkPath()
	 * 
	 * iterate through all its target elements and calls their checkPath() function to
	 * determine if any viable path exists.
	 */
	public ShittyArrayList<Transition> checkPath() {
		// no targets fount -> dead end
		if(mTargets.getLength()==0){
			Logger.log(Logger.WARNING, "Condition has no target");
			return null;
		}
		
		// check all transitions for direction
		for (Transition transition:mTargets){
			ShittyArrayList<Transition> path = transition.checkPath();
			if (path!=null){
				return path;
			}
		}
		//no transition leads to a path -> dead end
		return null;
	}
}
