package egviz;

import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * Describes an iterative layout algorithm
 * @author lhogie
 *
 */

public abstract class Layout
{
	/*
	 * What does each step of the algorithm.
	 */
	public abstract long step(JGraph g, Rectangle r);

	/*
	 * Returns a Swing component which contains the graphical controllers for the given
	 * algorithm.
	 */
	public abstract JComponent getControls();

	
	/*
	 * create a node class specific to this algo needs.
	 */
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
