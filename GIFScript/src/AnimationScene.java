
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Implementation of a GIF scene manager */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;

public class AnimationScene implements UpdateManager {
	private ArrayList<Renderable> scene;
	private ArrayList<Updateable> updateables;
	private int width, height;
	private Color backgroundColor;
	private int frameCount;
	
	public AnimationScene ( )
	{
		width = height = 256;
		scene = new ArrayList<Renderable>();
		updateables = new ArrayList<>();
		backgroundColor = Color.BLACK;
		frameCount = 0;
	}
	
	public AnimationScene ( int width, int height, Color background)
	{
		this.width = width;
		this.height = height;
		scene = new ArrayList<Renderable>();
		updateables = new ArrayList<>();
		backgroundColor = background;
		frameCount = 0;
	}
	
	public AnimationScene ( int width, int height)
	{
		this.width = width;
		this.height = height;
		scene = new ArrayList<Renderable>();
		updateables = new ArrayList<>();
		backgroundColor = Color.BLACK;
		frameCount = 0;
	}
	
	public void addRenderable ( Renderable r)
	{
		scene.add( r);
	}
	
	public void removeRenderable ( Renderable r)
	{
		scene = scene.stream().filter( x -> x != r).collect( Collectors.toCollection( ArrayList::new));
	}

	public  ArrayList<Renderable> getRenderables ( )
	{
		return scene;
	}
	
	public void drawFrameTo ( GIFWriter writer, int frameTime, boolean clearScene)
	{
		BufferedImage newFrame = new BufferedImage( this.width, this.height, BufferedImage.TYPE_INT_ARGB);
		
		// initialize graphics context
		Graphics2D g2d = newFrame.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor( backgroundColor);
		g2d.fillRect( 0, 0, width, width);
		
		// order visible elements in their z ordering 
		scene.stream().filter( Renderable::isVisible)
						.sorted( Comparator.comparing( Renderable::getZ))
						.forEach( r -> r.render(g2d) );
		
		// TODO: save sorted version for performance?
		
		writer.setDimensions( width, height);
		writer.addFrame( new GIFFrame( frameCount, newFrame, frameTime));
		
		if ( clearScene)
		{
			for ( Renderable o : scene)
			{
				o.setVisible( false);
			}
		}
	}
	
	@Override
	public void addUpdateable ( Updateable r)
	{
		updateables.add( r);
		
		foldUpdateHierarchy ( );
	}
	
	@Override
	public void removeUpdateable ( Updateable r)
	{
		updateables = updateables.stream().filter( x -> x != r).collect( Collectors.toCollection( ArrayList::new));
	}

	@Override
	public  ArrayList<Updateable> getUpdateables ( )
	{
		return updateables;
	}
	
	@Override
	public void update ( double dt)
	{
		foldUpdateHierarchy ( );
		
		// convert to secs
		dt = dt / 1000;
		
		for ( Updateable u : updateables)
			u.update( dt);
	}

	//// getters / setters
	
	public int getWidth ( ) {
		return width;
	}

	public void setWidth ( int width) {
		this.width = width;
	}

	public int getHeight ( ) {
		return height;
	}

	public void setHeight ( int height) {
		this.height = height;
	}

	public int getFrameCount ( ) {
		return frameCount;
	}

	public Color getBackgroundColor ( ) {
		return backgroundColor;
	}

	public void setBackgroundColor ( Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	//// helpers
	
	// fold the update hierarchy to only include topmost elements
	private void foldUpdateHierarchy ( )
	{
		
		Iterator<Updateable> iter = updateables.iterator();
		
		while ( iter.hasNext())
		{
			Updateable u1 = iter.next();
			boolean remove = false;
			
			for ( Updateable u2 : updateables)
			{
				if ( u1.isChildOf( u2))
					remove = true;
			}
			
			if ( remove)
				iter.remove();
		}
		
		updateables = updateables.stream().distinct().collect( Collectors.toCollection(ArrayList::new));
	}
}
