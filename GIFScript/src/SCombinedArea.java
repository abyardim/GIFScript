
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Renders a geometric combination of vectorized objects using basic     */
/* path operations */

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SCombinedArea extends SGeometricPrimitive {
	ArrayList<Subarea> listAreas;
	
	public static final int AREA_ADD = 0;
	public static final int AREA_SUBTRACT = 1;
	public static final int AREA_INTERSECT = 2;
	public static final int AREA_XOR = 3;
	
	public SCombinedArea ( ) {
		super( new Point2D.Double[]{ new Point2D.Double()});
		
		listAreas = new ArrayList<Subarea>();
	}

	public void add ( SGeometricPrimitive o)
	{
		listAreas.add( new Subarea( AREA_ADD, o));
		
		o.setVisible( false);
	}
	
	public void add ( SGeometricPrimitive ... objects)
	{
		for ( SGeometricPrimitive o : objects)
		{
			listAreas.add( new Subarea( AREA_ADD, o));
			o.setVisible( false);
		}
	}
	
	public void combine ( int type, SGeometricPrimitive o)
	{
		listAreas.add( new Subarea( type, o));
		
		o.setVisible( false);
	}
	
	public void combine ( int type, SGeometricPrimitive ... objects)
	{
		for ( SGeometricPrimitive o : objects)
		{
			listAreas.add( new Subarea( type, o));
			o.setVisible( false);
		}
	}
	
	public void subtract ( SGeometricPrimitive ... objects )
	{
		for ( SGeometricPrimitive o : objects)
			combine( AREA_SUBTRACT, o);
	}
	
	public void intersect ( SGeometricPrimitive ... objects )
	{
		for ( SGeometricPrimitive o : objects)
			combine( AREA_INTERSECT, o);
	}
	
	public void xor ( SGeometricPrimitive ... objects )
	{
		for ( SGeometricPrimitive o : objects)
			combine( AREA_XOR, o);
	}
	
	public void remove ( SGeometricPrimitive obj)
	{
		listAreas = listAreas.stream().filter( o -> o.area != obj).collect( Collectors.toCollection(ArrayList::new));
	}
	
	public void remove ( SGeometricPrimitive ... objects)
	{
		for ( SGeometricPrimitive o : objects)
			remove( o);
	}
	
	@Override
	public void render(Graphics2D g) {
		Shape combinedShape = getShape( );
		
		if ( getDrawFill())
		{
			g.setColor( getFillColor());
			g.fill( combinedShape);
		}
		
		if ( getDrawStroke())
		{
			g.setStroke( getStroke());
			g.setColor( getStrokeColor());
			g.draw( combinedShape);
		}
	}
	
	///////// geometric transformations
	@Override
	public void translate(double dx, double dy) {
		for ( Subarea o : listAreas)
			o.area.translate( dx, dy);
			
		setTransformCenter( getTransformCenter().x + dx, getTransformCenter().y + dy);
	}

	@Override
	public void rotate ( double angle) {
		rotate( angle, getTransformCenter().x, getTransformCenter().y);
	}

	@Override
	public void rotate ( double angle, double centerx, double centery) {
		
		Point2D.Double c = this.getTransformCenter();
		this.setLocation( centerx + ( c.x - centerx) * Math.cos( -angle) - ( c.y - centery) * Math.sin( -angle),
							centery + ( c.x - centerx) * Math.sin( -angle) + ( c.y - centery) * Math.cos( -angle));
		
		for ( Subarea o : listAreas)
		{
			Point2D.Double p = o.area.getTransformCenter();
			Point2D.Double p2 = new Point2D.Double();
			
			p2.x = centerx + ( p.x - centerx) * Math.cos( -angle) - ( p.y - centery) * Math.sin( -angle);
			p2.y = centery + ( p.x - centery) * Math.sin( -angle) + ( p.y - centery) * Math.cos( -angle);
			
			o.area.setLocation( p2.x, p2.y);
			o.area.rotate( angle);
		}
		
		
	}

	@Override
	public void rotateOrigin ( double angle) {
		rotate( angle, 0, 0);
	}

	@Override
	public void setLocation(double x, double y) {
		double dx = x - getTransformCenter().x;
		double dy = y - getTransformCenter().y;
		
		translate( dx, dy);
	}

	@Override
	public void scale ( double factor) {
		
		Point2D.Double c = this.getTransformCenter();
		
		for ( Subarea o : listAreas)
		{
			o.area.scale( factor);
			
			Point2D.Double c2 = o.area.getTransformCenter();
			o.area.translate( ( c2.x - c.x) * ( factor - 1),
								( c2.y - c.y) * ( factor - 1));
		}
	}
	
	
	///////// internal helpers
	
	private class Subarea 
	{
		SGeometricPrimitive area;
		// method of combination:
		int combinationType;
		
		public Subarea ( int type, SGeometricPrimitive area)
		{
			this.combinationType = type;
			this.area = area;
		}
	}


	@Override
	public Shape getShape() {
		Area areaCombined = new Area();
		
		for ( Subarea s : listAreas)
		{
			switch ( s.combinationType)
			{
			case AREA_ADD:
				areaCombined.add( new Area( s.area.getShape()));
				break;
			case AREA_SUBTRACT:
				areaCombined.subtract( new Area( s.area.getShape()));
				break;
			case AREA_XOR:
				areaCombined.exclusiveOr( new Area( s.area.getShape()));
				break;
			case AREA_INTERSECT:
				areaCombined.intersect( new Area( s.area.getShape()));
				break;
			}
		}
		
		return areaCombined;
	}
}
