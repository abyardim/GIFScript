
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Program entry point */
/* Accepts the script path and the image export path as command line     */
/* parameters */

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

public class Main {

    public static void main(String[] args){
    	if ( args.length != 2 )
    	{
    		System.out.println( "Parameters not recognized.");
    		return;
    	}
    	
    	ScriptInterpreter interpreter;
		try {
			// load script from file
			byte[] encoded = Files.readAllBytes( new File(args[0]).toPath());
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
		
    	try {
    		ImageOutputStream out = new FileImageOutputStream( new File( args[1]));
    		interpreter.writeGIF( out);
    		out.close();
    	} catch ( Exception e)
    	{
    		e.printStackTrace();
    		return;
    	}
    }
}