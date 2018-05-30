package egviz;

public class Node
{
	public final Object e;
	double x, y;

	public Node(Object e)
	{
		this.e = e;
		shuffle();
	}

	public void shuffle()
	{
		x = Math.random();
		y = Math.random();
	}
}
