package game.level.tile;

import game.entity.Entity;
import game.entity.hopper.YellowHopper;
import game.level.Level;

public class BlockTile extends Tile {

	protected BlockTile(int texture) { super(texture); }
	
	public boolean blocks() { return true; }
	
	public void bumpedInto(Level level, int xt, int yt, double xa, double ya, Entity e) {
		if(xa != 0) {
			if(e instanceof YellowHopper) {
				if(Math.abs(e.xa) > 6) {
					level.destroyTile(e, xt, yt);
					e.xa *= -0.005;
				}
			}
		}
	}
	
}