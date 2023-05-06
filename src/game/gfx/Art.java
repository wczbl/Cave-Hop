package game.gfx;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {

	public static final Bitmap[] sprites = loadAndCut("/sprites.png", 16, 16, 2);
	public static final Bitmap[] tiles = loadAndCut("/tiles.png", 16, 16, 2);
	public static final Bitmap[] levels = loadAndCut("/levels.png", 10, 7);
	public static final Bitmap[] font = loadAndCut("/font.png", 8);
	public static final Bitmap[] gui = loadAndCut("/gui.png", 8);
	public static final Bitmap[] particles = loadAndCut("/particles.png", 8);
	public static final Bitmap title = load("/title.png");
	public static final Bitmap background = load("/background.png");
	public static final Bitmap guipanel = load("/guipanel.png");
	
	public static Bitmap load(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(Art.class.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + path);
		}
		
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return new Bitmap(result);
	}
	
	public static Bitmap[] loadAndCut(String path, int size) { return loadAndCut(path, size, size, 1); }
	public static Bitmap[] loadAndCut(String path, int w, int h) { return loadAndCut(path, w, h, 1); }
	
	public static Bitmap[] loadAndCut(String path, int w, int h, int scale) {
		BufferedImage sheet = null;
		try {
			sheet = ImageIO.read(Art.class.getResourceAsStream(path));
		} catch (IOException e) {
			throw new RuntimeException("Failed to load " + path);
		}
		
		int sw = sheet.getWidth() / w;
		int sh = sheet.getHeight() / h;
		Bitmap[] result = new Bitmap[sw * sh];
		for(int y = 0; y < sh; y++) {
			for(int x = 0; x < sw; x++) {
				BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
				Graphics g = image.getGraphics();
				g.drawImage(sheet, -x * w, -y * h, null);
				g.dispose();
				result[x + y * sw] = new Bitmap(scale(image, scale));
			}			
		}			
		
		return result;
	}
	
	public static BufferedImage scale(BufferedImage src, int scale) {
		if(scale <= 1) return src;
	
		int sw = src.getWidth() * scale;
		int sh = src.getHeight() * scale;
		BufferedImage result = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_ARGB);
		Graphics g = result.getGraphics();
		g.drawImage(src.getScaledInstance(sw, sh, 16), 0, 0, null);
		g.dispose();
		return result;
	}
	
	public static Bitmap recolor(Bitmap b, int col) {
		Bitmap result = new Bitmap(b.w, b.h);
		for(int i = 0; i < b.w * b.h; i++) {
			int c = b.pixels[i];
			if(c == 0xFFFFFFFF) result.pixels[i] = 0xFF000000 | col;
			else result.pixels[i] = c;
		}
		
		return result;
	}
	
}