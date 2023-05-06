package game.particle;

public class FireParticle extends Particle {

	public FireParticle(double x, double y, int time) {
		super(x, y);
		this.gravity = -0.05;
		this.sprite = 2;
		this.ignoreBlocks = true;
		this.lifeTime = time;
		this.xa = (random.nextDouble() * 2 - 1) * 0.2;
	}
	
	public FireParticle(double x, double y) {
		super(x, y);
		this.gravity = -0.05;
		this.sprite = 2;
		this.ignoreBlocks = true;
		this.lifeTime = random.nextInt(10) + 10;
		this.xa = (random.nextDouble() * 2 - 1) * 0.2;
	}
	
}