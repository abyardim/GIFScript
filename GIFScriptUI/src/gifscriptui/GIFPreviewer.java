
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
	
	private Thread producerThread, consumerThread;
	
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
		
		producerThread = new Thread( frameProducer);
		consumerThread = new Thread( sceneUpdater);
		
		producerThread.setDaemon( true);
		consumerThread.setDaemon( true);
		
		producerThread.start();		
		consumerThread.start();
	}
	
	public void stop ( )
	{
		sceneUpdater.cancel();
		frameProducer.cancel();
		
		try {
			if ( producerThread.isAlive())
				producerThread.join();
		} catch ( InterruptedException e) {}
		
		try {
			if ( consumerThread.isAlive())
				consumerThread.join();
		} catch ( InterruptedException e) {}
				
		cache = null;
	}
}
