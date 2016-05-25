import java.awt.Color;
import java.awt.Stroke;
import java.awt.geom.Point2D;

import java.awt.BasicStroke;


public abstract class SGeometricPrimitive extends SceneObject {

	public Point2D.Double[] points;
	
	private Stroke stroke;
	private boolean drawFill;
	private boolean drawStroke;
	private Color fillColor;
	private Color strokeColor;
	
	public SGeometricPrimitive ( Point2D.Double[] points)
	{
		this.points = new Point2D.Double[ points.length];
		
		System.arraycopy( points, 0, this.points, 0, points.length );
		
		drawFill = false;
		drawStroke = true;
		fillColor = Color.WHITE;
		strokeColor = Color.BLACK;
		stroke = new BasicStroke();
	}
	
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
	
	public Stroke getStroke () {
		return stroke;
	}

	public void setStroke ( Stroke stroke) {
		this.stroke = stroke;
	}

	public boolean getDrawFill () {
		return drawFill;
	}

	public void setDrawFill ( boolean drawFill) {
		this.drawFill = drawFill;
	}
	
	public boolean getDrawStroke () {
		return drawStroke;
	}

	public void setDrawStroke ( boolean drawStroke) {
		this.drawStroke = drawStroke;
	}

	public Color getFillColor () {
		return fillColor;
	}

	public void setFillColor ( Color fillColor) {
		this.fillColor = fillColor;
	}

	public Color getStrokeColor () {
		return strokeColor;
	}

	public void setStrokeColor ( Color strokeColor) {
		this.strokeColor = strokeColor;
	}
	
	///// geometry transformations

	// translate all points
	@Override
	public void translate(double dx, double dy) {
		for ( Point2D.Double p : points)
		{
			p.x += dx;
			p.y += dy;
			
		}
		
		this.setTransformCenter( this.getTransformCenter().x + dx, this.getTransformCenter().y + dy);
	}

	// rotate points around the center of the shape
	@Override
	public void rotate(double angle) {
		double centerx = this.getTransformCenter().x;
		double centery = this.getTransformCenter().y;
		
		for ( Point2D.Double p : points)
		{
			p.x = centerx + ( p.x - centerx) * Math.cos( angle) - ( p.y - centery) * Math.sin( angle);
			p.y = centery + ( p.x - centery) * Math.sin( angle) + ( p.y - centery) * Math.cos( angle);
		}

	}

	// rotate the shape around a specified point
	@Override
	public void rotate(double angle, double centerx, double centery) {
		
		for ( Point2D.Double p : points)
		{
			p.x = centerx + ( p.x - centerx) * Math.cos( angle) - ( p.y - centery) * Math.sin( angle);
			p.y = centery + ( p.x - centery) * Math.sin( angle) + ( p.y - centery) * Math.cos( angle);
		}
		
		Point2D.Double c = this.getTransformCenter();
		this.setTransformCenter( centerx + ( c.x - centerx) * Math.cos( angle) - ( c.y - centery) * Math.sin( angle),
									centery + ( c.x - centery) * Math.sin( angle) + ( c.y - centery) * Math.cos( angle));
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
