package game.particle;

public class BlueParticle extends Particle {

	public BlueParticle(double x, double y) {
		super(x, y);
		this.gravity = -0.08;
		this.sprite = 1;
		this.ignoreBlocks = true;
		this.lifeTime = random.nextInt(10) + 30;
	}

}