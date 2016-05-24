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
	
	private ArrayList<Renderable> scene;
	private String script;
	private int width, height;
	private Color backgroundColor;
	private int frameCount;
	
	private ScriptEngine engine;
	private GIFWriter animation;
	
	public ScriptInterpreter ( String script)
	{
		this.script = script;
	}
	
	public ScriptInterpreter ( File file) throws IOException
	{
		byte[] encoded = Files.readAllBytes( file.toPath());
		script = new String(encoded, StandardCharsets.UTF_8);
	}
	
	public void runScript ( ) throws ScriptException
	{
		// initialize a 256x256 scene
		width = height = 256;
		scene = new ArrayList<Renderable>();
		
		animation = new GIFWriter( 256, 256, -1);
		backgroundColor = Color.BLACK;
		frameCount = 0;
		
		// create a Nashorn script engine
		ScriptEngineManager factory = new ScriptEngineManager();
		engine = factory.getEngineByName("nashorn");
		
		// prepare the GIFscript environment
		setupEnvironment();
		
		// execute the script
		engine.eval( script);
	}
	
	public void writeGIF ( ImageOutputStream iout ) throws IIOException, IOException
	{
        animation.writeToStream( iout);
	}
	
	// TODO move these to another class
	// TODO dedicated to the Java-JavaScript interface
	public void line ( double p1x, double p1y, double p2x, double p2y, Color c)
	{
		scene.add( new SLine( new Point2D.Double( p1x, p1y), new Point2D.Double( p2x, p2y), c ));
		System.out.println( "line_color: " + c);
	}
	
	public void line ( double p1x, double p1y, double p2x, double p2y)
	{
		line( p1x, p1y, p2x, p2y, Color.BLACK);
	}
	
	
	public void frame ( int delay, boolean clearScene)
	{
		BufferedImage newFrame = new BufferedImage( this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = newFrame.createGraphics();
		g2d.setColor( backgroundColor);
		g2d.fillRect( 0, 0, width, width);
		
		for ( Renderable o : scene)
		{
			if ( o.isVisible())
				o.render( g2d);	
		}
		
		animation.addFrame( new GIFFrame( frameCount, newFrame, delay));
		
		if ( clearScene)
		{
			for ( Renderable o : scene)
			{
				o.setVisible( false);
			}
		}
	}
	
	public void frame ( int delay)
	{
		frame( delay, true);
	}
	
	///// internal helper functions
	
	// load and map the functions/classes for use within scripts
	private void setupEnvironment ( ) throws ScriptException
	{
		// TODO: map to an exclusive interface object
		
		engine.getBindings( ScriptContext.ENGINE_SCOPE).put( "_gifscript_env", this);
		
		// Java types:
		engine.eval( "var Color = Java.type(\"java.awt.Color\");");
		engine.eval( "var Point = Java.type(\"java.awt.geom.Point2D.Double\");");
		
		// map to static functions for convenience
		// TODO more readable version?
		engine.eval( "var line=function (a,b,c,d,e){if ( typeof e !== \"undefined\"){return _gifscript_env.line( a, b, c, d, e);} else {return _gifscript_env.line( a, b, c, d);}}");
		engine.eval( "var newFrame=function(d,clr){if( typeof clr !== \"undefined\"){return _gifscript_env.frame(d,clr);}else{return _gifscript_env.frame(d);}}");
	}
	
}
