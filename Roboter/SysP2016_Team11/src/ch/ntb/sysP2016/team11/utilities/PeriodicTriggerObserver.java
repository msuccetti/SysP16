package ch.ntb.sysP2016.team11.utilities;



import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysP2016.team11.utilities.deepLibraryExtension.ShittyArrayList;

public class PeriodicTriggerObserver extends Task{

	private ShittyArrayList<Observer> mObservers;

	public PeriodicTriggerObserver(int aPeriodInMs) {
		mObservers = new ShittyArrayList<Observer>();
		period=aPeriodInMs;
		Task.install(this);
	}
	
	public void registerObserver(Observer aObserver){
		mObservers.add(aObserver);
	}
	
	private void notifyObservers(){
		for(Observer obs:mObservers){
			obs.update();
		}
	}
	
	public void action(){
		notifyObservers();
	}



}