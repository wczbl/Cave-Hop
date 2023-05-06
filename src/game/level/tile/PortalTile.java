package game.level.tile;

import game.entity.Entity;
import game.entity.hopper.Hopper;
import game.level.Level;
import game.particle.BlueParticle;

public class PortalTile extends AirTile {

	protected PortalTile(int texture) { super(texture); }

	public void tick(Level level, int xt, int yt) {
		if(yt + 1 < level.h && !level.getTile(xt, yt + 1).blocks()) {
			level.setTile(air, xt, yt);
			level.setTile(this, xt, yt + 1);
		}
		
		for(int i = 0; i < 3; i++) {
			level.addParticle(new BlueParticle((xt << 5) + 17 + random.nextInt(16) - 8, (yt << 5) + 32));
		}
	}
	
	public void bumpedInto(Level level, int xt, int yt, double xa, double ya, Entity e) {
		if(e instanceof Hopper && ((Hopper)e).idle()) ((Hopper)e).portal(e);
	}
	
}