package game.gfx;

public class Font {

	private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ      0123456789.,!?'\"-+=/\\%()<>[]:;  abcdefghijklmnopqrstuvwxyz      ";
	
	public static void draw(String text, Bitmap screen, int xp, int yp) {
		for(int i = 0; i < text.length(); i++) {
			int ix = chars.indexOf(text.charAt(i));
			if(ix < 0) continue;
			
			int xx = ix % 32;
			int yy = ix / 32;
			screen.draw(Art.font[xx + yy * 32], xp + i * 7, yp);
		}
	}
	
	public static void draw(String text, Bitmap screen, int xp, int yp, int scale) {
		for(int i = 0; i < text.length(); i++) {
			int ix = chars.indexOf(text.charAt(i));
			if(ix < 0) continue;
			
			int xx = ix % 32;
			int yy = ix / 32;
			screen.scaleDraw(Art.font[xx + yy * 32], xp + i * 7 * scale, yp, scale, scale);
		}
	}
	
	public static void draw(String text, Bitmap screen, int xp, int yp, int scale, int col) {
		for(int i = 0; i < text.length(); i++) {
			int ix = chars.indexOf(text.charAt(i));
			if(ix < 0) continue;
			
			int xx = ix % 32;
			int yy = ix / 32;
			screen.scaleDraw(Art.recolor(Art.font[xx + yy * 32], col), xp + i * 7 * scale, yp, scale, scale);
		}
	}
	
}