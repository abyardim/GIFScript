
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Text drawn on the scene in a transformed rectangle, used by the script */
package gifscript;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SText extends SGeometricPrimitive {
	
	private String text;
	private Font font;

	private AffineTransform transform;
	
	public SText ( ) {
		super( new Point2D.Double[]{ new Point2D.Double()});
		
		setFillColor( Color.BLACK);
		setDrawStroke( false);
		setDrawFill( true);
		
		text = " ";
		font = new Font( "Helvetica", Font.PLAIN, 26);
		
		transform = new AffineTransform();
		transform.setToIdentity();
	}
	
	public SText ( String text) {
		super( new Point2D.Double[]{ new Point2D.Double()});
		
		setFillColor( Color.BLACK);
		setDrawStroke( false);
		setDrawFill( true);
		
		this.text = text;
		font = new Font( "Helvetica", Font.PLAIN, 26);
		
		transform = new AffineTransform();
		transform.setToIdentity();
	}

	@Override
	public void render ( Graphics2D g) {
		Shape textShape = vectorizeText( g);
		
		Rectangle2D aabb = textShape.getBounds2D(); 
		
		AffineTransform combinedTransform = new AffineTransform();
		combinedTransform.setToIdentity();
		combinedTransform.concatenate( transform);
		combinedTransform.translate( - aabb.getWidth() / 2, 0);
		
		Shape transformedShape = combinedTransform.createTransformedShape( textShape);
		
		if ( getDrawFill())
		{
			g.setColor( getFillColor());
			g.fill( transformedShape);
		}
		
		if ( getDrawStroke())
		{
			g.setColor( getStrokeColor());
			g.setStroke( getStroke());
			g.fill( transformedShape);
		}
	}
	
	////// text manipulation
	
	public void setText ( String s)
	{
		this.text = s;
	}
	
	public String getText ( )
	{
		return text;
	}
	
	public Font getFont ( )
	{
		 return font;
	}
	
	public void setFont ( Font f)
	{
		this.font = f;
	}
	
	////// geometric transformations:

	@Override
	public void translate ( double dx, double dy) {
		transform.preConcatenate( AffineTransform.getTranslateInstance( dx, dy));
		setTransformCenter( getTransformCenter().x + dx, getTransformCenter().y + dy);
	}

	@Override
	public void rotate ( double angle) {
		transform.preConcatenate( AffineTransform.getRotateInstance( -angle, getTransformCenter().x, getTransformCenter().y));
	}

	@Override
	public void rotate ( double angle, double centerx, double centery) {
		transform.preConcatenate( AffineTransform.getRotateInstance( -angle, centerx, centery));
		
		Point2D.Double p = getTransformCenter();
		Point2D.Double p2 = new Point2D.Double();
		
		p2.x = centerx + ( p.x - centerx) * Math.cos( -angle) - ( p.y - centery) * Math.sin( -angle);
		p2.y = centery + ( p.x - centerx) * Math.sin( -angle) + ( p.y - centery) * Math.cos( -angle);
		
		setTransformCenter( p2.x, p2.y);
	}

	@Override
	public void rotateOrigin( double angle) {
		rotate( angle, 0, 0);
	}

	@Override
	public void setLocation(double x, double y) {
		translate( x - getTransformCenter().x, y - getTransformCenter().y);
	}

	@Override
	public void scale ( double factor) {
		AffineTransform scaling = AffineTransform.getScaleInstance( factor, factor);
		
		transform.preConcatenate( AffineTransform.getTranslateInstance( - getTransformCenter().x, - getTransformCenter().y));
		transform.preConcatenate( scaling);
		transform.preConcatenate( AffineTransform.getTranslateInstance( getTransformCenter().x, getTransformCenter().y));
	}

	// internal helpers
	
	private Shape vectorizeText ( Graphics2D g)
	{
	    GlyphVector v = font.createGlyphVector( g.getFontRenderContext(), text);
	    return v.getOutline();
	}
	
	private Shape vectorizeText ()
	{
		BufferedImage testFrame = new BufferedImage( 1000, 1000, BufferedImage.TYPE_INT_ARGB); // TODO: a cleaner way?
		Graphics2D g = testFrame.createGraphics();
		GlyphVector v = font.createGlyphVector( g.getFontRenderContext(), text);
		
		return v.getOutline();
	}

	@Override
	public Shape getShape ( ) {
		Shape sText = vectorizeText();
		Rectangle2D aabb = sText.getBounds2D(); 
		
		AffineTransform combinedTransform = new AffineTransform();
		combinedTransform.setToIdentity();
		combinedTransform.concatenate( transform);
		combinedTransform.translate( - aabb.getWidth() / 2, 0);
	
		return combinedTransform.createTransformedShape(sText);
	}
}
