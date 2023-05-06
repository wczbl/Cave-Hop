package game.task;

import game.entity.hopper.Hopper;
import game.level.Level;
import game.particle.WhiteParticle;

public class PortalTask extends Task {

	private int tickTime;
	private Level level;
	
	public PortalTask(Hopper owner, Level level) {
		super(owner);
		this.level = level;
	}
	
	public void doAction() {
		this.tickTime++;
		for(int i = 0; i < 3; i++) {
			level.addParticle(new WhiteParticle(this.owner.x + random.nextInt(16) - 8, this.owner.y + this.owner.yr));
		}
	}

	public boolean finished() {
		if(this.tickTime >= 60) {
			this.owner.completed = true;
			this.owner.die();
			return true;
		}
		
		return false;
	}
}