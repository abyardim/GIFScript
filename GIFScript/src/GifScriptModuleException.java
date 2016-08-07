
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
