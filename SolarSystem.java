package module9;

import javax.swing.*;

/**
 * Animation of the four most-interior planets of the Solar System, and Earth's moon
 * on circular orbits. Ability to pause and reset the animation, and toggle detail labels
 * @author Ross Dobson
 * @version 1.0 (2019-12-20)
 */
public class SolarSystem {

	/** Create and display JFrame containing the animation GUI panel */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("Solar System Animation");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setSize(750,750);
				JPanel panel = new SolarSystemGuiPanel();
				frame.add(panel);
				frame.setVisible(true);
			}
		});
	}
}
