package game.entity;

import java.util.List;
import java.util.Random;

import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.Level;

public abstract class Entity {

	protected static final Random random = new Random();
	public double xo;
	public double yo;
	public double x;
	public double y;
	public double xa;
	public double ya;
	public double xr = 14;
	public double yr = 14;
	public int tickTime;
	public int ysOffs = 4;
	public int sprite = -1;
	public Level level;
	protected double bounce = 0.05;
	public int dir;
	public double bobFactor;
	public int xSlot;
	public int ySlot;
	public int zSort;
	public double scale = 1.0;
	public Entity under;
	public int hurtTime;
	public int health = 2;
	public boolean ignoreBlocks;
	public boolean onGround;
	public boolean removed;
	
	public Entity(double x, double y) {
		this.xo = this.x = x;
		this.yo = this.y = y;
		this.zSort = 0;
	}
	
	public void init(Level level) { this.level = level; }
	
	public void tick() {
		this.xo = this.x;
		this.yo = this.y;
		this.tickTime++;
		
		if(this.hurtTime > 0) this.hurtTime--;
		
		move();
		
		this.xa *= 0.7;
		this.ya *= this.level.friction;
		this.ya += this.level.gravity;
	}
	
	public void render(Bitmap screen, double delta) {
		if(this.hurtTime > 0 && this.hurtTime / 4 % 2 == 0) return;
		
		if(this.sprite >= 0) {
			double xx = (int)(this.xo + (this.x - this.xo) * delta);
			double yy = (int)(this.yo + (this.y - this.yo) * delta);
			
			if(this.dir == 0) screen.flipped = true;
			double breath = Math.sin(this.tickTime * 0.1) * 2.5;
			
			screen.scaleDraw(Art.sprites[this.sprite].rotate(this.bobFactor), (int)(xx - this.xr), (int)(yy - this.yr + breath) - 2, this.scale, this.scale);
			screen.flipped = false;
		}
	}
	
	public void move() {
		this.bobFactor += (Math.abs(this.xa)) * 0.5;
		this.bobFactor *= 0.15;
		
		this.onGround = false;
		this.under = null;
		
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
			} else {
				if(this.ya > 0) this.onGround = true;
				this.ya *= -this.bounce;
			}
		}
	}
	
	public boolean isFree(double xxa, double yya) {	
		if(this.ignoreBlocks) return true;
		
		int xto0 = (int)(this.x - this.xr) >> 5;
		int yto0 = (int)(this.y - this.yr) >> 5;
		int xto1 = (int)(this.x + this.xr) >> 5;
		int yto1 = (int)(this.y + this.yr) >> 5;
	
		int xt0 = (int)((this.x + xxa) - this.xr) >> 5;
		int yt0 = (int)((this.y + yya) - this.yr) >> 5;
		int xt1 = (int)((this.x + xxa) + this.xr) >> 5;
		int yt1 = (int)((this.y + yya) + this.yr) >> 5;
		
		for(int yt = yt0; yt <= yt1; yt++) {
			for(int xt = xt0; xt <= xt1; xt++) {
				if(xt >= xto0 && yt >= yto0 && xt <= xto1 && yt <= yto1) continue;
				
				this.level.getTile(xt, yt).bumpedInto(this.level, xt, yt, xxa, yya, this);
				
				if(!this.level.getTile(xt, yt).blocks()) continue;
				return false;
				
			}			
		}
		
		List<Entity> wasInside = this.level.getEntities((int)(this.x - this.xr), (int)(this.y - this.yr), (int)(this.x + this.xr), (int)(this.y + this.yr));
		List<Entity> isInside = this.level.getEntities((int)(this.x + xxa - this.xr), (int)(this.y + yya - this.yr), (int)(this.x + xxa + this.xr), (int)(this.y + yya + this.yr));
		
		for(int i = 0; i < isInside.size(); i++) {
			Entity e = isInside.get(i);
			if(e == this) continue;
			
			if(yya > 0) {
				touchUnder(e);
				return false;
			}
			
			e.touchedBy(this);
		}
		
		isInside.removeAll(wasInside);
		for(int i = 0; i < isInside.size(); i++) {
			Entity e = isInside.get(i);
			if(e == this) continue;
			if(!e.blocks(this)) continue;
			return false;
		}
		
		return true; 
	}
	
	public boolean intersects(int x0, int y0, int x1, int y1) {
		return !(this.x + this.xr < x0 || this.y + this.yr < y0 || this.x - this.xr > x1 || this.y - this.yr > y1);
	}
	
	public void hurt(double xa, double ya, int damage) {
		if(this.hurtTime <= 0) {
			this.xa *= xa;
			this.ya *= ya;
			this.health -= damage;
			if(this.health <= 0) die();
			else this.hurtTime = 60;
		}
	}
	
	protected void touchedBy(Entity e) {
		this.xa += e.xa * 0.007;
		this.ya += e.ya * 0.007;
	}
	
	protected void touchUnder(Entity e) { this.under = e; }
	protected boolean blocks(Entity e) { return true; }
	public void bump(double xxa, double yya) {}
	public void die() { this.removed = true; }
	
}