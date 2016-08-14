
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* A sthird order Bezier curve, for use by scripts */
package gifscript;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Point2D;

public class SBezier3 extends SGeometricPrimitive {

	public SBezier3 ( )
	{
		super ( new Point2D.Double[]{ new Point2D.Double(), new Point2D.Double(), new Point2D.Double()});
	}
	
	public SBezier3 ( Point2D.Double cp1, Point2D.Double cp2, Point2D.Double cp3, Point2D.Double cp4)
	{
		super ( new Point2D.Double[]{ cp1, cp2, cp3, cp4});
	}
	
	// TODO add convenience methods

	@Override
	public void render(Graphics2D g) {
		if ( getDrawStroke())
		{
			CubicCurve2D.Double curve = new CubicCurve2D.Double( points[0].x, points[0].y, 
																	points[1].x, points[1].y,
																	points[2].x, points[2].y,
																	points[3].x, points[3].y);
		
			g.setStroke( getStroke());
			g.setColor( getStrokeColor());
			
			g.draw( curve);
		}
	}
	
	@Override
	public Shape getShape ( )
	{
		return new CubicCurve2D.Double( points[0].x, points[0].y, 
										points[1].x, points[1].y,
										points[2].x, points[2].y,
										points[3].x, points[3].y);
	}

}
