package ch.ntb.sysP2016.team11.utilities.statemachine;

import ch.ntb.sysP2016.team11.utilities.deepLibraryExtension.ShittyArrayList;

/**
 * superclass for the elements of a statechart.
 * 
 * @author joel
 *
 */
public abstract class StateChartElement {
	protected final StateChart mParentStateChart;
	private final String mIdentifier;

	/**
	 * @param aIdentifier	an identifier String used only for debugging/logging
	 * @param aParentStateChart		holds a reference to its statechart.
	 */
	protected StateChartElement(String aIdentifier, StateChart aParentStateChart){
		mParentStateChart=aParentStateChart;
		mIdentifier=aIdentifier;
	}

	/**
	 * accessor for the identifier
	 * @return its identifier string
	 */
	protected String getIdentifier(){
		return mIdentifier;
	}
	
	public StateChart getParentStateChart(){
		return mParentStateChart;
	}

	/**
	 * Iterate through transitions to find a viable path (TRIGGER and all GUARDs must return true)
	 * if multiple paths are feasible, it always returns the first one found (i.e. the upper one in the list)
	 * In this function the current state does NOT change. It only searches for a feasible way.
	 * 
	 * @return		a list of transitions to pass in order to reach the next state
	 */
	public abstract ShittyArrayList<Transition> checkPath();

}
