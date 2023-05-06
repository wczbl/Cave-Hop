package game.particle;

import java.util.Random;

import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.Level;

public class Particle {

	protected static final Random random = new Random();
	protected Level level;
	public double scale = 1.0;
	public double x;
	public double y;
	public double xa;
	public double ya;
	public double rot;
	public double rotA;
	public int sprite = -1;
	public double bounce = 0.8;
	public int tickTime;
	public int lifeTime = 120;
	public double gravity = 0.09;
	public boolean ignoreBlocks;
	public boolean removed;
	
	public Particle(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void init(Level level) { this.level = level; }
	
	public void tick() {
		if(this.lifeTime-- <= 0) {
			remove();
			return;
		}
		
		this.tickTime++;
		
		move();
		
		this.rot += this.rotA;
		this.rotA += this.xa * 0.2;
		this.rotA *= 0.3;
		
		this.xa *= this.level.friction;
		this.ya *= this.level.friction;
		this.ya += this.gravity;
	}
	
	public void render(Bitmap screen) {
		if(this.sprite >= 0) {
			screen.scaleDraw(Art.particles[this.sprite].rotate(this.rot), (int)(this.x - 4), (int)(this.y - 4), this.scale, this.scale);
		}
	}
	
	public void move() {
		int xSteps = (int)(Math.abs(this.xa * 1000) + 1);
		for(int i = xSteps; i >= 0; i--) {
			if(isFree(this.xa * i / xSteps, 0)) {
				this.x += this.xa * i / xSteps;
				break;
			} else this.xa *= -this.bounce;
		}
		
		int ySteps = (int)(Math.abs(this.ya * 1000) + 1);
		for(int i = ySteps; i >= 0; i--) {
			if(isFree(0, this.ya * i / ySteps)) {
				this.y += this.ya * i / ySteps;
				break;
			} else this.ya *= -this.bounce;			
		}
	}
	
	public boolean isFree(double xxa, double yya) {	
		if(this.ignoreBlocks) return true;
		
		int xto0 = (int)(this.x - 4) >> 5;
		int yto0 = (int)(this.y - 4) >> 5;
		int xto1 = (int)(this.x + 4) >> 5;
		int yto1 = (int)(this.y + 4) >> 5;
	
		int xt0 = (int)((this.x + xxa) - 4) >> 5;
		int yt0 = (int)((this.y + yya) - 4) >> 5;
		int xt1 = (int)((this.x + xxa) + 4) >> 5;
		int yt1 = (int)((this.y + yya) + 4) >> 5;
		
		for(int yt = yt0; yt <= yt1; yt++) {
			for(int xt = xt0; xt <= xt1; xt++) {
				if(xt >= xto0 && yt >= yto0 && xt <= xto1 && yt <= yto1) continue;
								
				if(!this.level.getTile(xt, yt).blocks()) continue;
				return false;
				
			}			
		}
		
		return true; 
	}
	
	public void remove() { this.removed = true; }
	
}