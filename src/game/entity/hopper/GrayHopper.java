package game.entity.hopper;

import game.task.CreateBubbleTask;

public class GrayHopper extends Hopper {

	public GrayHopper(double x, double y) {
		super(x, y);
		this.sprite = 1;
		this.skillDelay = this.skillTime = 120;
	}

	public boolean castSkill() {
		if(this.onGround) {
			setTask(new CreateBubbleTask(this));
			return true;
		}
		
		return false;
	}

}