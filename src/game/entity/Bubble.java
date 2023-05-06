package game.entity;

import game.gfx.Art;
import game.gfx.Bitmap;
import game.particle.WhiteParticle;

public class Bubble extends Entity {

	public int creationTime;
	public int maxCreationTime;
	public double minScale;
	public double maxScale;
	public int lifeTime;
	public int maxLifeTime;
	
	public Bubble(double x, double y) {
		super(x, y);
		this.xa = this.ya = 0;
		this.creationTime = this.maxCreationTime = 120;
		this.lifeTime = this.maxLifeTime = 60 * 15;
		this.sprite = 4;
		this.zSort = 1;
		this.minScale = this.scale = 0.3;
		this.maxScale = 1;
		this.yr = 2;
		this.ignoreBlocks = true;
	}
	
	public void tick() {
		this.scale = (this.minScale + (this.maxScale - this.minScale) * (this.maxCreationTime - this.creationTime) / this.maxCreationTime);
		
		if(created()) {
			if(this.lifeTime-- <= 0) die();
			this.scale = (this.minScale + (this.maxScale - this.minScale) * this.lifeTime / this.maxLifeTime);
		}
		
		this.xo = this.x;
		this.yo = this.y;
		this.tickTime++;
		
		move();
		
		this.xa *= 0.7;
		this.ya *= 0.9;
	}
	
	public void render(Bitmap screen, double delta) {
		double xx = this.xo + (this.x - this.xo) * delta;
		double yy = this.yo + (this.y - this.yo) * delta;
		
		double breath = Math.sin(this.tickTime * 0.1) * 0.05;
		screen.scaleDraw(Art.sprites[this.sprite], (int)(xx - this.xr * this.scale) + (screen.flipped ? 16 : 8), (int)(yy - this.yr * this.scale) - 16, this.scale + breath, this.scale + breath);
	}
	
	protected boolean blocks(Entity e) { return created(); }

	public void blow() {
		if(this.creationTime > 0) this.creationTime--;
	}
	
	public void die() {
		super.die();
		for(int i = 0; i < 5; i++) {
			this.level.addParticle(new WhiteParticle(this.x + random.nextInt(16) - 8, this.y - random.nextInt(16) - 8, random.nextDouble() * 0.04, 0.006));
		}
	}
	
	public boolean created() { return this.creationTime == 0; }
	protected void touchedBy(Entity e) {}
	
	
}