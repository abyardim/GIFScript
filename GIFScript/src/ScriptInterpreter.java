import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.stream.ImageOutputStream;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptInterpreter {

	private String script;
	
	private ScriptEngine engine;
	private GIFWriter writer;
	private AnimationScene scene;
	
	@SuppressWarnings("unused")
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
		catch ( Exception e)
		{
			e.printStackTrace();
			return new ScriptError( e.getMessage(), -1, -1, "--"); // TODO: find a way to get the last line of the script
		}
	}
	
	public void writeGIF ( ImageOutputStream iout ) throws IIOException, IOException
	{
        writer.writeToStream( iout);
	}	
}
