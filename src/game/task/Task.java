package game.task;

import java.util.Random;

import game.entity.hopper.Hopper;

public abstract class Task {

	protected static final Random random = new Random();
	protected Hopper owner;
	
	public Task(Hopper owner) { this.owner = owner; }
	
	public abstract void doAction();
	public abstract boolean finished();
	public void destroy() {}
	
}