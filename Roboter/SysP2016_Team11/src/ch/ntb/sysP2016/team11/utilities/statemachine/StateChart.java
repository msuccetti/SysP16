package ch.ntb.sysP2016.team11.utilities.statemachine;

import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysP2016.team11.utilities.Logger;

/**
 * This class holds a state chart. It contains references to the default transition and the current state. It triggers
 * the state's traversions. The statechart can be used as its own task or the task may not be installed and the action()
 * method is called regularly from another source.
 * 
 * @author joel
 *
 */
public abstract class StateChart extends Task{
	
	public static enum Activity{RUNNING, PAUSED, TERMINATED}
	
	protected final String mIdentifier;
	protected Activity mCurrentActivity = Activity.TERMINATED;

	protected Transition mDefaultTransition=null;
	protected State mCurrentState=null;
	
	
	public StateChart(String aIdentifier){
		mIdentifier = aIdentifier;
	}

	/**
	 * create the state chart by calling the populate() routing.
	 * check if default transition has been defined. 
	 * start the default transition's state and set the statechart's activity level to running.
	 */
	public void start(){
		mDefaultTransition = populate();
		// error handling
		if(mCurrentActivity!=Activity.TERMINATED){
			Logger.log(Logger.WARNING, "Started statechart ", mIdentifier, ", which has already been started!");
			return;
		}
		// error handling
		if(mDefaultTransition==null){
			Logger.log(Logger.ERROR, "Started statechart ", mIdentifier, " without any default transition!");
			return;
		}

//		// starting procedure
		mCurrentActivity=Activity.RUNNING;
		mCurrentState=mDefaultTransition.traverse();
		mCurrentState.init();
	}
	
	/**
	 * EXPERIMENTAL AND UNTESTED
	 * pausing and resuming a statechart has not been tested yet
	 */ 
	public void pause(){
		// error handling
		if(mCurrentActivity!=Activity.RUNNING){
			Logger.log(Logger.WARNING, "tried to pause statechart ", mIdentifier, " which wasn't running");
			return;
		}
		
		mCurrentActivity=Activity.PAUSED;
		// pause the sub-statechart in the active state!
		StateChart subStateChart = mCurrentState.getChildStateChart();
		if(subStateChart != null){
			subStateChart.pause();
		}
	}
	
	/**
	 * EXPERIMENTAL AND UNTESTED
	 * pausing and resuming a statechart has not been tested yet
	 */ 
	public void resume(){
		if(mCurrentActivity != Activity.PAUSED){
			Logger.log(Logger.WARNING, "tried to resume statechart ", mIdentifier, " which wasn't paused");
			return;
		}
		mCurrentActivity=Activity.RUNNING;
		// resume the sub-statechart in the active state!
		StateChart subStateChart = mCurrentState.getChildStateChart();
		if(subStateChart != null){
			subStateChart.resume();
		}
	}
	
	/**
	 * 	executed by the terminal transition. The statechart execution is finished.
	 */
	public void terminate(){
		if(mCurrentActivity==Activity.TERMINATED){
			Logger.log(Logger.WARNING, "tried to terminate already terminated statechart ", mIdentifier);
			return;
		}
		mCurrentActivity = Activity.TERMINATED;
		
		// terminate the sub-statechart in the active state!
		StateChart subStateChart = mCurrentState.getChildStateChart();
		if(subStateChart != null){
			subStateChart.terminate();
		}
		mCurrentState.terminate();
	}
	
	/**
	 * returns the statechart's current activity
	 * @return	RUNNING, PAUSED or TERMINATED
	 */
	public Activity getActivity(){
		return mCurrentActivity;
	}

	/**
	 * sets the default transition.
	 * The default transition is a transition, which is invoked using following constructor:
	 * new Transition(this, State){}
	 * 
	 * @param aDefaultTransition	the default transition
	 */
	public void setDefaultTransition(Transition aDefaultTransition){
		mDefaultTransition = aDefaultTransition;
	}
	
	/**
	 * accessor for the default transition
	 * @return the default transition
	 */
	public Transition getDefaultTransition(){
		return mDefaultTransition;
	}

	/**
	 * sets the state which is triggered currently
	 * @param aCurrentState		the currently enabled state.
	 */
	public void setCurrentState(State aCurrentState){
		mCurrentState = aCurrentState;
	}
	/**
	 * accessor for the current state
	 * @return	the current State
	 */
	public State getCurrentState(){
		return mCurrentState;
	}
	
	public void action(){
		//error handling
		if(mCurrentActivity==Activity.TERMINATED){
			Logger.log(Logger.WARNING, "tried to use action() on terminated statechart ", mIdentifier);
		}
		
		// executing the state chart
		if(mCurrentActivity==Activity.RUNNING){
			mCurrentState.run();
		}
	}
	
	public void installOwnTask(int aPeriod){
		period=aPeriod;
		Task.install(this);
	}

	/**
	 * create the states, conditions and returns the default transition!
	 */
	protected abstract Transition populate();
}
