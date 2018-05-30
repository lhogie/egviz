package egviz;

import javax.swing.JComponent;

public abstract class PlacementAlgo
{
	public abstract void step(JGraph g);

	public abstract JComponent getControls();

	public Node createNode(Object e)
	{
		return new Node(e);
	}

}
