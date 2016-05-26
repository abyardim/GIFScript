import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Stream;

public class AnimationScene {
	private ArrayList<Renderable> scene;
	private int width, height;
	private Color backgroundColor;
	private int frameCount;
	
	public AnimationScene ( )
	{
		width = height = 256;
		scene = new ArrayList<Renderable>();
		backgroundColor = Color.BLACK;
		frameCount = 0;
	}
	
	public AnimationScene ( int width, int height, Color background)
	{
		this.width = width;
		this.height = height;
		scene = new ArrayList<Renderable>();
		backgroundColor = background;
		frameCount = 0;
	}
	
	public AnimationScene ( int width, int height)
	{
		this.width = width;
		this.height = height;
		scene = new ArrayList<Renderable>();
		backgroundColor = Color.BLACK;
		frameCount = 0;
	}
	
	public void addRenderable ( Renderable r)
	{
		scene.add( r);
	}

	public  ArrayList<Renderable> getRenderables ( )
	{
		return scene;
	}
	
	public void drawFrameTo ( GIFWriter writer, int frameTime, boolean clearScene)
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
	
	public void update ( )
	{
		// TODO: update logic for animated elements
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
	

}
