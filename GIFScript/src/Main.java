//
//import jdk.nashorn.api.scripting.ClassFilter;
//import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.script.ScriptException;

public class Main {

    public static void main(String[] args){
    	if ( args.length != 2 )
    	{
    		System.out.println( "Parameters not recognized.");
    		return;
    	}
    	
    	ScriptInterpreter interpreter;
		try {
			interpreter = new ScriptInterpreter( new File( args[0]));
		} catch (IOException e1) {
			// script file not found
			e1.printStackTrace();
			return;
		}
    	
    	// run script from file
    	try {
			interpreter.runScript();
		} catch (ScriptException e) {
			e.printStackTrace();
			return;
		}
    	
    	try {
    		ImageOutputStream out = new FileImageOutputStream(new File( args[1]));
    		interpreter.writeGIF( out);
    		out.close();
    	} catch ( Exception e)
    	{
    		e.printStackTrace();
    		return;
    	}
    }


}