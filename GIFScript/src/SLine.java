
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* A line object drawn to the scene */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class SLine extends SGeometricPrimitive {
	
	public SLine ( )
	{
		super( new Point2D.Double[]{ new Point2D.Double(), new Point2D.Double()});
	}
	
	public SLine ( Point2D.Double p1, Point2D.Double p2)
	{
		super( new Point2D.Double[]{ p1, p2});
	}
	
	public SLine ( Point2D.Double p1, Point2D.Double p2, Color c)
	{
		super( new Point2D.Double[]{ p1, p2},
				false,
				true,
				Color.WHITE,
				c,
				new BasicStroke());
		
		System.out.println( "Color: " + c);
	}
	
	//// getters / setters for line properties
	
	public Point2D.Double getStart ( )
	{
		return points[0];
	}
	
	public Point2D.Double getEnd ( )
	{
		return points[1];
	}
	
	public void setEnd ( Point2D.Double end)
	{
		points[1] = new Point2D.Double( end.x, end.y);
	}
	
	public void setStart ( Point2D.Double start)
	{
		points[0] = new Point2D.Double( start.x, start.y);
	}
	
	@Override
	public void render(Graphics2D g) {
		if ( getDrawStroke())
		{
			g.setColor( getStrokeColor());
			g.setStroke( getStroke());
			System.out.println( "Points: " + points[0] + points[1]);
			g.drawLine( (int) points[0].x, (int) points[0].y, (int) points[1].x, (int) points[1].y);
		}
	}

	@Override
	public Shape getShape() {
		return new Line2D.Double( points[0].x, points[0].y, points[1].x, points[1].y);
	}




	
}
