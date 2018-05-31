package egviz;

import java.awt.Rectangle;
import java.util.Random;

public class Node
{
	static Random prng = new Random();
	public final Object e;
	double x, y;

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
