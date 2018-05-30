package egviz;

import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class BrownianMovingNodes extends PlacementAlgo
{
	private Random r = new Random();

	@Override
	public void step(JGraph g)
	{
		for (Node u : g.nodes)
		{
			u.x += (r.nextDouble() - 0.5) / 100;

			if (u.x < 0)
				u.x = 0;

			if (u.x > 1)
				u.x = 1;

			u.y += (r.nextDouble() - 0.5) / 100;

			if (u.y < 0)
				u.y = 0;

			if (u.y > 1)
				u.y = 1;
		}
	}

	@Override
	public JComponent getControls()
	{
		return new JLabel("no algo control");
	}

}
