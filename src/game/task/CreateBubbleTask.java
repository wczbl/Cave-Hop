package game.task;

import game.entity.Bubble;
import game.entity.hopper.Hopper;

public class CreateBubbleTask extends Task {

	private Bubble bubble;
	private int tickTime;
	
	public CreateBubbleTask(Hopper owner) {
		super(owner);
		this.bubble = new Bubble(owner.x + (owner.dir == 0 ? -1 : 1) * 10, owner.y);
		this.owner.level.addEntity(this.bubble);
	}
	
	public void doAction() {
		this.tickTime++;
		this.bubble.blow();
		this.owner.scale += Math.sin(this.tickTime * 0.1) * 0.01;
	}
	
	public boolean finished() {
		boolean result = this.bubble.created();
		
		if(result) {
			this.bubble.ya -= 0.3;
			this.bubble.xa += (owner.dir == 0 ? -1 : 1) * 5;
			this.owner.scale = 1.0;
		}
		
		return result;
	}
	
}