
public class GifScriptModuleScriptException extends GifScriptModuleException {
	private static final long serialVersionUID = 1L;
	
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
