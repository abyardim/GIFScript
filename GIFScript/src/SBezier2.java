
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* A second order Bezier curve object, used by scripts  */

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;

public class SBezier2 extends SGeometricPrimitive {
	
	
	public SBezier2 ( )
	{
		super ( new Point2D.Double[]{ new Point2D.Double(), new Point2D.Double(), new Point2D.Double()});
	}
	
	public SBezier2 ( Point2D.Double cp1, Point2D.Double cp2, Point2D.Double cp3)
	{
		super ( new Point2D.Double[]{ cp1, cp2, cp3});
	}
	
	// TODO add convenience methods

	@Override
	public void render(Graphics2D g) {
		if ( getDrawStroke())
		{
			QuadCurve2D.Double curve = new QuadCurve2D.Double( points[0].x, points[0].y, 
																points[1].x, points[1].y,
																points[2].x, points[2].y);
		
			g.setStroke( getStroke());
			g.setColor( getStrokeColor());
			
			g.draw( curve);
		}
	}
	
	@Override
	public Shape getShape ( )
	{
		QuadCurve2D.Double curve = new QuadCurve2D.Double( points[0].x, points[0].y, 
				points[1].x, points[1].y,
				points[2].x, points[2].y);
		
		return curve;
	}
}
