import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;


public class SArc extends SGeometricPrimitive {
	
	// start / stop angles of the arc segment
	double angleStart, angleStop;
	
	public SArc ( )
	{
		super( new Point2D.Double[]{ new Point2D.Double(), new Point2D.Double(),  new Point2D.Double()});
	}
	
	public SArc ( Point2D.Double p1, double semiMajor, double semiMinor)
	{
		super( new Point2D.Double[]{ p1, null, null});
		
		setRadii( semiMajor, semiMinor);
		angleStart = 0;
		angleStop = Math.PI * 2;
	}
	
	public SArc ( Point2D.Double p1, double semiMajor, double semiMinor, Color c)
	{
		super( new Point2D.Double[]{ p1, null, null},
				false,
				true,
				Color.WHITE,
				c,
				new BasicStroke());
		
		setRadii( semiMajor, semiMinor);

		angleStart = 0;
		angleStop = Math.PI * 2;
	}
	
	public SArc ( Point2D.Double p1, double semiMajor, double semiMinor, 
					Color c, double angleStart, double angleStop)
	{
		super( new Point2D.Double[]{ p1, null, null},
				false,
				true,
				Color.WHITE,
				c,
				new BasicStroke());
		
		setRadii( semiMajor, semiMinor);

		this.angleStart = angleStart;
		this.angleStop = angleStop;
	}
	
	// getters / setters for circle properties
	
	public double getRadius1 ( )
	{
		return Math.sqrt( Math.pow( points[0].x - points[1].x, 2) +  Math.pow( points[0].y - points[1].y, 2));
	}
	
	public double getRadius2 ( )
	{
		return Math.sqrt( Math.pow( points[0].x - points[2].x, 2) +  Math.pow( points[0].y - points[2].y, 2));
	}
	
	public void setRadius1 ( double semiMajor)
	{
		points[1] = new Point2D.Double( points[0].x + semiMajor, points[0].y);
	}
	
	public void setRadius2 ( double semiMinor)
	{
		points[2] = new Point2D.Double( points[0].x, points[0].y + semiMinor); 
	}
	
	public void setRadii ( double semiMajor, double semiMinor)
	{
		points[1] = new Point2D.Double( points[0].x + semiMajor, points[0].y); 
		points[2] = new Point2D.Double( points[0].x, points[0].y + semiMinor); 
	}
	
	public Point2D.Double getCenter ()
	{
		return new Point2D.Double( points[0].x, points[0].y);
	}

	public double getAngleStart() {
		return angleStart;
	}

	public void setAngleStart(double angleStart) {
		this.angleStart = angleStart;
	}

	public double getAngleStop() {
		return angleStop;
	}

	public void setAngleStop(double angleStop) {
		this.angleStop = angleStop;
	}

	@Override
	public void render ( Graphics2D g) {
		double radius1 = getRadius1();
		double radius2 = getRadius2();
		
		double rotationAngle = Math.atan2( ( points[1].y - points[0].y), ( points[1].x - points[0].x));
		
		Arc2D.Double arc = new Arc2D.Double( points[0].x - radius1, points[0].y - radius2, 
												2 * radius1, 2 * radius2, Math.toDegrees( angleStart), Math.toDegrees( angleStop), Arc2D.OPEN);
		
		AffineTransform transform = new AffineTransform();
		transform.rotate( rotationAngle, points[0].x, points[0].y);
		Shape sStroke = transform.createTransformedShape( arc);
		arc.setArcType( Arc2D.PIE);
		Shape sFill = transform.createTransformedShape( arc);
		
		if ( getDrawStroke())
		{
			g.setColor( getStrokeColor());
			g.setStroke( getStroke());
			g.draw( sStroke);
		}
		
		if ( getDrawFill())
		{
			g.setColor( getFillColor());
			g.fill( sFill);
		}
	}
	
}
