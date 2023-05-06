package game;

import game.entity.hopper.Hopper;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;

public class GameGui {

	private Player player;
	private int tickTime;
	
	public GameGui(Player player) { this.player = player; }
	
	public void tick() { this.tickTime++; }

	public void render(Bitmap screen) {
		int xOffs = 0;
		int yOffs = GameComponent.HEIGHT - 32;
		
		screen.draw(Art.guipanel, xOffs, yOffs);
		
		for(int i = 0; i < this.player.hoppers.size(); i++) {
			Hopper hopper = this.player.hoppers.get(i);
			
			int xx = (120 + xOffs + i * 20);
			int yy = yOffs + 10;
			double scale = 0.5;
			
			screen.scaleDraw(Art.sprites[hopper.sprite], xx, yy, scale, scale);
		
			if(this.player.index == i) {
				screen.draw(Art.gui[0], xx + 4, (int)(yy - 8 + Math.abs(Math.sin(this.tickTime * 0.15) * 2.2)));
			}
		}
		
		// Left Side
		Font.draw("Move - WASD", screen, 10, GameComponent.HEIGHT - 24);
		Font.draw("Restart - R", screen, 10, GameComponent.HEIGHT - 16);
		
		// Right Side
		String text = "Skill - Enter";
		
		Font.draw(text, screen, GameComponent.WIDTH - text.length() * 8, GameComponent.HEIGHT - 24);
		
		text = "Select - Q/E";
		Font.draw(text, screen, GameComponent.WIDTH - text.length() * 8, GameComponent.HEIGHT - 16);
		
		
	}

}