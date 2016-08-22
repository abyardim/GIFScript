
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the GNU License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* JavaFX task for the thread responsible for updating the Image viewer  */
/* when previewing GIFs */

package gifscriptui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SceneUpdaterTask extends Task<Void> {

	private ImageView view;
	private FrameBag cache;
	
	public SceneUpdaterTask ( ImageView view, FrameBag cache)
	{
		this.view = view;
		this.cache = cache;
	}
	
	@Override
	protected Void call() throws Exception {
		
		System.out.println( "Updater start.");
	
		int frameCount = 0;
		FrameCache currentFrame = null;
		try {
			while ( true)
			{
				synchronized ( cache)
				{
					if ( cache.getFrameCount() <= frameCount)
					{
						if ( cache.isCachingComplete())
							frameCount = 0;
						else
							cache.wait();
					}
					
					if ( cache.getFrameCount() > 0)
						currentFrame = cache.getFrame( frameCount);
			
					if ( currentFrame != null)
					{
						// System.out.println( "Output frame: " + frameCount);
						Image img = currentFrame.getFrameImage();
						Platform.runLater( () -> view.setImage( img));
					}
					
					frameCount++;
				}				
				
				Thread.sleep( Math.max( currentFrame.getFrametime() - 1, 15));
				
				if ( isCancelled())
					throw new InterruptedException();
			}
		} catch ( InterruptedException e) {

		}
		
		System.out.println( "Updater exit.");
		
		return null;
	}

}
