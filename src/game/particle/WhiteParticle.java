package game.particle;

public class WhiteParticle extends Particle {

	public WhiteParticle(double x, double y) {
		super(x, y);
		this.gravity = -0.08;
		this.sprite = 3;
		this.ignoreBlocks = true;
		this.lifeTime = random.nextInt(80) + 10;
	}
	
	public WhiteParticle(double x, double y, double speed, double gravity) {
		super(x, y);
		this.gravity = -0.08;
		this.sprite = 3;
		this.ignoreBlocks = true;
		this.lifeTime = random.nextInt(80) + 10;
		
		do {
			this.xa = random.nextDouble() * 2 - 1;
			this.ya = random.nextDouble() * 2 - 1;
		} while(this.xa * this.xa + this.ya * this.ya > 1);
		double dd = Math.sqrt(this.xa * this.xa + this.ya * this.ya);
		this.xa = this.xa / dd * speed;
		this.ya = this.ya / dd * speed;
	}
	
}