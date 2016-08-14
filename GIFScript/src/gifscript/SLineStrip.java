
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* A list of successive points to draw a strip of lines, used by the script  */
package gifscript;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SLineStrip extends SGeometricPrimitive {
	
	private ArrayList<Point2D.Double> pointList;

	public SLineStrip ( )
	{
		super( new Point2D.Double[0]);
		pointList = new ArrayList<Point2D.Double>();
	}
	
	public SLineStrip ( Point2D.Double[] pointData)
	{
		super( pointData);
		
		pointList = new ArrayList<Point2D.Double>();
		
		for ( Point2D.Double p : pointData)
		{
			pointList.add( p);
		}
	}
	
	public void add ( Point2D.Double p)
	{
		updatePoints ( );
		
		pointList.add( new Point2D.Double( p.x, p.y));
		
		// update points
		points = pointList.toArray( new Point2D.Double[pointList.size()]);
	}
	
	public void add ( double x, double y)
	{
		updatePoints ( );
		
		pointList.add( new Point2D.Double( x, y));
		
		// update points
		points = pointList.toArray( new Point2D.Double[pointList.size()]);
	}
	
	public Point2D.Double pop ( )
	{
		updatePoints ( );
		
		if ( pointList.size() == 0)
			return null;
		else
		{
			Point2D.Double p = pointList.get( pointList.size() - 1);
			pointList.remove( pointList.size() - 1);
			
			// update points
			points = pointList.toArray( new Point2D.Double[pointList.size()]);
			
			return p;
		}
	}
	
	public ArrayList<Point2D.Double> getPoints ( )
	{
		updatePoints ( );
		// TODO return a deep copy
		return pointList;
	}
	
	public Point2D.Double get ( int i)
	{
		updatePoints ( );

		return new Point2D.Double( pointList.get( i).x,
									pointList.get( i).y);
	}
	
	
	@Override
	public void render(Graphics2D g) {
		
		// TODO: consider drawing a point
		if ( pointList.size() <= 1)
			return;
		
		// draw stroke
		if ( getDrawStroke())
		{
			Point2D.Double lastPoint = pointList.get( 0);
	
			g.setStroke( getStroke());
			g.setColor( getStrokeColor());
			
			
			for ( int i = 1; i < pointList.size(); i++)
			{
				g.drawLine( ( int) lastPoint.x, ( int) lastPoint.y, 
							( int) pointList.get( i).x, ( int) pointList.get( i).y);
				
				lastPoint = pointList.get( i);
			}
		}
	}
	
	// helper: update point data
	private void updatePoints ( )
	{
		for ( int i = 0; i < pointList.size(); i++)
		{
			pointList.get( i).setLocation( points[i].x, points[i].y);
		}
	}

	@Override
	public Shape getShape() {
		Path2D.Double path = new Path2D.Double();
		Point2D.Double lastPoint = pointList.get( 0);
		path.moveTo( lastPoint.x, lastPoint.y);
		
		for ( int i = 1; i < pointList.size(); i++)
		{
			path.lineTo( pointList.get( i).x, pointList.get( i).y);
		}
		
		return path;
	}
}
