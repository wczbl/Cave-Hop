package game;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class InputHandler implements KeyListener {

	public List<Key> keys = new ArrayList<Key>();
	public Key up = new Key();
	public Key down = new Key();
	public Key left = new Key();
	public Key right = new Key();
	public Key jump = new Key();
	public Key restart = new Key();
	public Key next = new Key();
	public Key prev = new Key();
	public Key useSkill = new Key();
	
	public InputHandler(Canvas canvas) { canvas.addKeyListener(this); }
	
	public void tick() {
		for(Key key : this.keys) {
			key.tick();
		}
	}
	
	public void releaseAll() {
		for(Key key : this.keys) {
			key.down = false;
		}
	}
	
	public void keyTyped(KeyEvent e) {}
	public void keyPressed(KeyEvent e) { toggle(e, true); }
	public void keyReleased(KeyEvent e) { toggle(e, false); }
	
	private void toggle(KeyEvent e, boolean pressed) {
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD8 || e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) this.up.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD2 || e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) this.down.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD4 || e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) this.left.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD6 || e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) this.right.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_F || e.getKeyCode() == KeyEvent.VK_0) this.useSkill.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_SPACE) this.jump.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_R) this.restart.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_Q) this.prev.toggle(pressed);
		if(e.getKeyCode() == KeyEvent.VK_E) this.next.toggle(pressed);
	}
	
	public class Key {
		private int absorbs;
		private int presses;
		
		public boolean down;
		public boolean clicked;
		
		public Key() { InputHandler.this.keys.add(this); }
		
		public void tick() {
			if(this.absorbs < this.presses) {
				this.absorbs++;
				this.clicked = true;
			} else this.clicked = false;
		}
		
		public void toggle(boolean pressed) {
			if(pressed != this.down) this.down = pressed;
			if(pressed) this.presses++;
		}
	}

}