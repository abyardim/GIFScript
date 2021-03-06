
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* A single rasterized frame                   */

package gifscript;

import java.awt.image.BufferedImage;

public class GIFFrame {
	private BufferedImage image;
	private int frameTimeMs;
	private int index;

	public GIFFrame ( int index, BufferedImage image)
	{
		this.image = image;
		this.index = index;
		this.frameTimeMs = 100;
	}
	
	public GIFFrame ( int index, BufferedImage image, int frameTimeMs)
	{
		this.image = image;
		this.index = index;
		this.frameTimeMs = frameTimeMs;
	}
	
	public BufferedImage getImage ( )
	{
		return image;
	}
	
	public int getFrameTime ( )
	{
		return frameTimeMs;
	}
	
	public int getIndex ( )
	{
		return index;
	}
	
}
