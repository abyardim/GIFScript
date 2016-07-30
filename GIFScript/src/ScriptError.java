/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Represents a interpreter error encountered by GIFScript */

import javax.script.ScriptException;

public class ScriptError {
	private String message;
	private int lineNumber, columnNumber;
	private String fileDetails;
	
	public ScriptError( String message, int line, int column, String details)
	{
		this.message = message;
		this.lineNumber = line;
		this.columnNumber = column;
		this.fileDetails = details;
	}
		
	public String getMessage ( ) {
		return message;
	}

	public void setMessage ( String message) {
		this.message = message;
	}

	public int getLineNumber ( ) {
		return lineNumber;
	}

	public void setLineNumber ( int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getColumnNumber ( ) {
		return columnNumber;
	}

	public void setColumnNumber ( int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public String getFileDetails ( ) {
		return fileDetails;
	}

	public void setFileDetails ( String fileDetails) {
		this.fileDetails = fileDetails;
	}

	@Override
	public String toString ( )
	{
		if ( columnNumber < 0)
			return "Error @Line " + lineNumber + ": " + message;
		
		return "Error @Line " + lineNumber + ", Column " + columnNumber + ": " + message;
	}
	
	public static ScriptError parseScriptException ( ScriptException e)
	{
		String errorMessage = e.getMessage().replace( "<eval>", "script");
		
		return new ScriptError( e.getMessage(), e.getLineNumber(), e.getColumnNumber(), e.getFileName());
	}
	
	public static ScriptError parseScriptException ( ScriptException e, int lineNo, String fileName)
	{
		String errorMessage = e.getMessage().replace( "line number 1", "line number " + lineNo);
		errorMessage = errorMessage.replace( "line 1", "line " + lineNo);
		errorMessage = errorMessage.replace( "<eval>", "script");
		
		return new ScriptError( errorMessage, lineNo, e.getColumnNumber(), fileName);
	}
	
	public static ScriptError parseException ( Exception e, int lineNo, String fileName)
	{
		// TODO: map Nashorn error messages to be more meaningful in GIFScript's context
		
		return new ScriptError( e.getMessage(), lineNo, -1, fileName);
	}
}
