import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class ResourceManager {
	private HashMap<String, BufferedImage> loadedImages;
	
	public ResourceManager ( )
	{
		loadedImages = new HashMap<String, BufferedImage>();
	}
	
	public boolean loadResource ( String key, File file)
	{
		BufferedImage img;
		try {
			img = ImageIO.read( file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
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
