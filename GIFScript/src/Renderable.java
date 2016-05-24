import java.awt.Graphics2D;

public interface Renderable {
	public void render ( Graphics2D g);
	public boolean isVisible ( );
	public void setVisible ( boolean v);
}
