package egviz;

import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class WanderingNode extends PlacementAlgo
{
	class MoveNode extends Node
	{
		double xx, yy;

		public MoveNode(Object e)
		{
			super(e);
		}
	}

	Random r = new Random();

	@Override
	public void step(JGraph g)
	{
		for (Node u : g.nodes)
		{
			MoveNode mm = (MoveNode) u;
			mm.xx += (r.nextDouble() - 0.5) / 100;
			u.x += mm.xx;

			if (u.x < 0)
			{
				u.x = 0;
				mm.xx = - mm.xx;
			}

			if (u.x > 1)
			{
				u.x = 1;
				mm.xx = - mm.xx;
			}

			u.y += (r.nextDouble() - 0.5) / 100;

			if (u.y < 0)
			{
				u.y = 0;
				mm.yy = - mm.yy;
			}

			if (u.y > 1)
			{
				u.y = 1;
				mm.yy = - mm.yy;
			}
		}
	}

	@Override
	public JComponent getControls()
	{
		return new JLabel("no algo control");
	}

	@Override
	public Node createNode(Object e)
	{
		return new MoveNode(e);
	}

}
