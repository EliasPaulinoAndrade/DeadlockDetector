package application.delegates;

import application.graphDrawer.GDGraphDrawer;
import application.graphDrawer.GDGraphDrawerDelegate;

public class MyDeadlockGraphDelegate implements GDGraphDrawerDelegate{

	@Override
	public void graphDrawerNodeClicked(GDGraphDrawer graphDrawer) {
		
		System.out.println("ei");
	}

}
