
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Renders a loaded image resource in a transformed rectangle on the scene */
package gifscript;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class SImageFrame extends SceneObject {
	
	private Image srcImg;
	
	private AffineTransform transform;
	
	public SImageFrame ( Image img)
	{
		srcImg = img;
		
		transform = new AffineTransform();
		transform.setToIdentity();
		transform.translate( -srcImg.getWidth( null) / 2, -srcImg.getHeight( null) / 2);
	}

	/// transformations
	
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


	@Override
	public void render(Graphics2D g) {
		g.drawImage( srcImg, transform, null);
	}
}
