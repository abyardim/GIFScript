import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;
import java.awt.geom.Point2D;
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
	
	public SLine drawLine ( double p1x, double p1y, double p2x, double p2y, Color c)
	{
		SLine line = new SLine( new Point2D.Double( p1x, p1y), new Point2D.Double( p2x, p2y), c );
		line.setTransformCenter( ( p1x + p2x) / 2, ( p1y + p2y) / 2);
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
		 
		 arc.setTransformCenter( centerx, centery);
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
		
		arc.setTransformCenter( centerx, centery);
		scene.addRenderable( arc);

		return arc;
	}
	
	public SArc drawArc ( double centerx, double centery, double radius1, double radius2, double startAngle, 
							double stopAngle, boolean fill, boolean stroke, Color strokeColor, Color fillColor)
	{
		SArc arc = drawOval( centerx, centery, radius1, radius2, fill, stroke, strokeColor, fillColor);
		arc.setAngleStart( startAngle);
		arc.setAngleStop( stopAngle);
		
		arc.setTransformCenter( centerx, centery);
		scene.addRenderable( arc);
		
		return arc;
	}
	
	public SPolygon drawPolygon ( Point2D.Double[] points, boolean stroke, boolean fill, Color strokeColor, Color fillColor)
	{
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
	
	public SLineStrip drawLineStrip ( Stroke s, Point2D.Double... points)
	{
		SLineStrip strip = new SLineStrip( points);
		scene.addRenderable( strip);
		
		return strip;
	}
	
	public SCurvedPath drawCurvedPath ( Point2D.Double start, Stroke s)
	{
		SCurvedPath cpath = new SCurvedPath( start);
		scene.addRenderable( cpath);
		
		return cpath;
	}
	
	public SBezier2 drawBezier2 ( Point2D.Double pstart, Point2D.Double cp, Point2D.Double pstop, Stroke s)
	{
		SBezier2 bezier2 = new SBezier2 ( pstart, cp, pstop);
		bezier2.setStroke( s);
		
		scene.addRenderable( bezier2);
		
		return bezier2;
	}
	
	public SBezier3 drawBezier3 ( Point2D.Double pstart, Point2D.Double cp1, Point2D.Double cp2, Point2D.Double pstop, Stroke s)
	{
		SBezier3 bezier3 = new SBezier3 ( pstart, cp1, cp2, pstop);
		bezier3.setStroke( s);
		
		scene.addRenderable( bezier3);
		
		return bezier3;
	}
	
	public SGroup groupObjects ( SceneObject... objects)
	{
		SGroup group = new SGroup();
		group.add( objects);
		
		scene.addRenderable( group);
		
		return group;
	}
	
	public SCombinedArea combinePaths ( SGeometricPrimitive... objects)
	{
		SCombinedArea group = new SCombinedArea();
		group.add( objects);
		
		scene.addRenderable( group);
		
		for ( SGeometricPrimitive g : objects)
			scene.removeRenderable( g);
		
		return group;
	}
	
	public SText drawText ( String text, double posx, double posy, Font font)
	{
		SText textElement = new SText ( text);
		textElement.setFont( font);
		textElement.setLocation( posx, posy);
		
		scene.addRenderable( textElement);
		
		return textElement;
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
		/// TODO GIF comment extension
	}
	
	public void clearAllComments ( )
	{
		/// TODO GIF comment extension
	}
	
	///// internal helper functions
	
	// load and map the functions/classes for use within scripts
	private void buildDefaultEnvironment( ) throws ScriptException, UnsupportedEncodingException
	{
		// bind the environment variable
		engine.getBindings( ScriptContext.ENGINE_SCOPE).put( "_gifscript_env", this);
		
		// run initialization script
		InputStream initScript = getClass().getResourceAsStream( "/res/scripts/gifscript_environment.js");
		engine.eval( new InputStreamReader( initScript, "UTF-8"));		
	}
}
