import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


public class ScriptEnvironment {
	private AnimationScene scene;
	private GIFWriter writer;
	private ScriptEngine engine;
	
	public ScriptEnvironment ( AnimationScene scene, GIFWriter writer, ScriptEngine engine)
	{
		this.scene = scene;
		this.writer = writer;
		this.engine = engine;
		
		try {
			buildDefaultEnvironment( );
		} catch ( Exception e)
		{
			e.printStackTrace();
			assert false;	// error preparing GIFScript environment
							// TODO: check if incompatible Java distribution?
		}
	}
	
	public void line ( double p1x, double p1y, double p2x, double p2y, Color c)
	{
		scene.addRenderable( new SLine( new Point2D.Double( p1x, p1y), new Point2D.Double( p2x, p2y), c ));
		System.out.println( "line_color: " + c);
	}
	
	public void line ( double p1x, double p1y, double p2x, double p2y)
	{
		line( p1x, p1y, p2x, p2y, Color.BLACK);
	}
	
	
	public void frame ( int delay, boolean clearScene)
	{
		scene.drawFrameTo( writer, delay, clearScene);
	}
	
	public void frame ( int delay)
	{
		frame( delay, true);
	}
	
	///// internal helper functions
	
	// load and map the functions/classes for use within scripts
	private void buildDefaultEnvironment( ) throws ScriptException, UnsupportedEncodingException
	{		
		engine.getBindings( ScriptContext.ENGINE_SCOPE).put( "_gifscript_env", this);
		
		// run initialization script
		InputStream initScript = getClass().getResourceAsStream( "/res/scripts/gifscript_environment.js");
		engine.eval( new InputStreamReader( initScript, "UTF-8"));		
	}
}
