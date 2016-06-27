
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Abstract representation of an object needs to be updated each frame */
/* not necessarily visible, used for tweens */

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
	
	// for the tree structure of dependent Updateables
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
