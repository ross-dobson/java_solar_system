package module9;

import java.awt.Color;

/**
 *	A class to represent an orbiting object in the solar system, on a circular orbit.
 *	Stores information pertaining to the object. No calculations are carried out inside.
 *	Exceptions thrown for invalid period, radius, or diameter (need to be greater than 0).
 * 	@author Ross Dobson	
 * 	@version 1.1 (2019-12-19)
 */
public class CelestialCircular {

	private final String name;
	private final double p; // orbital period, earth days
	private final double r; // orbital radius, AU
	private final double d; // object physical diameter, km
	private Color c; // an awt colour that the celestial+labels will be drawn with

	// positions in pixels - needs to be retrieved for moons/satellites to orbit
	private double x;
	private double y;


	/**
	 * Constructor class requires necessary arguments to establish a circular orbit
	 * @param name The name of the planet
	 * @param p Orbital period of an object [earth days]
	 * @param r Orbital radius of an object [AU]
	 * @param d Diameter of object in km
	 * @param c A Java AWT Color object, the colour to draw the celestial + labels
	 * @throws Exception if p, r, or d are less than or equal to 0
	 */
	public CelestialCircular(String name, double p, double r, double d, Color c) throws Exception {
		super();
		this.name=name;
		if (p<=0) {
			throw new Exception("The period of "+name+" must be a valid (i.e. larger than zero) number of days");
		} else {
			this.p = p;
		}
		if (r<=0) {
			throw new Exception("The orbital radius of "+name+" must be a valid (i.e. larger than zero) number of AU");
		} else {
			this.r = r;
		}
		if (d<=0) {
			throw new Exception("The diameter of"+name+" must be a valid (i.e. larger than zero) number of km");
		} else {
			this.d = d;
		}

		this.c=c; // Color class has in-built exception handling, no need for any here
	}

	// getters

	/** Returns the name 
	 * @return name: name of celestial 
	 */
	public String getName() {
		return name;
	}
	/** Returns the orbital period in days
	 * @return p: orbital period in days*/
	public double getP() {
		return p;
	}
	/** Returns the orbital radius in AU
	 * @return r: orbital radius in AU*/
	public double getR() {
		return r;
	}
	/** Returns the diameter in km
	 * @return d: diameter in km*/
	public double getD() {
		return d;
	}
	/** Returns the java.awt colour 
	 * @return c: the java.awt colour
	 * @see java.awt.Color*/
	public Color getColor() {
		return c;
	}
	/** Returns the x co-ordinate in pixels 
	 * @return x: x co-ordinate in pixels*/
	public double getX() {
		return x;
	}
	/** Returns the y co-ordinate in pixels
	 * @return y: y co-ordinate in pixels*/
	public double getY() {
		return y;
	}

	// setters 

	/** Set a new x co-ordinate in pixels 
	 * @param x: new x-coordinate in pixels */
	public void setX(double x) {
		this.x=x;
	}
	/** Set a new y co-ordinate in pixels
	 * @param y: new y-coordinate in pixels */
	public void setY(double y) {
		this.y=y;
	}

	@Override
	public String toString() {
		return(name+"|Period: "+p+" days|Orbital Radius: "+r+" AU|Object Diameter: "+d+" km.");
	}



}
