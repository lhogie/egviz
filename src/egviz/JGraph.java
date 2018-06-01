package egviz;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public abstract class JGraph extends JPanel implements Graph
{
	private final Layout algo;
	List<Node> nodes = new ArrayList<>();
	long pauseDuration = 30;
	Stroke stroke = new BasicStroke(2.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	private Node selectedNode;

	int mt, mr, mb, ml;
	Rectangle layoutArea;

	public JGraph()
	{
		this(new SpringLayout());
	}

	public JGraph(Layout algo)
	{
		this.algo = algo;
		setBorder(new EmptyBorder(mr, ml, mb, mr));

		addMouseListener(new MouseAdapter()
		{
			MouseMotionListener ml = new MouseMotionAdapter()
			{
				@Override
				public void mouseDragged(MouseEvent e)
				{
					selectedNode.x = e.getX();
					selectedNode.y = e.getY();
				}
			};

			@Override
			public void mouseReleased(MouseEvent e)
			{
				selectedNode.isSelected = false;
				removeMouseMotionListener(ml);
				selectedNode = null;
			}

			@Override
			public void mousePressed(MouseEvent e)
			{
				selectedNode = findClosestNode(e.getX(), e.getY());
				selectedNode.isSelected = true;
				addMouseMotionListener(ml);
			}

			private Node findClosestNode(int x, int y)
			{
				double minD = Double.MAX_VALUE;
				Node minN = null;

				for (Node n : nodes)
				{
					double dx = Math.abs(x - n.x);
					double dy = Math.abs(y - n.y);
					double d = Math.sqrt(dx * dx + dy * dy);

					if (d < minD)
					{
						minD = d;
						minN = n;
					}
				}

				return minN;
			}
		});
	}

	public void start()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				int nbSteps = 0;

				while (true)
				{
					if (isVisible())
					{
						updateLayoutArea();

						if (layoutArea.width > 0 && layoutArea.height > 0)
						{
							if (nbSteps == 0)
								shuffle();

							algo.step(JGraph.this, layoutArea);
							++nbSteps;
						}
					}

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

	private void updateLayoutArea()
	{
		int ml = 50;
		int mr = 50;
		int mb = 50;
		int mt = 50;

		Dimension size = getSize();
		Insets insets = getInsets();

		if (layoutArea == null)
			layoutArea = new Rectangle();

		layoutArea.x = getInsets().left + ml;
		layoutArea.y = getInsets().right + mt;
		layoutArea.width = size.width - insets.left - insets.right - ml - mr;
		layoutArea.height = size.height - insets.top - insets.bottom - mb - mt;

	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		doDrawing(g);
	}

	private void doDrawing(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(rh);

		for (Node u : nodes)
		{
			for (Node v : nodes)
			{
				int t = arcType(u, v);

				if (t != 0)
				{
					g2.drawLine((int) u.x, (int) u.y, (int) v.x, (int) v.y);
				}
			}
		}

		g2.setStroke(stroke);

		for (Node u : nodes)
		{
			int usize = getSize(u);

			if (usize > 0)
			{
				ImageIcon icon = getIcon(u);
				String text = getText(u);

				if (icon != null)
				{
					icon = new ImageIcon(
							icon.getImage().getScaledInstance( - 1, getSize(u), 0));

					g.drawImage(icon.getImage(), (int) u.x - icon.getIconWidth() / 2,
							(int) u.y - icon.getIconHeight() / 2, this);
				}
				else if (text != null)
				{
					FontRenderContext frc = g2.getFontRenderContext();
					Font font = getFont().deriveFont((float) usize);
					GlyphVector gv = font.createGlyphVector(frc, u.e.toString());
					int textW = (int) gv.getVisualBounds().getWidth();
					int textH = (int) gv.getVisualBounds().getHeight();

					g.setColor(getFillColor(u));
					int gap = 6;
					g2.fillRect((int) u.x - textW / 2 - gap, (int) u.y - textH / 2 - gap,
							textW + 2 * gap, textH + 2 * gap);
					g.setColor(getColor(u));
					g2.drawRect((int) u.x - textW / 2 - gap, (int) u.y - textH / 2 - gap,
							textW + 2 * gap, textH + 2 * gap);
					g2.drawGlyphVector(gv, (int) u.x - textW / 2,
							(int) u.y + textH / 2 + 2);
				}
				else
				{
					g.setColor(getFillColor(u));
					g.fillOval((int) u.x - usize / 2, (int) u.y - usize / 2, usize,
							usize);
					g.setColor(getColor(u));
					g.drawOval((int) u.x - usize / 2, (int) u.y - usize / 2, usize,
							usize);
				}
			}
		}
	}

	public void shuffle()
	{
		for (Node u : nodes)
		{
			u.shuffle(layoutArea);
		}
	}

	public JComponent getControls()
	{
		JPanel p = new JPanel(new GridLayout(2, 1));
		p.add(new GraphControls(this));
		p.add(algo.getControls());
		return p;
	}

	public JComponent getBundleComponent()
	{
		JPanel p = new JPanel(new BorderLayout());
		p.add(this, BorderLayout.CENTER);
		p.add(getControls(), BorderLayout.SOUTH);
		return p;
	}

	public void addNode(Object nodeElement)
	{
		nodes.add(algo.createNode(nodeElement));
	}

	public List<Node> getNodes()
	{
		return nodes;
	}

}
