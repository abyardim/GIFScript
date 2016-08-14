
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Program entry point */
/* Accepts the script path and the image export path as command line     */
/* parameters */

package gifscript;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

public class Main {

    public static void main ( String[] args)
    {
    	if ( args.length != 2 && args.length != 3)
    	{
    		System.out.println( "Wrong number of parameters.");
    		return;
    	}
    	
    	if ( args[0].equals( "-p"))	// option: pack a module
    	{
    		ModulePacker packer = new ModulePacker(args[1]);
    		
    		try {
    			// export the GIFScript module
				packer.write();
			} catch ( GifScriptModuleNameUndefinedException e) {
				System.out.println( "Module has no name specified.");
			}
    		catch ( FileNotFoundException e)
    		{
				e.printStackTrace();
			}
    		catch ( IOException e)
    		{
				e.printStackTrace();
			}
    	}
    	else if ( args[0].equals( "-b")) // run a script
    	{
	    	ScriptInterpreter interpreter;
			try {
				// load script from file
				byte[] encoded = Files.readAllBytes( new File(args[1]).toPath());
				String script = new String(encoded, StandardCharsets.UTF_8);
				
				interpreter = new ScriptInterpreter( script);
			} catch ( IOException e1) {
				// script file not found
				e1.printStackTrace();
				return;
			}
	    	
	    	// run script
			ScriptError error = interpreter.runScript();
	    	if ( error != null)
	    	{
	    		System.out.println( error);
	    	}
			
	    	try ( ImageOutputStream out = new FileImageOutputStream( new File( args[2]))){
	    		interpreter.writeGIF( out);
	    	} catch ( Exception e)
	    	{
	    		e.printStackTrace();
	    		return;
	    	}
    	}
    }
}