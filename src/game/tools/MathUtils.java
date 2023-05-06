package game.tools;

public class MathUtils {

	public static int blendColor(int src, int dst) {
		int srcAlpha = (src >> 24) & 0xff;
		int dstAlpha = (dst >> 24) & 0xff;
		int alpha = srcAlpha + (255 - srcAlpha) * dstAlpha / 255;
		int r = ((src >> 16) & 0xff) * srcAlpha / 255 + ((dst >> 16) & 0xff) * (255 - srcAlpha) / 255;
		int g = ((src >> 8) & 0xff) * srcAlpha / 255 + ((dst >> 8) & 0xff) * (255 - srcAlpha) / 255;
		int b = (src & 0xff) * srcAlpha / 255 + (dst & 0xff) * (255 - srcAlpha) / 255;
		return (alpha << 24) | (r << 16) | (g << 8) | b;
	}
	
}