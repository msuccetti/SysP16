package ch.ntb.sysP2016.team11.utilities.statemachine;


import ch.ntb.sysP2016.team11.utilities.Logger;
import ch.ntb.sysP2016.team11.utilities.deepLibraryExtension.ShittyArrayList;



/**
 * This class defines the behaviour of a State.
 * it triggers the search for feasible paths to other states.
 * 
 * Each state may contain a nested state chart. As long as the current state stays
 * active, the substatechart is triggered. As soon as the state is left, the nested
 * statechart will be paused to continue on reentering the state.
 * PLEASE don't install a substatechart as Task or Chaos may ensue.
 * THE SUBSTATECHART FUNCTIONALITY IS YET UNTESTED.
 * 
 * If you need the entry exit or do functions defined by UML standard,
 * you may override the UmlEntry, UmlDo and UmlExit functions.
 * 
 */
public class State extends StateChartElement {

	private StateChart mChildStateChart = null;
	private ShittyArrayList<Transition> mTargets = new ShittyArrayList<Transition>();
	private boolean mInitialised=false;

	
	/**
	 * @param aIdentifier	identification string for debugging / logging
	 * @param aParentStateChart		reference to the state chart.
	 */
	public State(String aIdentifier, StateChart aParentStateChart){
		super(aIdentifier, aParentStateChart);
		mTargets = new ShittyArrayList<Transition>();
	}

	/**
	 * Initialises a state and its nested state charts.
	 * Initialisation must be reexecuted on every proceding state entry.
	 */
	public void init(){
		//error handling
		if(mInitialised){
			Logger.log(Logger.WARNING, "Started already running State ",getIdentifier());
			return;
		}
		
		mInitialised=true;
		
		// launch entry action
		UmlEntry(); 
		
		// initialise (start or resume) nested state chart.
		if(mChildStateChart!=null){
			if(mChildStateChart.getActivity()== StateChart.Activity.PAUSED){
				mChildStateChart.resume();
			}
			else {
				mChildStateChart.start();
			}
		}
		Logger.log(Logger.STATE_INFO, "entered State ",getIdentifier());
	}

	/**
	 * This function returns whether the 
	 * 
	 * @return true if the state has been initialised.
	 */
	public boolean isInitialised(){
		return mInitialised;
	}

	/**
	 * adds a transition to the state.
	 * @param aTarget	A Transition
	 */
	public void addTarget(Transition aTarget){
		if (mParentStateChart!=aTarget.getParentStateChart()){
			Logger.log(Logger.ERROR, "tried to add transition from foreign statechart to State ",getIdentifier());
		}
		mTargets.add(aTarget);
	}

	/**
	 * This method handles the further execution.
	 * 1. execute the "do" functionality defined by UML standard
	 * 2. check if a transition to another state is triggered.
	 * 3. leave this state if a transition has been found.
	 * 3. Traverse to a new state if a transition has been found.
	 * 4. if no traversion found, run substatechart (if available).
	 * 
	 */
	public final void run(){
		//error handling
		if(!mInitialised){
			Logger.log(Logger.WARNING, "Attempted to run inactive State ",getIdentifier());
			return;
		}
		
		// 1. execute UML do functionality
		UmlDo();
		
		// 2. check for triggered transitions
		ShittyArrayList<Transition> path = checkPath();
		if (path!=null){ //found a valid traverse so switch state.
			// 3. "leave" current state
			UmlExit();
			if(mChildStateChart!=null){
				mChildStateChart.pause();
			}
			terminate();
			
			// 4. traverse to next state
			State newState = path.get(0).traverse(path);
			
			//this is required because the transition might be the termination!
			if(newState != null){
				mParentStateChart.setCurrentState(newState);
				newState.init();
			}
			
		}
		else{
			//5. found no traverse to go. Trigger inner stateChart
			if(mChildStateChart!=null){
				if (mChildStateChart.getActivity() == StateChart.Activity.RUNNING)
					mChildStateChart.action();
			}
		}
	}

	/* (non-Javadoc)
	 * @see ch.ntb.sysP2016.team11.utilities.statemachine.StateChartElement#checkPath()
	 * 
	 * iterate through all registered transition. Check the transition's TRIGGER. If a
	 * transition triggers, check if it is a valid transition.
	 * returns the FIRST valid path from the array list even if other transitions trigger and are valid paths.
	 */
	public ShittyArrayList<Transition> checkPath() {
		// abort if no transitions are registered.
		if(mTargets.getLength()==0){
			return null;
		}
	
		//iterate through all registered transitions
		for(Transition transition:mTargets){
			if (transition.trigger()){
				ShittyArrayList<Transition> path = transition.checkPath();
				if(path!=null){
					return path;
				}
			}
		}
		//no feasible path found. Staying in this State.
		return null;
	}

	/**
	 * Each state may hold a nested statechart. This is defined here.
	 * 
	 * @param aStateChart	the nested state chart
	 */
	public void setChildStateChart(StateChart aStateChart){
		mChildStateChart = aStateChart;
	}

	/**
	 * accessor for child state chart.
	 * 
	 * @return	the child state chart
	 */
	public StateChart getChildStateChart(){
		return mChildStateChart;
	}

	/**
	 * uninitialise the state.
	 */
	public void terminate(){
		Logger.newLine(Logger.STATE_INFO);
		Logger.log(Logger.STATE_INFO_DETAILED, "exited State ",getIdentifier());
		mInitialised=false;
	}

	//override if desired!
	protected void UmlEntry(){}
	
	protected void UmlExit(){}
	
	protected void UmlDo(){}


}
