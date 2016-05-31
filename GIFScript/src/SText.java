import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class SText extends SGeometricPrimitive {
	
	private String text;
	private Font font;
	private Shape vectorizedShape;

	private AffineTransform transform;
	
	public SText ( ) {
		super( new Point2D.Double[]{ new Point2D.Double()});
		
		setFillColor( Color.BLACK);
		setDrawStroke( false);
		setDrawFill( true);
		
		text = " ";
		font = new Font( "Helvetica", Font.PLAIN, 26);
		
		transform = new AffineTransform();
	}
	
	public SText ( String text) {
		super( new Point2D.Double[]{ new Point2D.Double()});
		
		setFillColor( Color.BLACK);
		setDrawStroke( false);
		setDrawFill( true);
		
		this.text = text;
		font = new Font( "Helvetica", Font.PLAIN, 26);
		
		transform = new AffineTransform();
	}

	@Override
	public void render ( Graphics2D g) {
		Shape transformedShape = transform.createTransformedShape( vectorizeText ( g));
		
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
		transform.translate( dx, dy);
		setTransformCenter( getTransformCenter().x + dx, getTransformCenter().y + dy);
	}

	@Override
	public void rotate ( double angle) {
		transform.rotate( -angle, getTransformCenter().x, getTransformCenter().y );
	}

	@Override
	public void rotate(double angle, double centerx, double centery) {
		transform.rotate( -angle, centerx, centery );
		
		Point2D.Double p = getTransformCenter();
		Point2D.Double p2 = new Point2D.Double();
		
		p2.x = centerx + ( p.x - centerx) * Math.cos( -angle) - ( p.y - centery) * Math.sin( -angle);
		p2.y = centery + ( p.x - centerx) * Math.sin( -angle) + ( p.y - centery) * Math.cos( -angle);
		
		setTransformCenter( p2.x, p2.y);
	}

	@Override
	public void rotateOrigin(double angle) {
		rotate( angle, 0, 0);
	}

	@Override
	public void setLocation(double x, double y) {
		Point2D.Double p = getTransformCenter();
		translate( x - p.x, y - p.y);
		setTransformCenter( x, y);
	}

	@Override
	public void scale(double factor) {
		Point2D.Double p = getTransformCenter();
		transform.translate( p.x, p.y);
		transform.scale( factor, factor);
		transform.translate( -p.x, -p.y);
	}

	// internal helpers
	
	private Shape vectorizeText ( Graphics2D g)
	{
	    GlyphVector v = font.createGlyphVector( g.getFontRenderContext(), text);
	    return v.getOutline();
	}

	@Override
	public Shape getShape ( ) {
		return null;	// TODO: implement text vectorization
	}
}
