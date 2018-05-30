package egviz;

public class SpringLayout extends ForceBasedAlgo
{

	@Override
	public void step(JGraph g)
	{
		for (Node u : g.nodes)
		{
			for (Node v : g.nodes)
			{
				double dx = u.x - v.x;
				double factor = g.neighbors(u, v) != 0 && Math.abs(dx) > 20 ? attractionFactor
						: repulsionFactor;

				double xshift = dx * factor / 2 - dx;
				u.x -= xshift;
				v.x += xshift;

				double dy = u.y - v.y;
				double yshift = dy * factor / 2 - dy;
				u.y -= yshift;
				v.y += yshift;
			}
		}
	}
}
