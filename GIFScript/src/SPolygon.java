import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;


public class SPolygon extends SGeometricPrimitive {

	public SPolygon ( ArrayList<Point2D.Double> pointList) {
		super( pointList.toArray( new Point2D.Double[ pointList.size()]));
	}
	
	public SPolygon ( Point2D.Double[] pointList) {
		super( pointList);
	}
	
	public SPolygon ( ArrayList<Point2D.Double> pointList, Color c) {
		super( pointList.toArray( new Point2D.Double[ pointList.size()]),
				false,
				true,
				Color.WHITE,
				c,
				new BasicStroke());
	}
	
	public SPolygon ( Point2D.Double[] points, Color c) {
		super( points,
				false,
				true,
				Color.WHITE,
				c,
				new BasicStroke());
	}
	
	@Override
	public void render( Graphics2D g) {
		
		int[] pointsx, pointsy;
		pointsx = Arrays.stream( points).map( Point2D.Double::getX).mapToInt( x -> x.intValue()).toArray();
		pointsy = Arrays.stream( points).map( Point2D.Double::getY).mapToInt( y -> y.intValue()).toArray();
		
		if ( getDrawFill())
		{
			g.setColor( getFillColor());
			g.fillPolygon( pointsx, pointsy, points.length);
		}
		
		if ( getDrawStroke())
		{
			g.setColor( getStrokeColor());
			g.setStroke( getStroke());
			g.drawPolygon( pointsx, pointsy, points.length);
		}
	}

	@Override
	public Shape getShape() {
		int[] pointsx, pointsy;
		pointsx = Arrays.stream( points).map( Point2D.Double::getX).mapToInt( x -> x.intValue()).toArray();
		pointsy = Arrays.stream( points).map( Point2D.Double::getY).mapToInt( y -> y.intValue()).toArray();
		return new Polygon( pointsx, pointsy, points.length);
	}

	

}
