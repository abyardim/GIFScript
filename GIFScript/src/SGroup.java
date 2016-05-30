import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

// TODO: use generics to generalize groups
public class SGroup extends SceneObject {
	
	private ArrayList<SceneObject> elements;
	
	public SGroup ( )
	{
		elements = new ArrayList<SceneObject>();
	}
	
	// collection management methods:
	
	public void add ( SceneObject obj)
	{
		elements.add( obj);
	}
	
	public void add ( SceneObject ... objects)
	{
		for ( SceneObject o : objects)
			add( o);
	}
	
	public void remove ( SceneObject obj)
	{
		elements.remove( obj);
	}
	
	public void remove ( SceneObject ... objects)
	{
		for ( SceneObject o : objects)
			remove( o);
	}
	 

	@Override
	public void render(Graphics2D g) {
		// TODO rendering should be performed by the group manager?
		return;		
	}

	// transformation methods
	
	@Override
	public void translate(double dx, double dy) {
		for ( SceneObject o : elements)
			o.translate( dx, dy);
			
		setTransformCenter( getTransformCenter().x + dx, getTransformCenter().y + dy);
	}

	@Override
	public void rotate ( double angle) {
		Point2D.Double c = this.getTransformCenter();
		
		for ( SceneObject o : elements)
		{
			Point2D.Double p = o.getTransformCenter();
			Point2D.Double p2 = new Point2D.Double();
			
			p2.x = c.x + ( p.x - c.x) * Math.cos( -angle) - ( p.y - c.y) * Math.sin( -angle);
			p2.y = c.y + ( p.x - c.x) * Math.sin( -angle) + ( p.y - c.y) * Math.cos( -angle);
			
			o.setLocation( p2.x, p2.y);
			o.rotate( angle);
		}
	}

	@Override
	public void rotate ( double angle, double centerx, double centery) {
		
		Point2D.Double c = this.getTransformCenter();
		this.setLocation( centerx + ( c.x - centerx) * Math.cos( -angle) - ( c.y - centery) * Math.sin( -angle),
							centery + ( c.x - centerx) * Math.sin( -angle) + ( c.y - centery) * Math.cos( -angle));
		
		for ( SceneObject o : elements)
		{
			Point2D.Double p = o.getTransformCenter();
			Point2D.Double p2 = new Point2D.Double();
			
			p2.x = centerx + ( p.x - centerx) * Math.cos( -angle) - ( p.y - centery) * Math.sin( -angle);
			p2.y = centery + ( p.x - centerx) * Math.sin( -angle) + ( p.y - centery) * Math.cos( -angle);
			
			o.setLocation( p2.x, p2.y);
			o.rotate( angle);
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
		
		for ( SceneObject o : elements)
		{
			o.scale( factor);
			
			Point2D.Double c2 = o.getTransformCenter();
			o.translate( ( c2.x - c.x) * ( factor - 1),
							( c2.y - c.y) * ( factor - 1));
		}
	}
	
	// TODO z ordering managed internally?
	
	@Override
	public void setZ ( int z)
	{
		super.setZ( z);
		
		for ( SceneObject o : elements)
		{
			o.setZ( z);
		}
	}
	
	@Override
	public void setVisible ( boolean v)
	{
		super.setVisible( v);
		
		for ( SceneObject o : elements)
		{
			o.setVisible( false);
		}
	}

	// TODO: methods for applying strokes/fills?
}
