
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
