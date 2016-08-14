
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* A manager for external file resources to be accessed by scripts */
package gifscript;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ResourceManager {
	private HashMap<String, BufferedImage> loadedImages;
	
	public ResourceManager ( )
	{
		loadedImages = new HashMap<String, BufferedImage>();
	}
	
	public boolean loadResource ( String key, File file) throws IOException
	{
		BufferedImage img;

		img = ImageIO.read( file);

		if ( img == null)
			return false;
		
		loadedImages.put( key, img);
		return true;
	}
	
	public boolean loadResource ( String key, byte[] data) throws IOException
	{
		BufferedImage img;
		
		try ( InputStream in = new ByteArrayInputStream( data))
		{
			img = ImageIO.read(in);
		}

		if ( img == null)
			return false;
		
		loadedImages.put( key, img);
		return true;
	}
	
	public BufferedImage getResource ( String key)
	{
		return loadedImages.get( key);
	}
	
	public void deleteResource ( String key )
	{
		loadedImages.remove( key);
	}
}
