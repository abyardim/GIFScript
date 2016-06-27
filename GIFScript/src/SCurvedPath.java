
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* A list of handles for a successive combination of bezier curves */

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class SCurvedPath extends SGeometricPrimitive {

	private ArrayList<Segment> segmentList;
	private boolean isClosed;
	
	public SCurvedPath ( ) {
		super( new Point2D.Double[0]);
		segmentList = new ArrayList<Segment>();
		setStart( 0, 0);
		
		isClosed = false;
	}
	
	public SCurvedPath ( Point2D.Double start) {
		super( new Point2D.Double[0]);
		segmentList = new ArrayList<Segment>();
		setStart( start);
		
		isClosed = false;
	}
	
	// starting point of the curve
	public void setStart ( Point2D.Double p)
	{
		updateSegments();
		segmentList.set( 0, new Segment( 0, new Point2D.Double( p.x, p.y)));
		copyToBuffer();
	}
	
	public void setStart ( double x, double y)
	{
		setStart( new Point2D.Double( x, y));
	}
	
	// draw a straight line
	public void addLine( Point2D.Double p)
	{
		updateSegments();
		segmentList.add( new Segment( 1, new Point2D.Double( p.x, p.y)));
		copyToBuffer();
	}
	
	public void addLine ( double x, double y)
	{
		addLine ( new Point2D.Double( x, y));
	}
	
	// add a quadratic bezier curve
	public void addCurve ( double x1, double y1, double x2, double y2)
	{
		updateSegments();
		segmentList.add( new Segment( 2, new Point2D.Double( x1, y1), new Point2D.Double( x2, y2)));
		copyToBuffer();
	}
	
	public void addCurve ( Point2D.Double p1, Point2D.Double p2)
	{
		addCurve( p1.x, p1.y, p2.x, p2.y);
	}
	
	// add a cubic bezier curve
	public void addCurve ( double x1, double y1, double x2, double y2, double x3, double y3)
	{
		updateSegments();
		segmentList.add( new Segment(3, new Point2D.Double( x1, y1), new Point2D.Double( x2, y2), new Point2D.Double( x3, y3)));
		copyToBuffer();
	}
	
	public void addCurve ( Point2D.Double p1, Point2D.Double p2, Point2D.Double p3)
	{
		addCurve( p1.x, p1.y, p2.x, p2.y, p3.x, p3.y);
	}
	
	public boolean getClosed ()
	{
		return isClosed;
	}
	
	public void setClosed ( boolean c)
	{
		this.isClosed = c;
	}
	
	// TODO: expose CurvedPath points properly
	//
	// 
	
	@Override
	public void render ( Graphics2D g) {
		updateSegments();
		
		Path2D.Double path = calculatePath();
		
		if ( isClosed)
			path.closePath();
		
		if ( getDrawStroke())
		{
			g.setStroke( getStroke());
			g.setColor( getStrokeColor( ));
			g.draw( path);
		}
		
		if ( getDrawFill())
		{
			g.setColor( getFillColor( ));
			g.fill( path);
			g.draw( path);
		}
	}

	// update segment points from transformed point data
	// from SGeometricPrimitive
	private void updateSegments ( )
	{
		int lastIndex = 0;
		int i = 0;
		
		while ( lastIndex < points.length)
		{
			switch ( segmentList.get( i).type)
			{
			case 0:
				segmentList.get( i).p1 = points[lastIndex];
				lastIndex++;
				break;
			case 1:
				segmentList.get( i).p1 = points[lastIndex];
				lastIndex++;
				break;
			case 2:
				segmentList.get( i).p1 = points[lastIndex];
				segmentList.get( i).p2 = points[lastIndex + 1];
				lastIndex += 2;
				break;
			case 3:
				segmentList.get( i).p1 = points[lastIndex];
				segmentList.get( i).p2 = points[lastIndex + 1];
				segmentList.get( i).p3 = points[lastIndex + 2];
				lastIndex += 3;
				break;
			}
			
			i++;
		}
	}
	
	// copy the point data to the points array
	private void copyToBuffer ( )
	{
		ArrayList<Point2D.Double> pointData = new ArrayList<Point2D.Double>();
		
		for ( Segment s : segmentList )
		{
			switch( s.type)
			{
			case 3:
				pointData.add( s.p3);
			case 2:
				pointData.add( s.p2);
			case 1:
			case 0:
				pointData.add( s.p1);
				break;
			}
		}
		
		points = pointData.toArray( new Point2D.Double[ pointData.size()]);
	}
	
	private Path2D.Double calculatePath ( )
	{
		Path2D.Double path = new Path2D.Double();
		
		for ( Segment s : segmentList )
		{
			switch( s.type)
			{
			case 3:
				path.curveTo( s.p1.x, s.p1.y, s.p2.x, s.p2.y, s.p3.x, s.p3.y );
				break;
			case 2:
				path.quadTo( s.p1.x, s.p1.y, s.p2.x, s.p2.y);
				break;
			case 1:
				path.lineTo( s.p1.x, s.p1.y);
				break;
			case 0:
				path.moveTo( s.p1.x, s.p1.y);
				break;
			}
		}
		
		return path;
	}
	
	// internal class, code segments of the general curve
	private static class Segment
	{
		// type of the path segment:
		// 0 = move point to
		// 1 = straight line
		// 2 = Bezier order 2
		// 3 = Bezier order 3
		int type;
		
		// point data, not all necessarily used
		Point2D.Double p1, p2, p3;
		
		public Segment ( int type, Point2D.Double p1)
		{
			this.type = type;
			this.p1 = p1;
		}
		
		public Segment ( int type, Point2D.Double p1, Point2D.Double p2)
		{
			this.type = type;
			this.p1 = p1;
			this.p2 = p2;
		}
		
		public Segment ( int type, Point2D.Double p1, Point2D.Double p2, Point2D.Double p3)
		{
			this.type = type;
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
		}
	
	}

	@Override
	public Shape getShape() {
		return calculatePath();
	}
}
