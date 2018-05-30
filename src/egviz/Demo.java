package egviz;

import java.awt.Color;

import javax.swing.JFrame;

public class Demo
{
	public static void main(String[] args)
	{
		JGraph g = new JGraph(new SpringLayout())
		{
			@Override
			protected int neighbors(Node u, Node v)
			{
				if (((String) u.e).compareTo((String) v.e) < 0)
				{
					Node tmp = u;
					u = v;
					v = tmp;
				}

				if (u.e.equals("Luc") && v.e.equals("Cle"))
					return 1;
				if (u.e.equals("Luc") && v.e.equals("Elisa"))
					return 1;
				if (u.e.equals("Lucien") && v.e.equals("Luc"))
					return 1;
				if (u.e.equals("Nad") && v.e.equals("Cle"))
					return 1;
				if (u.e.equals("Nad") && v.e.equals("Elisa"))
					return 1;
				if (u.e.equals("Nicole") && v.e.equals("Nad"))
					return 1;
				if (u.e.equals("Mitou") && v.e.equals("Luc"))
					return 1;
				if (u.e.equals("Luc") && v.e.equals("Bubus"))
					return 1;
				if (u.e.equals("vers a soie") && v.e.equals("Elisa"))
					return 1;

				return 2;
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

				return 10;
			}
		};

		g.add("Luc");
		g.add("Nad");
		g.add("Elisa");
		g.add("Cle");
		g.add("Mitou");
		g.add("Bubus");
		g.add("Nicole");
		g.add("Lucien");
		g.add("vers a soie");

		JFrame f = new JFrame("graph view");
		f.setSize(800, 600);

		f.setContentPane(g.getBundleComponent());
		f.setVisible(true);
		g.start();
		System.out.println("coucou :)");
	}
}
