
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Abstract representation of a geometric primitive rendered to the scene */
package gifscript;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.BasicStroke;

public abstract class SGeometricPrimitive extends SceneObject implements Sketchable {

	public Point2D.Double[] points;
	
	private Stroke stroke;
	private boolean drawFill;
	private boolean drawStroke;
	private Color fillColor;
	private Color strokeColor;
	
	public SGeometricPrimitive ( Point2D.Double[] points)
	{
		this.points = new Point2D.Double[ points.length];
		
		for ( int i = 0; i < points.length; i++)
			this.points[i] = new Point2D.Double( points[i].x, points[i].y);
		
		drawFill = false;
		drawStroke = true;
		fillColor = Color.WHITE;
		strokeColor = Color.BLACK;
		stroke = new BasicStroke();
	}
	
	//// abstract methods
	
	public abstract Shape getShape ();
	
	//// constructors
	
	public SGeometricPrimitive ( Point2D.Double[] points, boolean drawFill, boolean drawStroke, 
									Color fillColor, Color strokeColor, Stroke stroke)
	{
		this.points = new Point2D.Double[ points.length];
		
		System.arraycopy( points, 0, this.points, 0, points.length );
		
		this.drawFill = drawFill;
		this.drawStroke = drawStroke;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		this.stroke = stroke;
	}
	
	
	////// getters/setters for visual properties
	
	@Override
	public Stroke getStroke () {
		return stroke;
	}

	@Override
	public void setStroke ( Stroke stroke) {
		this.stroke = stroke;
	}

	@Override
	public boolean getDrawFill () {
		return drawFill;
	}

	@Override
	public void setDrawFill ( boolean drawFill) {
		this.drawFill = drawFill;
	}
	
	@Override
	public boolean getDrawStroke () {
		return drawStroke;
	}

	@Override
	public void setDrawStroke ( boolean drawStroke) {
		this.drawStroke = drawStroke;
	}

	@Override
	public Color getFillColor () {
		return fillColor;
	}

	@Override
	public void setFillColor ( Color fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public Color getStrokeColor () {
		return strokeColor;
	}

	@Override
	public void setStrokeColor ( Color strokeColor) {
		this.strokeColor = strokeColor;
	}
	
	///// geometry transformations

	// translate all points
	@Override
	public void translate ( double dx, double dy) {
		for ( Point2D.Double p : points)
		{
			p.x += dx;
			p.y += dy;
		}
		
		this.setTransformCenter( this.getTransformCenter().x + dx, this.getTransformCenter().y + dy);
	}

	// rotate points around the center of the shape
	@Override
	public void rotate ( double angle) {

		double centerx = this.getTransformCenter().x;
		double centery = this.getTransformCenter().y;
		
		for ( Point2D.Double p : points)
		{
			Point2D.Double p2 = new Point2D.Double();
			
			p2.x = centerx + ( p.x - centerx) * Math.cos( -angle) - ( p.y - centery) * Math.sin( -angle);
			p2.y = centery + ( p.x - centerx) * Math.sin( -angle) + ( p.y - centery) * Math.cos( -angle);
			
			p.x = p2.x;
			p.y = p2.y;
		}

	}

	// rotate the shape around a specified point
	@Override
	public void rotate ( double angle, double centerx, double centery) {
		
		for ( Point2D.Double p : points)
		{
			Point2D.Double p2 = new Point2D.Double();
			
			p2.x = centerx + ( p.x - centerx) * Math.cos( -angle) - ( p.y - centery) * Math.sin( -angle);
			p2.y = centery + ( p.x - centerx) * Math.sin( -angle) + ( p.y - centery) * Math.cos( -angle);
			
			p.x = p2.x;
			p.y = p2.y;
		}
		
		Point2D.Double c = this.getTransformCenter();
		this.setLocation( centerx + ( c.x - centerx) * Math.cos( -angle) - ( c.y - centery) * Math.sin( -angle),
							centery + ( c.x - centerx) * Math.sin( -angle) + ( c.y - centery) * Math.cos( -angle));
	}
	
	// rotate shape around the origin
	@Override
	public void rotateOrigin ( double angle)
	{
		rotate( angle, 0, 0);
	}

	
	@Override
	public void setLocation ( double x, double y) {
		translate( x - getTransformCenter().x, y - getTransformCenter().y);
	}

	// scale the shape
	@Override
	public void scale ( double factor){
		Point2D.Double c = this.getTransformCenter();
		
		for ( Point2D.Double p : points)
		{
			p.x = c.x + ( p.x - c.x) * factor;
			p.x = c.y + ( p.y - c.y) * factor;
		}
	}

}
