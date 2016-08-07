
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Exception: GIFScript failed to find a requested in-module resource    */

public class GifScriptModuleResourceNotFoundException extends
		GifScriptModuleException {

	private static final long serialVersionUID = -3479081573774565588L;
	String resourceName;
	
	public GifScriptModuleResourceNotFoundException ( String message, String moduleName, String resName) {
		super(message, moduleName);
		
		this.resourceName = resName;
	}
	
	public String getResourceName ( )
	{
		return resourceName;
	}

}
