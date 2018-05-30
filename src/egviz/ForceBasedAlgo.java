package egviz;

import javax.swing.JComponent;

public abstract class ForceBasedAlgo extends PlacementAlgo
{
	protected double attractionFactor = 0.9;
	protected double repulsionFactor = 1.2;

	@Override
	public JComponent getControls()
	{
		return new ForceBasedControls(this);
	}
}
