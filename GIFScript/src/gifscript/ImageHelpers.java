
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Static methods for image helper functions */

package gifscript;

import java.awt.image.*;

public class ImageHelpers {
	
	// a deep copy of the BufferedImage instance
	public static BufferedImage copyImage ( BufferedImage source )
	{
		ColorModel cm = source.getColorModel();
		boolean isAlphaPremultiplied = source.isAlphaPremultiplied();
		WritableRaster raster = source.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null).getSubimage( 0, 0, source.getWidth(), source.getHeight());
	}	
}
