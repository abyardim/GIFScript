import java.awt.Color;
import java.awt.Stroke;


public interface Sketchable {
	public Stroke getStroke ();
	public void setStroke ( Stroke stroke);

	public boolean getDrawFill ();
	public void setDrawFill ( boolean drawFill);
	
	public boolean getDrawStroke ();
	public void setDrawStroke ( boolean drawStroke);

	public Color getFillColor ();
	public void setFillColor ( Color fillColor);

	public Color getStrokeColor ();
	public void setStrokeColor ( Color strokeColor);
}
