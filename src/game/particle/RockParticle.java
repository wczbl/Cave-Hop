package game.particle;

import game.gfx.Art;
import game.gfx.Bitmap;

public class RockParticle extends Particle {

	public RockParticle(double x, double y) {
		super(x, y);
		do {
			this.xa = random.nextDouble() * 2 - 1;
			this.ya = random.nextDouble() * 2 - 1;
		} while(this.xa * this.xa + this.ya * this.ya > 1);
		double dd = Math.sqrt(this.xa * this.xa + this.ya * this.ya);
		double speed = Math.sqrt(this.xa * this.xa + this.ya * this.ya);
		
		this.sprite = 0;
		this.xa = this.xa / dd * speed;
		this.ya = this.ya / dd * speed;
	}
	
	public void render(Bitmap screen) {
		screen.scaleDraw(Art.particles[this.sprite].rotate(this.rot), (int)(this.x - 4), (int)(this.y - 4), 2, 2);
	}
	
}