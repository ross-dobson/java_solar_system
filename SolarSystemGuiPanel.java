package module9;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *  Panel with start, stop, restart and exit buttons for solar system animation.
 *  @author Ross Dobson
 *  @version 1.2 (2019-12-20)
 */
public class SolarSystemGuiPanel extends JPanel implements ActionListener {
	private SolarSystemAnimPanel animPanel; // panel containing animation
	private JButton startButton;
	private JButton stopButton;
	private JButton resetButton;
	private JButton exitButton;

	/** Create JPanel containing the animation panel and control buttons. */
	public SolarSystemGuiPanel() {
		super();
		setPreferredSize(new Dimension(1000,1000));
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

		animPanel = new SolarSystemAnimPanel(500,500);
		
		startButton = new JButton("Start");
		stopButton  = new JButton("Stop");
		resetButton = new JButton("Reset");
		exitButton = new JButton("Exit");

		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		resetButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		// make a panel for buttons, add the buttons to it
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel,BoxLayout.X_AXIS));
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(exitButton);

		// add animation and button panels to the GUI panel
		add(animPanel);
		add(buttonPanel);
	}

	/* Respond to the buttons being clicked */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==startButton) start();
		else if (e.getSource()==stopButton) stop();
		else if (e.getSource()==resetButton) reset();
		else if (e.getSource()==exitButton) System.exit(0);
	}

	/** Start animation */
	public void start() {animPanel.start();}

	/** Stop animation */
	public void stop() {animPanel.stop();}
	
	/** Reset animation */
	public void reset() {animPanel.setDays(0);}
}