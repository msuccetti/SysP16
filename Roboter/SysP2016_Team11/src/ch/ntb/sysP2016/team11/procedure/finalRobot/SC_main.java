package ch.ntb.sysP2016.team11.procedure.finalRobot;



import ch.ntb.inf.deep.runtime.ppc32.Task;
import ch.ntb.sysP2016.team11.utilities.statemachine.StateChart;
import ch.ntb.sysP2016.team11.utilities.statemachine.Transition;

public class SC_main extends StateChart{

	public SC_main(String aIdentifier) {
		super("SC_main");
		
		populate();
		start();
		
		period=100;
		Task.install(this);
	}

	@Override
	public Transition populate() {
		return null;
		//TODO make procedure state chart!
	}
}
