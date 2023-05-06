package game;

import game.gfx.Bitmap;
import game.level.Level;
import game.screen.GameOverScreen;
import game.screen.GameScreen;
import game.screen.Screen;
import game.screen.TitleScreen;
import game.screen.WinScreen;

public class Game {
	
	private Screen screen;
	private Player player;
	private Level level;
	
	public Game() { 
		this.player = new Player(this);
		setScreen(new TitleScreen());
	}
	
	public void setScreen(Screen screen) {
		if(this.screen != null) this.screen.destroy();
		this.screen = screen;
		this.screen.init(this);
	}
	
	public void tick(InputHandler input) { 
		if(input.restart.clicked) {
			if(this.screen instanceof WinScreen) newGame();
			else restartLevel();
		}
		
		this.screen.tick(input); 
	}
	
	public void render(Bitmap screen, double delta) { this.screen.render(screen, delta); }
	
	public void newGame() {
		loadLevel(0);
		setScreen(new GameScreen(this.level, this.player));
	}
	
	public void nextLevel() { loadLevel(this.level.num + 1); }
	public void restartLevel() { loadLevel(this.level.num); }
	
	public void loadLevel(int num) {
		this.level = new Level(num);
		this.player.init(this.level);
		setScreen(new GameScreen(this.level, this.player));
	}
	
	public void gameOver() {
		setScreen(new GameOverScreen((GameScreen)this.screen));
	}
	
	public void win() {
		setScreen(new WinScreen((GameScreen)this.screen));		
	}
	
}