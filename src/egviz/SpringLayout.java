package egviz;

import java.awt.Rectangle;

public class SpringLayout extends ForceBasedAlgo
{

	@Override
	public void step(JGraph g, Rectangle r)
	{

		for (Node u : g.nodes)
		{
			for (Node v : g.nodes)
			{
				if (u != v)
				{
					int dx = u.x - v.x;
					int dy = u.y - v.y;
					double d = Math.sqrt(dx * dx + dy * dy);
					double maxDistance = Math
							.sqrt(r.width + r.width + r.height + r.height);
					double force = Math.pow(d / maxDistance, 2);

					double direction = g.arcType(u, v) != 0 && g.arcType(v, u) != 0
							? attractionFactor
							: repulsionFactor;

					force *= direction;

					int xshift = (int) (dx * force - dx);
					u.x = ensureBounds(u.x - xshift, r.x, r.x + r.width);
					v.x += ensureBounds(v.x + xshift, r.x, r.x + r.width);

					int yshift = (int) (dy * force / 2 - dy);
					u.y = ensureBounds(u.y - yshift, r.y, r.y + r.height);
					v.y += ensureBounds(v.y + yshift, r.y, r.y + r.height);
				}
			}
		}
	}

	private int ensureBounds(int x, int lb, int up)
	{
		if (x < lb)
			return lb;

		if (x > up)
			return up;

		return x;
	}
}
