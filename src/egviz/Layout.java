package egviz;

import java.awt.Rectangle;

import javax.swing.JComponent;

public abstract class Layout
{
	public abstract long step(JGraph g, Rectangle r);

	public abstract JComponent getControls();

	public Node createNode(Object e)
	{
		return new Node(e);
	}

	public void run(JGraph g, Rectangle r)
	{
		while (step(g, r) > 0)
			;
	}
}
