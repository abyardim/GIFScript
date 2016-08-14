
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Exception: The to-be-packed GIFScript module has no name assigned */

package gifscript;

public class GifScriptModuleNameUndefinedException extends GifScriptModuleException {
	private static final long serialVersionUID = 4311118387474112311L;

	 public GifScriptModuleNameUndefinedException ( String message)
	 {
		 super( message, "null");
	 }
}
