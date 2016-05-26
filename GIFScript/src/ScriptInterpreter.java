import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptInterpreter {

	private String script;
	
	private ScriptEngine engine;
	private GIFWriter writer;
	private AnimationScene scene;
	private ScriptEnvironment environment;
	
	public ScriptInterpreter ( String script)
	{
		this.script = script;
	}
	
	public ScriptError runScript ( )
	{
		// initialize a 256x256 black background scene
		scene = new AnimationScene( );
		writer = new GIFWriter( 256, 256, -1);
		
		// create a Nashorn script engine
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("nashorn");
		
		environment = new ScriptEnvironment( scene, writer, engine);
		
		// execute the script
		try {
			engine.eval( script);
			
			// successful
			return null;
		} catch ( ScriptException e)
		{
			return ScriptError.parseException( e);
		}
	}
	
	public void writeGIF ( ImageOutputStream iout ) throws IIOException, IOException
	{
        writer.writeToStream( iout);
	}	
}
