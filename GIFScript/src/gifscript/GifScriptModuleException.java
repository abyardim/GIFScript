
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Topmost class for GIFScript module related exceptions */

package gifscript;

public class GifScriptModuleException extends Exception {
	private static final long serialVersionUID = -6238081109063136384L;
	
	public String moduleName;
	
	public GifScriptModuleException ( String message, String moduleName)
	{
		super( message);
		
		this.moduleName = moduleName;
	}
	
	public String getModuleName ( )
	{
		return moduleName;
	}
}
