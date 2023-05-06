package game.screen;

import game.GameComponent;
import game.InputHandler;
import game.gfx.Art;
import game.gfx.Bitmap;
import game.gfx.Font;

public class TitleScreen extends Screen {

	private int tickTime;
	
	public void tick(InputHandler input) {
		if(this.tickTime++ > 120) {
			if(input.jump.clicked) this.game.newGame();
		}
	}

	public void render(Bitmap screen, double delta) {
		int yOffs = GameComponent.HEIGHT - this.tickTime * 2;
		if(yOffs < 0) yOffs = 0;
		screen.draw(Art.title, 0, -yOffs);
		
		if(this.tickTime > 120) {
			String text = "PRESS SPACE TO START";
			Font.draw(text, screen, (GameComponent.WIDTH - text.length() * 7) / 2, (GameComponent.HEIGHT - 40) - 3 - (int)(Math.abs(Math.sin(this.tickTime * 0.1) * 10)));
		}
		
	}

	public void destroy() {
		
	}

}