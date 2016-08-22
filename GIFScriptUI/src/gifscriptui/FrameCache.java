
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the GNU License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* The cache for a single GIf frame  */

package gifscriptui;

import javafx.scene.image.Image;

public class FrameCache {
	private long frameTimeMS;
	private Image frameImage;
	
	public FrameCache ( long time, Image img)
	{
		this.frameTimeMS = time;
		this.frameImage = img;
	}
	
	public Image getFrameImage ( )
	{
		return frameImage;
	}
	
	public long getFrametime ( )
	{
		return frameTimeMS;
	}
}
