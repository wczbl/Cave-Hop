package game.level.tile;

public class WallTile extends Tile {

	protected WallTile(int texture) { 
		super(texture);
		this.canAutoTile = true;
	}
	
	public boolean blocks() { return true; }
	
}