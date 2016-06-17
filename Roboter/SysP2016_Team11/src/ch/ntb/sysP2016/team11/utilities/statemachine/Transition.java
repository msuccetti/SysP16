package ch.ntb.sysP2016.team11.utilities.statemachine;
import ch.ntb.sysP2016.team11.utilities.Logger;
import ch.ntb.sysP2016.team11.utilities.deepLibraryExtension.ShittyArrayList;


public abstract class Transition extends StateChartElement {
	private final StateChartElement mSource;
	private final StateChartElement mTarget;

	private final boolean mDefaultTransition;
	private final boolean mExitTransition;


	/**
	 * This is the constructor for a standard transition (not default transition or exit transition)
	 * it registers itself at its source element.
	 * 
	 * @param aSource
	 * @param aTarget
	 */
	public Transition(StateChartElement aSource, StateChartElement aTarget){
		super("Transition", aSource.getParentStateChart());

		// error handling
		if(aSource instanceof Transition || aTarget instanceof Transition){
			Logger.log(Logger.ERROR, "Transition from ", aSource.getIdentifier(), " to ",aTarget.getIdentifier(), ": tried to connect two transitions directly!");
		}

		//error handling
		if(aSource.getParentStateChart() != aTarget.getParentStateChart()){
			Logger.log(Logger.ERROR, "Transition from ", aSource.getIdentifier(), " to ",aTarget.getIdentifier(), ": tried to connect elements of two different state charts!");
		}

		mSource=aSource;
		mTarget=aTarget;

		// register itself to the source element.
		if(mSource instanceof State){
			((State)(mSource)).addTarget(this);
		}
		else if(mSource instanceof Condition){
			((Condition)(mSource)).addTarget(this);
		}
		mDefaultTransition = false;
		mExitTransition = false;
	}


	/**
	 * This constuctor creates a termination transition!
	 * 
	 * @param aSource	the source element
	 * @param aTerminationStateChart the state which is to be terminated
	 */
	public Transition(StateChartElement aSource, StateChart aTerminationStateChart){
		super("termination transition", aTerminationStateChart);
		if(aSource.getParentStateChart()!= aTerminationStateChart){
			Logger.log(Logger.ERROR, "tried to terminate wrong state chart");
		}
		mSource = aSource;
		mTarget = null;
		mExitTransition = true;
		mDefaultTransition = false;
	}

	/**
	 * This constuctor creates a default transition!
	 * @param aOriginStateChart	the state chart
	 * @param aTarget			the targeted State
	 */
	public Transition(StateChart aOriginStateChart, StateChartElement aTarget){
		super("default transition", aOriginStateChart);
		
		//error handling
		if(aTarget.getParentStateChart()!= aOriginStateChart){
			Logger.log(Logger.ERROR, "tried to make a default transition to  a wrong state chart");
		}
		
		mSource = null;
		mTarget = aTarget;
		mExitTransition = false;
		mDefaultTransition = true;
	}

	@Override
	public ShittyArrayList<Transition> checkPath() {
		// if it is the default transition, Trigger or Guard are ignored!
		if(mDefaultTransition){
			ShittyArrayList<Transition> path = new ShittyArrayList<Transition>();
			return path;
		}
		
		// Option one: Guard of this Transition blocks
		if(!guard()){
			return null;
		}
		
		//Option two: This Transition leads directly to a state and therefore finishes (begins) the chain
		//	OR it is the exit transition.
		if (mTarget instanceof State || mExitTransition){
			ShittyArrayList<Transition> path = new ShittyArrayList<Transition>();
			path.add(this);
			return path;
		}

		//option three: This transition leads to a Condition
		if (mTarget instanceof Condition == false){
			Logger.log(Logger.ERROR, "Transition leads neither to State nor to Condition");
		} else {
		}

		// Option Three: There is no viable path leading to a state
		ShittyArrayList<Transition> path = ((Condition)mTarget).checkPath();
		if (path==null){
			return null;
		}

		// Option four: There are further transitions leading to a state
		path.add(0,this);
		return path;

	}

	/**
	 * goes along the path array list and executes all Transition's ACTION method along the way.
	 * Finally the resulting State is returned.
	 * 
	 * @param path	Array list containing all furter transitions required to enter the next state.
	 * @return		the next state
	 */
	public State traverse(ShittyArrayList<Transition> path) {
		// log and debug information
		if (mExitTransition){
			Logger.log(Logger.STATE_INFO, "Executing Terminal transition from State ", mSource.getIdentifier());
		}
		else{
			Logger.log(Logger.STATE_INFO_DETAILED, "traversing from ", mSource.getIdentifier(), " to ",mTarget.getIdentifier());
		}
		
		action();
		
		// handle the termination transition
		if(mExitTransition){
			mParentStateChart.terminate();
			return null;
		}
		
		// This Transition leads directly to a state
		if (mTarget instanceof State){
			return (State)mTarget;
		}

		// This Transition leads to a condition.
		//remove itself from traverse path and continue along the path
		path.remove(0);
		return path.get(0).traverse(path);
	}

	/**
	 * this traverse is only used in default transition and is invoked by the StateChart!
	 */
	public State traverse(){
			Logger.log(Logger.STATE_INFO, "Executing default transition to state ", mTarget.getIdentifier());
			return (State)mTarget;
	}
	
	protected void logAction(){
		Logger.log(Logger.STATE_INFO, "Executing action of Transition from ", mSource.getIdentifier(), "to", mTarget.getIdentifier());
	}
	
	//override these in implementation!
	public boolean trigger(){
		return true;
	}
	protected void action(){
		
	}
	protected boolean guard(){
		return true;
	}
}
