
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* An interface for an object renderable with a stroke and/or fill*/
package gifscript;

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
