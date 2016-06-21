
public abstract class Updateable {
	
	private UpdateManager manager;
	
	public Updateable ( UpdateManager manager)
	{
		this.manager = manager;
	}
	
	public UpdateManager getManager()
	{
		return manager;
	}
	
	public abstract void update ( double dt);
	public abstract boolean isChildOf ( Updateable u);
	public abstract boolean isTop ( );
	public abstract Updateable getParent ( );
	
	private boolean running = true;
	
	public void stop ()
	{
		running = false;
	}
	
	public void run ()
	{
		running = true;
	}
	
	public boolean isRunning ()
	{
		return running;
	}
}
