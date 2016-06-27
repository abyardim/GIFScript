
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* An interface for any visual elements on the scene */

import java.awt.Graphics2D;

public interface Renderable {
	public void render ( Graphics2D g);
	public boolean isVisible ( );
	public void setVisible ( boolean v);
	
	public void setZ ( int z);
	public int getZ ();
}
