package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Bitmap;
import game.gfx.Font;

public class WinScreen extends Screen {
	
	public GameScreen gameScreen;
	private int tickTime;
	
	public WinScreen(GameScreen gameScreen) { this.gameScreen = gameScreen; }
	public void tick(InputHandler input) { this.tickTime++; }

	public void render(Bitmap screen, double delta) {
		this.gameScreen.render(screen, delta);
		
		String text = "YOU WIN!";
		int scale = 3;
		Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 7 * scale) / 2, (GameComponent.HEIGHT - 10) / 3 - (int)(Math.abs(Math.sin(this.tickTime * 0.1)) * 15), scale, 0x7F0000);
	
		text = "Press 'R' to restart";
		scale = 2;
		Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 7 * scale) / 2, (GameComponent.HEIGHT  + 30) / 2, scale);
	}

	public void destroy() {
		
	}

}