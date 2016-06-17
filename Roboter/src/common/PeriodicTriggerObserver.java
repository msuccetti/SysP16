package common;



import ch.ntb.inf.deep.runtime.ppc32.Task;

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