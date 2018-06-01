package egviz;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Demo
{
	public static void main(String[] args)
	{
		// creates a graphical graph using "wandering nodes" layout algorithm
		JGraph g = new JGraph(new WanderingNodes())
		{
			@Override
			public int arcType(Node src, Node dest)
			{
				// if "Luc" preceeds "Cle"
				if (src.e.equals("Luc") && dest.e.equals("Cle"))
					return 1;
				if (src.e.equals("Luc") && dest.e.equals("Elisa"))
					return 1;
				if (src.e.equals("Luc") && dest.e.equals("Lucien"))
					return 2;
				if (src.e.equals("Nad") && dest.e.equals("Cle"))
					return 1;
				if (src.e.equals("Nad") && dest.e.equals("Elisa"))
					return 1;
				if (src.e.equals("Nicole") && dest.e.equals("Nad"))
					return 1;
				if (src.e.equals("Mitou") && dest.e.equals("Luc"))
					return 1;
				if (src.e.equals("Bubus") && dest.e.equals("Luc"))
					return 1;
				if (src.e.equals("Bubus") && dest.e.equals("Animal"))
					return 1;
				if (src.e.equals("Elisa") && dest.e.equals("vers a soie"))
					return 2;

				return 0;
			}

			@Override
			public String getText(Node u)
			{
				return u.e.toString();
			}

			@Override
			public Color getColor(Node u)
			{
				return Color.black;
			}

			@Override
			public Color getFillColor(Node u)
			{
				if (u.e.equals("Luc"))
					return Color.blue;

				if (u.e.equals("Nad"))
					return Color.pink;

				return Color.white;
			}

			@Override
			public int getSize(Node u)
			{
				if (u.e.equals("Luc"))
					return 20;

				if (u.e.equals("Animal"))
					return 150;

				return 10;
			}

			@Override
			public ImageIcon getIcon(Node u)
			{
				if (u.e.equals("Animal"))
					return new ImageIcon(getClass().getResource("animal.png"));

				return null;
			}
		};

		g.addNode("Luc");
		g.addNode("Nad");
		g.addNode("Elisa");
		g.addNode("Cle");
		g.addNode("Mitou");
		g.addNode("Bubus");
		g.addNode("Nicole");
		g.addNode("Lucien");
		g.addNode("vers a soie");
		g.addNode("Animal");

		JFrame f = new JFrame("graph view");
		f.setSize(800, 600);

		f.setContentPane(g.getBundleComponent());
		f.setVisible(true);
		g.start();
		System.out.println("coucou :)");
	}
}
