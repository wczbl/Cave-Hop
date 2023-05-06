package game.screen;

import game.GameGui;
import game.InputHandler;
import game.Player;
import game.gfx.Bitmap;
import game.level.Level;

public class GameScreen extends Screen {

	private Level level;
	private Player player;
	private GameGui gui;
	
	public GameScreen(Level level, Player player) {
		this.level = level;
		this.player = player;
		this.gui = new GameGui(this.player);
	}
	
	public void tick(InputHandler input) {
		this.level.tick();
		this.player.tick(input);
		this.gui.tick();
	}

	public void render(Bitmap screen, double delta) {
		this.level.render(screen, delta);
		this.gui.render(screen);		
	}

	public void destroy() {

	}

}