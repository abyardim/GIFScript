import java.awt.geom.Point2D;


public interface Transformable {
	public void translate ( double dx, double dy);
	public void rotate ( double angle);
	public void rotate ( double angle, double centerx, double centery);
	public void rotateOrigin ( double angle);
	public void setTransformCenter ( double x, double y);
	public Point2D.Double getTransformCenter ( );
	public void setLocation ( double x, double y);
	
}
