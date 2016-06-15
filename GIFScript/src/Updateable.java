
public interface Updateable {
	public void update ( double dt);
	public boolean isChildOf ( Updateable u);
	public boolean isTop ( );
	public Updateable getParent ( );
}
