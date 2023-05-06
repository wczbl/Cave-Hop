package game.task;

import game.entity.hopper.Hopper;

public class IdleTask extends Task {

	public IdleTask(Hopper owner) { super(owner); }

	public void doAction() {}
	public boolean finished() { return true; }

	
}