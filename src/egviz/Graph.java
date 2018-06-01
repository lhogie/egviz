package egviz;

import java.awt.Color;

import javax.swing.ImageIcon;

public interface Graph
{

	int getSize(Node u);

	String getText(Node u);

	Color getColor(Node u);

	Color getFillColor(Node u);

	ImageIcon getIcon(Node u);

	int arcType(Node u, Node v);
}
