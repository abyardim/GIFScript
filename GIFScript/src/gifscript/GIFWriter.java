
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Interface to a generic GIF exporter class, should export the provided */
/* frames to an ImageOutputStream                                        */

package gifscript;

import java.io.IOException;

import javax.imageio.IIOException;
import javax.imageio.stream.ImageOutputStream;

public interface GIFWriter {
	public void addFrame ( GIFFrame newFrame);
	public void setDimensions ( int width, int height);
	public void writeToStream ( ImageOutputStream outputStream) throws IIOException, IOException;
}
