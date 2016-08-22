
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the GNU License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Thread for "compiling" the GIFScript into frame caches for previewing */

package gifscriptui;

import gifscript.AnimationScene;
import gifscript.GIFFrame;
import gifscript.GIFWriter;
import gifscript.ScriptEnvironment;
import gifscript.ScriptError;

import java.io.File;
import java.io.IOException;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;

import javax.imageio.stream.ImageOutputStream;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

// a producer thread, computes the GIF frame by frame for previewing
public class FrameProducerTask extends Task<Integer> implements GIFWriter {
	
	// the script and its location on the disk
	private String script;
	private File scriptDirectory;
	
	private FrameBag cache;
	private TextArea console;
	
	public FrameProducerTask ( TextArea console, String script, FrameBag cache, File scriptDirectory)
	{
		this.script = script;
		this.cache = cache;
		this.console = console;
		
		this.scriptDirectory = scriptDirectory;
	}
	
	@Override
	public void addFrame ( GIFFrame newFrame) {
		if ( isCancelled())
			throw new RuntimeException( "Paused.");
		
		Image image = SwingFXUtils.toFXImage( newFrame.getImage(), null);
		cache.addFrame( new FrameCache( newFrame.getFrameTime(), image));
	}

	@Override
	public void setDimensions ( int width, int height) {
		return;
	}

	@Override
	public void writeToStream ( ImageOutputStream outputStream) throws IOException
	{
		throw new IOException( "Operation not supported.");
	}

	private int runInterpreter ( )
	{
		// initialize a 256x256 black background scene
		AnimationScene scene = new AnimationScene( );

		// create a Nashorn script engine
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("nashorn");

		@SuppressWarnings("unused")
		ScriptEnvironment environment = new ScriptEnvironment( scene, this, engine, scriptDirectory);

		// execute the script
		try {
			engine.eval( script);
			cache.raiseCacheComplete();
			
			Platform.runLater( () -> console.setText( "Output:\nDone."));
			
			// successful
			System.out.println( "Generator exit.");
			return 1;
		}
		catch ( ScriptException e)
		{
			String errorMessage = ScriptError.parseScriptException( e, "script").toString();
			Platform.runLater( () -> console.setText( "Output:\n" + errorMessage));

			System.out.println( "Generator exit.");
			return -1;
		}
		catch ( RuntimeException e)
		{
			if ( e.getMessage().equals( "Paused."))
				Platform.runLater( () -> console.setText( "Output:\nStopped."));
			else
			{
				String errorMessage = ScriptError.parseException( e, "script").toString();
				Platform.runLater( () -> console.setText( "Output:\n" + errorMessage));
			}
			
			System.out.println( "Generator exit.");
			return -1;
		}
		catch ( Exception e)
		{
			String errorMessage = ScriptError.parseException( e, "script").toString();
			Platform.runLater( () -> console.setText( "Output:\n" + errorMessage));
			
			System.out.println( "Generator exit.");
			return -1;
		}
	}

	@Override
	protected Integer call ( ) throws Exception
	{
		System.out.println( "Generator start.");
		return runInterpreter();
	}
}
