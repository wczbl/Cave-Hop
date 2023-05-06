package game.entity.hopper;

import game.InputHandler;
import game.entity.Bubble;
import game.entity.Entity;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.tile.LavaTile;
import game.particle.WhiteParticle;
import game.task.IdleTask;
import game.task.PortalTask;
import game.task.Task;

public abstract class Hopper extends Entity {

	private Task task;
	private int frames;
	private IdleTask idleTask = new IdleTask(this);
	public double walkSpeed;
	public double acceleration;
	public boolean completed;
	public int selectedTime;
	public boolean ignoresLava;
	public int skillDelay;
	public int skillTime;
	
	public Hopper(double x, double y) {
		super(x, y);
		this.dir = random.nextInt(2);
		setTask(this.idleTask);
		this.walkSpeed = 0.7;
	}
	
	public boolean idle() { return this.task == null || this.task.finished(); }
	public void select() { this.selectedTime = 30; }
	
	public void tick() {
		super.tick();
		
		if(!this.ignoresLava && this.level.getTile((int)(this.x) >> 5, ((int)(this.y - this.yr) >> 5) + 1) instanceof LavaTile) hurt(-2.8, -0.8, 1);
		
		double xd = this.xo - this.x;
		if(Math.abs(xd) > 3) this.level.addParticle(new WhiteParticle(this.xo, this.yo + this.yr, 0.1, -0.02));
	
		if(this.selectedTime > 0) this.selectedTime--;
		if(this.task != null && !this.task.finished()) this.task.doAction();
		if(this.task == null || this.task.finished()) setTask(this.idleTask);
		
		if(this.under instanceof Bubble) {
			if(((Bubble)this.under).created()) {
				// do nothing
			}
		}
	}
	
	public void tick(InputHandler input) {
		if(idle()) {
			double walkSpeed = this.walkSpeed + this.acceleration;
			boolean walk = false;
			
			if(this.skillTime < this.skillDelay) this.skillTime++;
			
			if(input.left.down) {
				this.xa -= walkSpeed;
				this.dir = 0;
				walk = true;
			}
			
			if(input.right.down) {
				this.xa += walkSpeed;
				this.dir = 1;
				walk = true;
			}
			
			if(input.up.down && this.under instanceof Bubble) this.under.ya -= 0.5;
			if(input.down.down && this.under instanceof Bubble) this.under.ya += 0.06;

			if(input.useSkill.down) {
				if(this.skillTime >= this.skillDelay && castSkill()) this.skillTime = 0;
			}
			
			if(walk) this.frames += Math.round(walkSpeed + 0.5);
			
			if(input.jump.clicked && this.onGround) this.ya -= 3 + Math.abs(this.xa) * 0.2;
			
		}
	}
	
	public void render(Bitmap screen, double delta) {
		if(this.selectedTime > 0 && this.selectedTime / 2 % 2 == 0) return;
		super.render(screen, delta);
		
		int xx = (int)(this.xo + (this.x - this.xo) * delta);
		int yy = (int)(this.yo + (this.y - this.yo) * delta);
		
		if(this.dir == 0) screen.flipped = true;
				
		screen.draw(Art.sprites[(this.frames / 10 % 3) + 2 * 4], (int)(xx - this.xr), (int)(yy - this.yr + this.ysOffs) - 2);
		screen.flipped = false;
	}
	
	public abstract boolean castSkill();
	
	public void setTask(Task task) {
		if(task != null) task.destroy();
		this.task = task;
	}
	
	public void portal(Entity e) { setTask(new PortalTask(this, this.level)); }
	
}