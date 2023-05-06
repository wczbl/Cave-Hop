package game.entity.hopper;

public class YellowHopper extends Hopper {

	public YellowHopper(double x, double y) {
		super(x, y);
		this.sprite = 0;
		this.skillDelay = this.skillTime = 0;
	}
	
	public void tick() {
		super.tick();
		this.acceleration *= 0.8;
	}

	public boolean castSkill() {
		this.acceleration = 2.0;
		return true;
	}

}