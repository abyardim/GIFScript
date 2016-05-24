import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;


public class SLine extends SceneObject {

	public Point2D.Double start, end;
	public Color color;
	
	public SLine ( )
	{
		start = new Point2D.Double( );
		end = new Point2D.Double( );
		color = Color.BLACK;
	}
	
	public SLine ( Point2D.Double p1, Point2D.Double p2)
	{
		start = p1;
		end = p2;
		color = Color.BLACK;
	}
	
	public SLine ( Point2D.Double p1, Point2D.Double p2, Color c)
	{
		start = p1;
		end = p2;
		color = c;
	}
	
	@Override
	public void render(Graphics2D g) {
		g.setColor( color);
		g.drawLine( (int) start.x, (int) start.y, (int) end.x, (int) end.y);
	}

	@Override
	public void translate(double dx, double dy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(double angle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rotate(double angle, double centerx, double centery) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCenter(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocation(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scale(double factor) {
		// TODO Auto-generated method stub
		
	}
	
}
