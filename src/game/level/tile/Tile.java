package game.level.tile;

import java.util.Random;

import game.entity.Entity;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.level.Level;

public class Tile {

	public static final Random random = new Random();
	public static final Tile wall = new WallTile(0);
	public static final Tile lava = new LavaTile(16);
	public static final Tile block = new BlockTile(32);
	public static final Tile air = new AirTile(-1);
	public static final Tile portal = new PortalTile(-1);
	protected boolean canAutoTile;
	protected int texture;
	
	protected Tile(int texture) { this.texture = texture; }
	
	public void tick(Level level, int xt, int yt) {}
	
	
	public void render(Bitmap screen, Level level, int x, int y) {
		if(this.texture >= 0) {
			int xx = x >> 5;
			int yy = y >> 5;
			int t = 0;
			
			if(this.canAutoTile) {
				if(!(level.getTile(xx, yy - 1) instanceof AirTile)) t += 1;
				if(!(level.getTile(xx + 1, yy) instanceof AirTile)) t += 2;
				if(!(level.getTile(xx, yy + 1) instanceof AirTile)) t += 4;
				if(!(level.getTile(xx - 1, yy) instanceof AirTile)) t += 8;
				
			}
			
			screen.draw(Art.tiles[this.texture + t], x, y);
		}
	}
	
	public void bumpedInto(Level level, int xt, int yt, double xa, double ya, Entity e) {}
	public boolean blocks() { return false; }
	
}