package game;

import java.util.ArrayList;
import java.util.List;

import game.entity.hopper.Hopper;
import game.gfx.Art;
import game.level.Level;

public class Player {

	public List<Hopper> hoppers = new ArrayList<Hopper>();
	public int index;
	private Level level;
	private Game game;
	private Hopper hopper;
	private boolean complete;
	
	public Player(Game game) {
		this.index = 0;
		this.game = game;
	}
	
	public void init(Level level) {
		this.level = level;
		this.hoppers = this.level.hoppers;
	}
	
	public Hopper getAlive() {
		int completed = 0;
		for(int i = 0; i < this.hoppers.size(); i++) {
			Hopper hopper = this.hoppers.get(i);
			if(hopper.completed) completed++;
			
			if(!hopper.removed) {
				this.index = i;
				return hopper;
			}
		}
		
		this.complete = completed == this.hoppers.size();
		return null;
	}
	
	public Hopper get(int index) {
		Hopper result = null;
		if(!this.hoppers.isEmpty()) {
			result = this.hoppers.get(index % this.hoppers.size());
			if(result.removed) result = getAlive();
			if(result != null && this.hopper != null && this.hopper != result) result.select();
		}
		
		return result;
	}
	
	public void tick(InputHandler input) {
		if(input.prev.clicked) {
			this.index--;
			if(this.index <= -1) this.index = this.hoppers.size() - 1;
		}
	
		if(input.next.clicked) {
			this.index++;
			if(this.index >= this.hoppers.size()) this.index = 0;
		}
		
		this.hopper = get(this.index);
		
		if(this.hopper != null) this.hopper.tick(input);
		else {
			if(complete) {
				if(this.level.num == Art.levels.length - 1) this.game.win();
				else this.game.nextLevel();
			} else this.game.gameOver();
		}
	}
	
}