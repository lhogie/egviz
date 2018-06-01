package egviz;

import java.awt.Rectangle;
import java.awt.font.GlyphVector;
import java.util.Random;

import javax.swing.ImageIcon;

public class Node
{
	static Random prng = new Random();
	public final Object e;
	int x, y;
	boolean isSelected;
	public ImageIcon cache_icon;
	public boolean cache_icon_valid;
	public boolean cache_text_valid;
	public GlyphVector cache_text;

	public Node(Object e)
	{
		this.e = e;
	}

	public void shuffle(Rectangle r)
	{
		x = prng.nextInt(r.width) + r.x;
		y = prng.nextInt(r.height) + r.y;
	}
}
