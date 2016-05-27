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
import jdk.nashorn.api.scripting.ScriptUtils;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

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
	
	public SLine drawLine ( double p1x, double p1y, double p2x, double p2y, Color c)
	{
		SLine line = new SLine( new Point2D.Double( p1x, p1y), new Point2D.Double( p2x, p2y), c );
		scene.addRenderable( line);
		
		return line;
	}
	
	public SArc drawCircle ( double centerx, double centery, double radius, boolean fill, 
								boolean stroke, Color strokeColor, Color fillColor)
	{
		 SArc arc = new SArc( new Point2D.Double( centerx, centery), radius, radius, strokeColor);
		 arc.setDrawFill( fill);
		 arc.setDrawStroke( stroke);
		 
		 if ( fill)
			 arc.setFillColor( fillColor);
		 if ( stroke)
			 arc.setDrawStroke( stroke);
		 
		 scene.addRenderable( arc);
		 
		 return arc;
	}
	
	public SArc drawOval ( double centerx, double centery, double radius1, double radius2,
							boolean fill, boolean stroke, Color strokeColor, Color fillColor)
	{
		SArc arc = new SArc( new Point2D.Double( centerx, centery), radius1, radius2, strokeColor);
		arc.setDrawFill( fill);
		arc.setDrawStroke( stroke);

		if ( fill)
			arc.setFillColor( fillColor);
		if ( stroke)
			arc.setDrawStroke( stroke);
		
		scene.addRenderable( arc);

		return arc;
	}
	
	public SArc drawArc ( double centerx, double centery, double radius1, double radius2, double startAngle, 
							double stopAngle, boolean fill, boolean stroke, Color strokeColor, Color fillColor)
	{
		SArc arc = drawOval( centerx, centery, radius1, radius2, fill, stroke, strokeColor, fillColor);
		arc.setAngleStart( startAngle);
		arc.setAngleStop( stopAngle);
		
		scene.addRenderable( arc);
		
		return arc;
	}
	
	public SPolygon drawPolygon ( Point2D.Double[] points, boolean stroke, boolean fill, Color strokeColor, Color fillColor)
	{
		// boolean a = ((ScriptObjectMirror) points).isArray();
		
		// Point2D.Double[] pointData = ( Point2D.Double[]) ScriptUtils.convert( points, Point2D.Double[].class);
		
		SPolygon poly = new SPolygon( points);
		poly.setDrawFill( fill);
		poly.setDrawStroke( stroke);
		
		if ( stroke)
			poly.setStrokeColor( strokeColor);
		if ( fill)
			poly.setStrokeColor( fillColor);
		
		scene.addRenderable( poly);
		
		return poly;
	}
	
	///// GIF property control
	
	public void newFrame ( int delay, boolean clearScene)
	{
		scene.drawFrameTo( writer, delay, clearScene);		
	}
	
	public void setGIFDimensions ( int width, int height)
	{
		writer.setDimensions( width, height);
		scene.setHeight( height);
		scene.setWidth( width);
	}
	
	public void setBackgroundColor ( Color c)
	{
		scene.setBackgroundColor( c);
	}
	
	public void addGIFComment ( String comment)
	{
		/// TODO
	}
	
	///// internal helper functions
	
	// load and map the functions/classes for use within scripts
	private void buildDefaultEnvironment( ) throws ScriptException, UnsupportedEncodingException
	{		
		engine.getBindings( ScriptContext.ENGINE_SCOPE).put( "_gifscript_env", this);
		
		// run initialization script, loaded from class resources
		InputStream initScript = getClass().getResourceAsStream( "/res/scripts/gifscript_environment.js");
		engine.eval( new InputStreamReader( initScript, "UTF-8"));		
	}
}
