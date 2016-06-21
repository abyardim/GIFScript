import java.util.function.DoubleConsumer;

public class FrameNotifier extends Updateable {
	private DoubleConsumer notify;
	 
	public FrameNotifier ( UpdateManager manager, DoubleConsumer notify)
	{
		super( manager);
		this.notify = notify;
	}

	@Override
	public void update(double dt) {
		if ( !isRunning())
			return;
		
		notify.accept( dt);
	}

	@Override
	public boolean isChildOf ( Updateable u) {
		return false;
	}

	@Override
	public boolean isTop ( ) {
		return true;
	}

	@Override
	public Updateable getParent ( ) {
		return null;
	}
}
