import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;


public class SArc extends SGeometricPrimitive {
	public SArc ( )
	{
		super( new Point2D.Double[]{ new Point2D.Double(), new Point2D.Double()});
	}
	
	public SArc ( Point2D.Double p1, double radius)
	{
		super( new Point2D.Double[]{ p1, null});
		
		setRadius( radius);
	}
	
	public SArc ( Point2D.Double p1, double radius, Color c)
	{
		super( new Point2D.Double[]{ p1, null},
				false,
				true,
				Color.WHITE,
				c,
				new BasicStroke());
		setRadius( radius);

	}
	
	// getters / setters for circle properties
	
	public double getRadius ( )
	{
		return Math.sqrt( Math.pow( points[0].x - points[1].x, 2) +  Math.pow( points[0].y - points[1].y, 2));
	}
	
	public void setRadius ( double radius)
	{
		points[1] = new Point2D.Double( points[0].x + radius, points[0].y); 
	}
	
	public Point2D.Double getCenter ()
	{
		return new Point2D.Double( points[0].x, points[0].y);
	}

	@Override
	public void render ( Graphics2D g) {
		double radius = getRadius();
		
		if ( getDrawStroke())
		{
			g.setColor( getStrokeColor());
			g.setStroke( getStroke());
			g.drawOval( ( int) ( points[0].x - radius), ( int) ( points[0].y - radius), ( int) radius, ( int) radius);
		}
		
		if ( getDrawFill())
		{
			g.setColor( getFillColor());
			g.fillOval( ( int) ( points[0].x - radius), ( int) ( points[0].y - radius), ( int) radius, ( int) radius);
		}
	}
	
}
