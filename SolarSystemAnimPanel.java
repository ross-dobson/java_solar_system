package module9;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

/** 
 * Animation of the four most-interior planets of the Solar System, and Earth's moon
 * on circular orbits. Requires a CelestialCircular object storing the name, period,
 * orbital radius, diameter, and colour, and then calculates and draws a circular orbit
 * from that information.
 * @author Ross Dobson
 * @version 1.3 (2019-12-20)
 */
public class SolarSystemAnimPanel extends JPanel implements ActionListener, MouseListener {

	private final int delay = 10; // delay in ms between steps
	private int dt = 0; // each time-step is 0.1 days in 'space'
	// this leads to a acceptable middle ground between frame rate and orbit speed


	private Timer animationTimer; // timer controlling frame rate

	private boolean showDetails = false; // controls whether P, r, d labels displayed
	private boolean hasMoon = false; // prevents pointless exception-handling checks, if no moon

	// an ArrayList to store the celestial objects with circular orbits
	ArrayList<CelestialCircular> circCelestials = new ArrayList<CelestialCircular>();

	/**
	 * Constructor creates panel for animation
	 * @param width width of panel
	 * @param height height of panel
	 */
	public SolarSystemAnimPanel(int width, int height) {
		setPreferredSize(new Dimension(width,height));
		animationTimer = new Timer(delay,this);
		animationTimer.start();
		addMouseListener(this); // listens for mouse clicks

		createCelestials(); 
	}

	/** A method to create the objects orbiting in a circular path
	 * Stores in an ArrayList circCelestials. Exception handling prevents invalid
	 * CelestialCircular objects from being added to the ArrayList. Only the planets
	 * in the ArrayList get used later on.
	 */
	public void createCelestials() {

		/* The other planets have such large diameters and/or orbit radii that they would be 
		 * very difficult to fit into the animation with the first four planets still visible. 
		 * Thus, I haven't included them as I wanted to keep the first four planets to scale. 
		 * Semi-major axis used as radius for circular orbits. All values to 5 s.f., obtained 
		 * from the NASA Space Science Data Coordinated Archive's Planetary Fact sheets.
		 */
		try {
			CelestialCircular mercury = new CelestialCircular("Mercury",87.968,0.38710,4879.4, Color.GRAY);
			circCelestials.add(mercury);
		} catch (Exception e) {
			System.out.println(e);
		} 

		try {
			CelestialCircular venus = new CelestialCircular("Venus",224.69,0.72332,12103, Color.LIGHT_GRAY);
			circCelestials.add(venus);
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			CelestialCircular earth = new CelestialCircular("Earth",365.24,1.0000,12742, Color.CYAN);
			circCelestials.add(earth);
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			CelestialCircular mars = new CelestialCircular("Mars",686.97,1.5235,6779.0, Color.RED);
			circCelestials.add(mars);
		} catch (Exception e) {
			System.out.println(e);
		}

		// real orbital radius r for moon is 0.001AU - took some artistic license here
		try {
			CelestialCircular moon = new CelestialCircular("Moon",27.322, 0.1, 3473.8, Color.WHITE);
			circCelestials.add(moon);
			hasMoon = true; // if exception thrown, this==false. Prevents exception if moon not defined
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * A method that draws a Celestial passed to it in the correct place
	 * by calculating where the Celestial is meant to be based on the data stored in it 
	 * @param g a Java.awt.Graphics object, used to draw components (shapes, strings) & specify colour
	 * @param celestial a CelestialCircular object that stores key data for circular-orbiting objects
	 */
	public void CircularOrbit(Graphics g, CelestialCircular celestial) {
		double P = celestial.getP(); // orbital period in days
		double r = celestial.getR(); // orbital radius in AU
		double d = celestial.getD(); // object diameter in thousands of kilometres

		// scale r and d to a sensible number of pixels, so can actually be seen
		r = r*200; 
		d = d/400;

		// no need for magnitude <= 2PI as Math trigonometric functions deal with it anyway
		double newAngle = (dt*2*Math.PI/(P*10));

		double x = r * Math.cos(newAngle);
		double y = r * Math.sin(newAngle);

		// store x and y - the moon uses the earth's stored values
		celestial.setX(x);
		celestial.setY(y);

		// draw orbital path first
		g.setColor(Color.WHITE);
		g.drawOval((int) (-r), (int) (-r), (int) (r*2), (int) (r*2));

		// draw celestial on top of path
		g.setColor(celestial.getColor());
		g.fillOval((int) (x-(d/2)), (int) (y-(d/2)), (int) d, (int) d);

		// add name label - font size is 10px tall, so shift y by 5 to align
		String name = celestial.getName();
		g.drawString(name, (int) (x+1+d/2), (int) (y+(5)));

		// if toggled, add labels with extra details below name
		if (showDetails) {
			g.drawString(("Period: "+P+" d"), (int) (x+1+d/2), (int) (y+(15)));
			g.drawString(("Orbit radius: "+celestial.getR()+" AU"), (int) (x+1+d/2), (int) (y+(25)));
			g.drawString(("Diameter: "+celestial.getD()+" km"), (int) (x+1+d/2), (int) (y+(35)));
		}
	}

	/* Paints the canvas */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int height = getHeight();
		int width = getWidth();

		// Create the background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		// Move origin to centre of panel
		g.translate(width/2, height/2);

		// Draw the Sun
		g.setColor(Color.YELLOW);
		int sunSize = 40; // pixels, not scientific
		g.fillOval(-sunSize/2, -sunSize/2, sunSize, sunSize);

		// Draw the celestial objects that have circular orbits
		for (CelestialCircular i : circCelestials) {

			// if there's no moon, no need to check for it
			if (!hasMoon) {
				CircularOrbit(g,i); // everything else is a planet orbiting the sun
			}

			// but if there is a moon, need to check for it 
			else if (!i.getName().equals("Moon")) { // don't do moon now - it'd orbit sun not earth
				CircularOrbit(g,i); // do the other planets as normal

				if (i.getName().equals("Earth")) { // but when we get to earth...
					g.translate((int) (i.getX()), (int) (i.getY())); // ...move origin to earth...

					for (CelestialCircular j : circCelestials) {
						if (j.getName().equals("Moon")) { // ...so now the moon will orbit earth
							CircularOrbit(g,j);
						}
					}
					g.translate((int) (-1*i.getX()), (int) (-1*i.getY())); //move origin back to sun
				}
			}
		}

		// add the days label in bottom-right corner
		g.setColor(Color.WHITE);
		String daysLabel = new String("Earth days elapsed: "+(dt/10));
		g.drawString(daysLabel, -width/2, height/2);

		// add the explanatory label about details in top-left corner
		String explainLabel = new String("Click to toggle celestial details!");
		g.drawString(explainLabel, -width/2, (height-20)/2);
	}

	/*
	 * This is called by the animation Timer object
	 * at regular intervals to update the display.
	 */
	public void actionPerformed(ActionEvent event) {
		dt++; // iterate the day counter
		repaint();
	}

	/** Start the animation */
	public void start() {
		animationTimer.start();
	}

	/** Stop the animation */
	public void stop() {
		animationTimer.stop();
	}

	/** Set the amount of days elapsed. Can reset the animation (days=0) via a GUI JButton 
	 * @param days the desired value for the elapsed number of days 
	 */
	public void setDays(int days) {
		this.dt=days*10;
	}

	/* Toggle labels if mouse is clicked */
	public void mouseClicked(MouseEvent e) {
		showDetails = !showDetails; // toggle the boolean
	}

	/* needed by interface, do nothing */
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}
