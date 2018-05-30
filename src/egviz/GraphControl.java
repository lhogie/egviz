package egviz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GraphControl extends JPanel
{
	JButton shuffleButton = new JButton("Shuffle");
	JSlider pauseSlider = new JSlider(0, 100, 50);
	
	public GraphControl(JGraph g)
	{
		add(shuffleButton);
		add(new JLabel("Pause duration"));
		add(pauseSlider);
		pauseSlider.setName("pause");
		
		shuffleButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				g.shuffle();
			}
		});
		
		pauseSlider.addChangeListener(new ChangeListener()
		{
			
			@Override
			public void stateChanged(ChangeEvent e)
			{
				g.pauseDuration  = pauseSlider.getValue();
				
			}
		});
	}
}
