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
