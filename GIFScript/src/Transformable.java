
public interface Transformable {
	public void translate ( double dx, double dy);
	public void rotate ( double angle);
	public void rotate ( double angle, double centerx, double centery);
	public void setCenter ( double x, double y);
	public void setLocation ( double x, double y);
}
