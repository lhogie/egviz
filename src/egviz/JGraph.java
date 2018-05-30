package egviz;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class JGraph extends JPanel
{
	private final PlacementAlgo algo;
	 List<Node> nodes = new ArrayList<>();
	long pauseDuration = 1000;
	int margin = 50;
	Stroke stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

	public JGraph()
	{
		this(new SpringLayout());
	}

	public JGraph(PlacementAlgo algo)
	{
		this.algo = algo;
		setBorder(new EmptyBorder(margin, margin, margin, margin));
	}

	public void start()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while (true)
				{
					algo.step(JGraph.this);

					try
					{
						Thread.sleep(pauseDuration);
					}
					catch (InterruptedException e)
					{
					}
					
					repaint();
				}
			}
		}).start();
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;

		g2d.setStroke(stroke);

		Dimension size = getSize();
		Insets insets = getInsets();
		double w = size.width - insets.left - insets.right;
		double h = size.height - insets.top - insets.bottom;

		LogicalBounds bounds = getGraphBounds();
		double xfact = w / bounds.width();
		double xshift = - bounds.xmin;
		double yfact = h / bounds.height();
		double yshift = - bounds.ymin;

		for (Node u : nodes)
		{
			for (Node v : nodes)
			{
				if (neighbors(u, v) != 0)
				{
					int x1 = (int) ((u.x + xshift) * xfact) + margin;
					int y1 = (int) ((u.y + yshift) * yfact) + margin;
					int x2 = (int) ((v.x + xshift) * xfact) + margin;
					int y2 = (int) ((v.y + yshift) * yfact) + margin;
					g2d.drawLine(x1, y1, x2, y2);
				}
			}
		}

		for (Node u : nodes)
		{
			int usize = getSize(u);

			if (usize > 0)
			{
				int x = (int) ((u.x + xshift) * xfact) + margin;
				int y = (int) ((u.y + yshift) * yfact) + margin;

				String text = getText(u);

				if (text == null)
				{
					g.setColor(getFillColor(u));
					g.fillOval(x - usize / 2, y - usize / 2, usize, usize);
					g.setColor(getColor(u));
					g.drawOval(x - usize / 2, y - usize / 2, usize, usize);
				}
				else
				{
					FontRenderContext frc = g2d.getFontRenderContext();
					Font font = getFont().deriveFont((float) usize);
					GlyphVector gv = font.createGlyphVector(frc, u.e.toString());
					int textW = (int) gv.getVisualBounds().getWidth();
					int textH = (int) gv.getVisualBounds().getHeight();

					g.setColor(getFillColor(u));
					int gap = 6;
					g2d.fillRect(x - textW / 2 - gap, y - textH / 2 - gap,
							textW + 2 * gap, textH + 2 * gap);
					g.setColor(getColor(u));
					g2d.drawRect(x - textW / 2 - gap, y - textH / 2 - gap,
							textW + 2 * gap, textH + 2 * gap);
					g2d.drawGlyphVector(gv, x - textW / 2, y + textH / 2 + 2);
				}

			}
		}
	}

	public abstract int getSize(Node u);

	public abstract String getText(Node u);

	public abstract Color getColor(Node u);

	public abstract Color getFillColor(Node u);

	LogicalBounds getGraphBounds()
	{
		LogicalBounds r = new LogicalBounds();

		for (Node u : nodes)
			r.update(u);

		return r;
	}

	static class LogicalBounds
	{
		double xmin = Double.MAX_VALUE, xmax = Double.MIN_VALUE, ymin = Double.MAX_VALUE,
				ymax = Double.MIN_VALUE;

		void update(Node n)
		{
			if (n.x < xmin)
				xmin = n.x;
			else if (n.x > xmax)
				xmax = n.x;

			if (n.y < ymin)
				ymin = n.y;
			else if (n.y > ymax)
				ymax = n.y;
		}

		double width()
		{
			return xmax - xmin;
		}

		double height()
		{
			return ymax - ymin;
		}

	}

	protected abstract int neighbors(Node u, Node v);

	public void shuffle()
	{
		for (Node u : nodes)
		{
			u.shuffle();
		}
	}

	public JComponent getControls()
	{
		JPanel p = new JPanel(new GridLayout(2,  1));
		p.add(new GraphControl(this));
		p.add(algo.getControls());
		return p;
	}

	public Container getBundleComponent()
	{
		JPanel p = new JPanel(new BorderLayout());
		p.add(this, BorderLayout.CENTER);
		p.add(getControls(), BorderLayout.SOUTH);
		return p;
	}

	public void add(Object nodeElement)
	{
		nodes.add(new Node(nodeElement));
	}

}
