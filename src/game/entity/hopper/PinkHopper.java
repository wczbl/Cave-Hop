package game.entity.hopper;

public class PinkHopper extends Hopper {

	public PinkHopper(double x, double y) {
		super(x, y);
		this.sprite = 2;
		this.ignoresLava = true;
	}

	public boolean castSkill() { return false; }

}