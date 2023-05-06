package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Bitmap;
import game.gfx.Font;

public class GameOverScreen extends Screen {

	public GameScreen gameScreen;
	
	public GameOverScreen(GameScreen gameScreen) { this.gameScreen = gameScreen; }
	
	public void tick(InputHandler input) {}

	public void render(Bitmap screen, double delta) {
		this.gameScreen.render(screen, delta);
		
		String text = "GAME OVER";
		int scale = 3;
		Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 7 * scale) / 2, (GameComponent.HEIGHT - 10) / 2, scale, 0x000000);
	}

	public void destroy() {}
	
}