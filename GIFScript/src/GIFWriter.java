
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.IIOException;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

// a class for creating gifs from a sequence of 
public class GIFWriter {
	private int width, height;
	//private Color backgroundColor;
	private ArrayList<GIFFrame> frames;
	
	// number of loops the GIF to be played
	// -1 for continuous looping
	private int loopCount;
	
	
	public GIFWriter ( )
	{
		frames = new ArrayList<GIFFrame>();
		width = 256; 
		height = 256;
		loopCount = -1;
	}
	
	public GIFWriter ( int width, int height)
	{
		frames = new ArrayList<GIFFrame>();
		this.width = width; 
		this.height = height;
		loopCount = -1;
	}
	
	public GIFWriter ( int width, int height, int loop)
	{
		frames = new ArrayList<GIFFrame>();
		this.width = width; 
		this.height = height;
		loopCount = loop;
	}
	
	public GIFWriter ( int width, int height, GIFFrame scene)
	{
		frames = new ArrayList<GIFFrame>();
		this.width = width; 
		this.height = height;
		loopCount = -1;
		
		addFrame( scene);
	}
	
	public void addFrame ( GIFFrame newFrame)
	{				
		frames.add( newFrame);
		
		resizeFrames();
	}
	
	public void setDimensions ( int width, int height)
	{
		if ( width == this.width && height == this.height)
			return;
		
		this.width = width;
		this.height = height;
		
		resizeFrames();
	}
	
	public void writeToStream ( ImageOutputStream outputStream) throws IIOException, IOException
	{
		if ( frames.isEmpty())
			return;
		
		ImageWriter writer = ImageIO.getImageWritersBySuffix("gif").next();
		
		ImageWriteParam imageWriteParam;
		IIOMetadata imageMetaData;
		
		imageWriteParam = writer.getDefaultWriteParam();

		ImageTypeSpecifier imageTypeSpecifier = ImageTypeSpecifier.createFromBufferedImageType( frames.get( 0).getImage().getType());
		imageMetaData = writer.getDefaultImageMetadata( imageTypeSpecifier, imageWriteParam);

		String metaFormatName = imageMetaData.getNativeMetadataFormatName();

		IIOMetadataNode root = (IIOMetadataNode) imageMetaData.getAsTree( metaFormatName);

		IIOMetadataNode graphicsControlExtensionNode = getNode( root, "GraphicControlExtension");

		graphicsControlExtensionNode.setAttribute("disposalMethod", "none");
		graphicsControlExtensionNode.setAttribute("userInputFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute("transparentColorFlag", "FALSE");
		graphicsControlExtensionNode.setAttribute( "delayTime", Integer.toString( frames.get( 0).getFrameTime() / 10));
		graphicsControlExtensionNode.setAttribute( "transparentColorIndex", "0");

		IIOMetadataNode commentsNode = getNode(root, "CommentExtensions");
		commentsNode.setAttribute("CommentExtension", "GIFScript");

		IIOMetadataNode appEntensionsNode = getNode( root, "ApplicationExtensions");
		IIOMetadataNode child = new IIOMetadataNode("ApplicationExtension");
		child.setAttribute("applicationID", "NETSCAPE");
		child.setAttribute("authenticationCode", "2.0");

		int loop = loopCount == -1 ? 0 : 1;

		child.setUserObject(new byte[]{ 0x1, (byte) (loop & 0xFF), (byte) ((loop >> 8) & 0xFF)});
		appEntensionsNode.appendChild(child);

		imageMetaData.setFromTree(metaFormatName, root);

		writer.setOutput(outputStream);

		writer.prepareWriteSequence(null);
		
		////////// write each frame in sequence
		
		for ( GIFFrame frame : frames)
		{
			writer.writeToSequence( new IIOImage( frame.getImage(), null, imageMetaData), imageWriteParam);
		}
		 
		 writer.endWriteSequence();    
	}

	
	//// internal methods
	
	// crop / scale frames to match GIF dimensions
	private void resizeFrames ( )
	{
		for ( int i = 0; i < frames.size(); i++)
		{
			GIFFrame currentFrame = frames.get( i);
			
			if ( 	currentFrame.getImage().getWidth() >= this.width &&
					currentFrame.getImage().getWidth() >= this.height)
			{
				currentFrame = new GIFFrame ( 	currentFrame.getIndex(), 
												currentFrame.getImage().getSubimage( 0, 0, width, height),
												currentFrame.getFrameTime());
				
				frames.set( i, currentFrame);
			}
			else if ( 	currentFrame.getImage().getWidth() < this.width &&
						currentFrame.getImage().getWidth() < this.height)
			{
				Image temp = currentFrame.getImage().getScaledInstance( width, height, Image.SCALE_SMOOTH);
			    BufferedImage scaledImage = new BufferedImage( width, height, BufferedImage.TYPE_INT_ARGB);

			    Graphics2D g2d = scaledImage.createGraphics();
			    g2d.drawImage( temp, 0, 0, null);
			    g2d.dispose();
			    
			    currentFrame = new GIFFrame ( 	currentFrame.getIndex(), 
												scaledImage,
												currentFrame.getFrameTime());
			    
			    frames.set( i, currentFrame);
			}
		}
	}

	private static IIOMetadataNode getNode(
			IIOMetadataNode rootNode,
			String nodeName) {
		int nNodes = rootNode.getLength();
		for (int i = 0; i < nNodes; i++) {
			if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)
					== 0) {
				return((IIOMetadataNode) rootNode.item(i));
			}
		}
		IIOMetadataNode node = new IIOMetadataNode(nodeName);
		rootNode.appendChild(node);
		return(node);
	}
	
	// test for this class
	public static void main ( String[] args) throws IOException
	{
		BufferedImage img = new BufferedImage( 100, 100, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setColor( Color.BLACK);
        g.fillRect( 0, 0, 100, 100);
        g.setColor( Color.RED);
        g.drawLine( 0, 0, 100, 0);
        
        GIFWriter writer = new GIFWriter( 100, 100, -1);
        
        writer.addFrame( new GIFFrame( 0, ImageHelpers.copyImage( img), 10));
        
        for(int i=1; i<100; i++) {
        	g.setColor( new Color( (float) Math.random(), (float) Math.random(), (float) Math.random()));
        	g.drawLine( i, 0, 100 + i, 100);
        	g.drawLine( 0, i, 100, 100 + i);
        	writer.addFrame( new GIFFrame( 0, ImageHelpers.copyImage( img), 10));
        }
        
        ImageOutputStream out = new FileImageOutputStream(new File( "customWriter.gif"));
        
        writer.writeToStream( out);
        
        out.close();
        
	}
}
