
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Exception: The requested module was not found */

package gifscript;

public class GifScriptModuleNotFoundException extends GifScriptModuleException {
	private static final long serialVersionUID = 7488161342022512110L;

	public GifScriptModuleNotFoundException(String message, String moduleName) {
		super(message, moduleName);
	}

}
