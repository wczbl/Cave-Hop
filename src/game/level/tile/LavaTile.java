package game.level.tile;

import game.level.Level;
import game.particle.FireParticle;

public class LavaTile extends Tile {

	private int tickTime;
	
	protected LavaTile(int texture) {
		super(texture);
		this.canAutoTile = true;
	}

	public void tick(Level level, int xt, int yt) {
		this.tickTime++;
		
		if(yt + 1 < level.h && !level.getTile(xt, yt + 1).blocks() && this.tickTime % 120 == 0) {
			level.setTile(air, xt, yt);
			level.setTile(this, xt, yt + 1);
		}
		
		for(int i = 0; i < 3; i++) {
			level.addParticle(new FireParticle((xt << 5) + 17 + random.nextInt(32) - 16, (yt << 5) + 8));
		}
		
		if(Math.random() < 0.01) {
			for(int i = 0; i < 3; i++) {
				level.addParticle(new FireParticle((xt << 5) + 16 + random.nextInt(32) - 16, (yt << 5) + 8, random.nextInt(30) + 30));
			}
		}
	}
	
	public boolean blocks() { return true; }

}