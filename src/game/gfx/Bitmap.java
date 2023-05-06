package game.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import game.tools.MathUtils;

public class Bitmap {

	public final int w;
	public final int h;
	public int[] pixels;

	public boolean flipped;
	public int xOffs;
	public int yOffs;
	
	public Bitmap(BufferedImage image) {
		this.w = image.getWidth();
		this.h = image.getHeight();
		this.pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	}

	public Bitmap(int w, int h) {
		this.w = w;
		this.h = h;
		this.pixels = new int[w * h];
	}

	public Bitmap(int w, int h, int[] pixels) {
		this.w = w;
		this.h = h;
		this.pixels = pixels;
	}
	
	public void clear(int col) {
		for(int i = 0; i < this.pixels.length; i++) {
			this.pixels[i] = col;
		}
	}
	
	public void setPixel(int xp, int yp, int col) {
		xp += this.xOffs;
		yp += this.yOffs;
		
		if(xp >= 0 && yp >= 0 && xp < this.w && yp < this.h) {
			this.pixels[xp + yp * this.w] = col;
		}
	}
	
	public void draw(Bitmap b, int xp, int yp) {
		xp += this.xOffs;
		yp += this.yOffs;
	
		int x0 = xp;
		int y0 = yp;
		int x1 = xp + b.w;
		int y1 = yp + b.h;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 > this.w) x1 = this.w;
		if(y1 > this.h) y1 = this.h;
		
		for(int y = y0; y < y1; y++) {
			int sp = this.flipped ? (y - yp) * b.w + xp + b.w - 1 : (y - yp) * b.w - xp;
			int dp = y * this.w;
			for(int x = x0; x < x1; x++) {
				int src = this.flipped ? b.pixels[sp - x] : b.pixels[sp + x];
				if((src >> 24) == 0) continue;
				
				int dst = this.pixels[dp + x];
				int blended = MathUtils.blendColor(src, dst);
				this.pixels[dp + x] = blended;
			}
		}
	}
	
	public void draw(Bitmap b, int xp, int yp, int xOffs, int yOffs, int w, int h) {
		xp += this.xOffs;
		yp += this.yOffs;
	
		int x0 = xp;
		int y0 = yp;
		int x1 = xp + w;
		int y1 = yp + h;
		
		if(x0 < 0) x0 = 0;
		if(y0 < 0) y0 = 0;
		if(x1 > this.w) x1 = this.w;
		if(y1 > this.h) y1 = this.h;
		for(int y = y0; y < y1; y++) {
			int sp = (y - y0 + yOffs) * b.w + xOffs;
			int dp = y * this.w;
			for(int x = x0; x < x1; x++) {
				int src = b.pixels[sp++];
				if(src == 0) continue;
				this.pixels[dp + x] = src;
			}
		}
	}
		
	public void scaleDraw(Bitmap b, int xp, int yp, double scaleX, double scaleY) {
		int scaledW = (int) (b.w * scaleX);
		int scaledH = (int) (b.h * scaleY);
		int[] scaledPixels = new int[scaledW * scaledH];
			
		for (int y = 0; y < scaledH; y++) {
			int oldY = (int) (y / scaleY);
			int yOffset = oldY * b.w;
			
			for (int x = 0; x < scaledW; x++) {
				int oldX = (int) (x / scaleX);
				int oldIndex = yOffset + oldX;
				
				int c = b.pixels[oldIndex];
				int alpha = (c >> 24) & 0xff;
				int red = (c >> 16) & 0xff;
				int green = (c >> 8) & 0xff;
				int blue = c & 0xff;
				
				if (alpha > 0) {
					int blendedColor = (alpha << 24) | (red << 16) | (green << 8) | blue;
					scaledPixels[x + y * scaledW] = blendedColor;
				}
			}
		}
			
		Bitmap scaled = new Bitmap(scaledW, scaledH, scaledPixels);
		draw(scaled, xp, yp);
	}
	
	public Bitmap rotate(double angle) {
	    if(angle == 0) return this;

	    double cos = Math.cos(angle);
	    double sin = Math.sin(angle);

	    int cx = this.w / 2;
	    int cy = this.h / 2;

	    int newW = (int)Math.round(Math.abs(this.w * cos) + Math.abs(this.h * sin));
	    int newH = (int)Math.round(Math.abs(this.w * sin) + Math.abs(this.h * cos));

	    int[] result = new int[newW * newH];

	    for(int y = 0; y < newH; y++) {
	        for(int x = 0; x < newW; x++) {
	            double xo = (x - newW / 2) * cos + (y - newH / 2) * sin;
	            double yo = -(x - newW / 2) * sin + (y - newH / 2) * cos;

	            int xx = (int)Math.round(xo) + cx;
	            int yy = (int)Math.round(yo) + cy;

	            if(xx >= 0 && yy >= 0 && xx < this.w && yy < this.h) {
	                int c = this.pixels[xx + yy * this.w];
	                result[x + y * newW] = c;
	            }
	        }
	    }

		Bitmap rotated = new Bitmap(newW, newH, result);
		return rotated;
	}
}