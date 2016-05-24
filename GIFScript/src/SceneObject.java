
public abstract class SceneObject implements Renderable, Transformable, Scaleable {
	private boolean visible = true;
	
	@Override
	public void setVisible ( boolean v)
	{
		this.visible = v;
	}
	
	@Override
	public boolean isVisible ( )
	{
		return visible;
	}
}
