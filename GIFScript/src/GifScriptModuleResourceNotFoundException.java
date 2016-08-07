
public class GifScriptModuleResourceNotFoundException extends
		GifScriptModuleException {

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
