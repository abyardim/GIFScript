
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the GNU License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Coordinator class for the GIF frame consumer/generator threads   */

package gifscriptui;

import java.io.File;

import javafx.concurrent.Task;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class GIFPreviewer {
	private String script;
	private ImageView targetView;
	private TextArea console;
	
	private File scriptDirectory;
	
	private FrameBag cache;
	
	private Task<Void> sceneUpdater;
	private Task<Integer> frameProducer;
	
	public GIFPreviewer ( String script, ImageView target, TextArea console, File scriptDirectory)
	{
		this.script = script;
		this.targetView = target;
		this.console = console;
		
		this.scriptDirectory = scriptDirectory;
	}

	public void start ( )
	{
		cache = new FrameBag();
		sceneUpdater = new SceneUpdaterTask( targetView, cache);
		frameProducer = new FrameProducerTask( console, script, cache, scriptDirectory);
		
		Thread producer = new Thread( frameProducer);
		Thread consumer = new Thread( sceneUpdater);
		
		producer.setDaemon( true);
		consumer.setDaemon( true);
		
		producer.start();		
		consumer.start();
	}
	
	public void stop ( )
	{
		sceneUpdater.cancel();
		frameProducer.cancel();
		
		cache = null;
	}
}
