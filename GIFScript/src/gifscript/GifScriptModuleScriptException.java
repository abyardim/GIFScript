
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Exception: A script-related error in the module to be loaded */

package gifscript;

public class GifScriptModuleScriptException extends GifScriptModuleException {
	private static final long serialVersionUID = 3379403804157393333L;
	private ScriptError error;
	
	public GifScriptModuleScriptException( String message, String moduleName, ScriptError error)
	{
		super(message, moduleName);
		
		this.error = error;
	}

	public ScriptError getScriptError ( )
	{
		return error;
	}
}
