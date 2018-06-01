package egviz;

import java.awt.Rectangle;

public class SpringLayout extends ForceBasedAlgo
{

	@Override
	public long step(JGraph g, Rectangle r)
	{
		long n = 0;

		for (Node u : g.nodes)
		{
			for (Node v : g.nodes)
			{
				if (u != v)
				{
					double dx = u.x - v.x;
					double dy = u.y - v.y;
					double d = Math.sqrt(dx * dx + dy * dy);
					double maxDistance = Math
							.sqrt(r.width + r.width + r.height + r.height);
					double force = Math.pow(d / maxDistance, 2);

					double direction = g.arcType(u, v) != 0 && g.arcType(v, u) != 0
							? attractionFactor
							: repulsionFactor;

					force *= direction;

					double xshift = dx * force - dx;
					u.x = (int) ensureBounds(u.x - xshift, r.x, r.x + r.width);
					v.x += (int) ensureBounds(v.x + xshift, r.x, r.x + r.width);

					double yshift = dy * force / 2 - dy;
					u.y = (int) ensureBounds(u.y - yshift, r.y, r.y + r.height);
					v.y += (int) ensureBounds(v.y + yshift, r.y, r.y + r.height);

					if (xshift > 0 || yshift > 0)
						++n;
				}
			}
		}

		return n;
	}

	private double ensureBounds(double x, double lb, double up)
	{
		if (x < lb)
			return lb;

		if (x > up)
			return up;

		return x;
	}
}
