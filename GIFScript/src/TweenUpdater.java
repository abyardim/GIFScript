
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Applies the value taken from a ValueGenerator to an object on the scene */
/* as a tween, updating each frame */

import java.awt.Color;
import java.awt.geom.Point2D;

public class TweenUpdater extends Updateable {

	private SceneObject target;
	@SuppressWarnings("rawtypes")
	private ValueGenerator generator;
	private String property;
	
	private double lastValue = 0;
	
	public TweenUpdater ( UpdateManager manager, SceneObject target, @SuppressWarnings("rawtypes") ValueGenerator generator, String property)
	{
		super( manager);
		
		this.target = target;
		this.generator = generator;
		this.property = property;
	}
	
	@Override
	public void update ( double dt) {
		
		if ( !isRunning())
			return;
		
		Sketchable sketch = (Sketchable) target;
		
		switch ( property)
		{
			//// tween stroke color
		case "sc":
			sketch.setStrokeColor( (Color) generator.getValue());
			break;
			
		case "sc.a":
			Color c = sketch.getStrokeColor();
			int newVal = clamp( getInteger( generator.getValue()));
			sketch.setStrokeColor( new Color( c.getRed(), c.getGreen(), c.getBlue(), newVal));
			break;
			
		case "sc.r":
			c = sketch.getStrokeColor();
			newVal = clamp( getInteger( generator.getValue()));
			sketch.setStrokeColor( new Color( newVal, c.getGreen(), c.getBlue(), c.getAlpha()));
			break;
			
		case "sc.g":
			c = sketch.getStrokeColor();
			newVal = clamp( getInteger( generator.getValue()));
			sketch.setStrokeColor( new Color( c.getRed(), newVal, c.getBlue(), c.getAlpha()));
			break;
			
		case "sc.b":
			c = sketch.getStrokeColor();
			newVal = clamp( getInteger( generator.getValue()));
			sketch.setStrokeColor( new Color( c.getRed(), c.getGreen(), newVal, c.getAlpha()));
			break;
		
			//// tween fill color:
		case "fc":
			sketch.setFillColor( (Color) generator.getValue());
			break;
			
		case "fc.a":
			c = sketch.getStrokeColor();
			newVal = clamp( getInteger( generator.getValue()));
			sketch.setFillColor( new Color( c.getRed(), c.getGreen(), c.getBlue(), newVal));
			break;
			
		case "fc.r":
			c = sketch.getStrokeColor();
			newVal = clamp( getInteger( generator.getValue()));
			sketch.setFillColor( new Color( newVal, c.getGreen(), c.getBlue(), c.getAlpha()));
			break;
			
		case "fc.g":
			c = sketch.getStrokeColor();
			newVal = clamp( getInteger( generator.getValue()));
			sketch.setFillColor( new Color( c.getRed(), newVal, c.getBlue(), c.getAlpha()));
			break;
			
		case "fc.b":
			c = sketch.getStrokeColor();
			newVal = clamp( getInteger( generator.getValue()));
			sketch.setFillColor( new Color( c.getRed(), c.getGreen(), newVal, c.getAlpha()));
			break;
			
			//// rotation
		case "r":
			double newValue = (Double) generator.getValue();
			target.rotate( newValue - lastValue);
			lastValue = newValue;
			break;
			
			//// translation
		case "p":
			Point2D.Double p = (Point2D.Double) generator.getValue();
			target.setLocation( p.x, p.y);
			break;
			
		case "p.x":
			target.setLocation( (Double) generator.getValue(), target.getTransformCenter().y);
			break;
			
		case "p.y":
			target.setLocation( target.getTransformCenter().x, (Double) generator.getValue());
			break;
			
			default:
				
		}
	}
	
	private static int clamp ( int n)
	{
		return Math.max( 0, Math.min( 255, n));
	}

	private static int getInteger ( Object o)
	{
		if ( o instanceof Double)
			return ( (Double) o).intValue();
		else if ( o instanceof Float)
			return ( (Float) o).intValue();
		else return (int) o;
	}

	
	public SceneObject getObject ( )
	{
		return target;
	}
	
	public String getPropertyID ( )
	{
		return property;
	}

	@Override
	public boolean isChildOf ( Updateable u) {
		return false;
	}

	@Override
	public boolean isTop() {
		return true;
	}

	@Override
	public Updateable getParent() {
		return null;
	}
}
