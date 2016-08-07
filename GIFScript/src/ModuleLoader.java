
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* An utility class for loading the contents of a GIFScript module into  */
/* a GIFScript environment current environment    */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.script.ScriptException;

public class ModuleLoader {
	
	private String moduleName;
	
	public ModuleLoader ( String moduleName)
	{
		this.moduleName = moduleName;
	}
	
	public void load ( ScriptEnvironment env) throws GifScriptModuleNotFoundException, 
														GifScriptModuleScriptException, 
														GifScriptModuleResourceNotFoundException
	{
		File moduleFile = new File( moduleName + ".gsc");
		
		
		try ( ZipFile zip = new ZipFile( moduleFile))
		{
			ZipEntry initEntry = zip.getEntry( "init");
			
			if ( initEntry == null)
				throw new GifScriptModuleResourceNotFoundException( "Init script missing.", moduleName, "init");
			
			List<String> initLines =  new BufferedReader( new InputStreamReader( zip.getInputStream( initEntry))).lines()
										.collect( Collectors.toList());
			
			for ( String line : initLines)
			{
				line = line.trim();
				
				if ( line.startsWith( "%"))
				{
					// name of the GS module
				}
				else if ( line.indexOf(' ') >= 0)	// resources
				{
					// TODO module resource loading
				}
				else
				{
					ZipEntry entry = zip.getEntry( line);
					
					if ( entry == null)
						throw new GifScriptModuleResourceNotFoundException( "Script file missing.", moduleName, line);
					
					try( InputStream is = zip.getInputStream( entry))
					{
						String script = convertStreamToString( is);
						
						// evaluate the script contents in the Nashorn engine
						try
						{
							env.loadModuleScript(script);
						}
						catch ( ScriptException e)
						{
							throw new GifScriptModuleScriptException( "@Module " + moduleName + ": Script eval error", line, ScriptError.parseScriptException( e, line));
						}
						catch ( Exception e)
						{
							throw new GifScriptModuleScriptException( "@Module " + moduleName + ": Script eval error", line, ScriptError.parseException( e, line));
						}
					}
				}
			}
		}
		catch ( FileNotFoundException e)
		{
			throw new GifScriptModuleNotFoundException( "Module " + moduleName + " not found.", moduleName);
		}
		catch ( IOException e)
		{
			throw new GifScriptModuleNotFoundException( "Module " + moduleName + " not readable.", moduleName);
		}
	}
	
	private static String convertStreamToString( InputStream is) {
	    @SuppressWarnings("resource")
		Scanner s = new Scanner(is).useDelimiter("\\A");
	    String res = s.hasNext() ? s.next() : "";
	    s.close();
	    
	    return res;
	}
}
