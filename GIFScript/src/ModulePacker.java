
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Packages given files to form a valid GIFScript module */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ModulePacker {
	
	private File initScript;
	
	public ModulePacker ( String initScript)
	{
		this.initScript = new File( initScript);
	}
	
	public void write ( ) throws GifScriptModuleNameUndefinedException, FileNotFoundException, IOException
	{
		File parentFolder = initScript.getParentFile();
		ArrayList<File> filesToPack = new ArrayList<File>();
		
		String moduleName = "";
		
		try ( BufferedReader br = new BufferedReader( new FileReader( initScript)))
		{		
			// list the files to be packed with the module
			String line;
			while( ( line = br.readLine()) != null) {
				line = line.trim();
				
				if ( line.isEmpty())
				{
					
				}
				else if ( line.startsWith("%"))		// module name declaration
				{
					moduleName = line.substring(1).trim();
				}
				else if ( line.contains( " "))		// resource file
				{
					String resFileName = line.substring( line.indexOf(' ')).trim();	
					filesToPack.add( new File( parentFolder, resFileName));
				}
				else								// script file
				{
					filesToPack.add( new File( parentFolder, line));
				}
			}
		}
		
		if ( moduleName.isEmpty())
			throw new GifScriptModuleNameUndefinedException("Module name unspecified");

		try ( ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream( new File(parentFolder, moduleName + ".gsc"))))
		{
			// add the initialization script to the archive
			putFileToZipOutputStream( initScript, "init", zipOut);
			
			// pack each file in module
			for ( File f : filesToPack)
			{
				putFileToZipOutputStream( f, f.getName(), zipOut);
			}
		}
	}
	
	private static void putFileToZipOutputStream ( File f, String entryName, ZipOutputStream zout) throws IOException
	{
		try ( FileInputStream fis = new FileInputStream( f))
		{
			zout.putNextEntry( new ZipEntry(entryName));
	
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zout.write(bytes, 0, length);
			}
			
			zout.closeEntry();
			fis.close();
		}
	}
}
