import java.util.function.DoubleConsumer;

public class FrameNotifier implements Updateable {
	private DoubleConsumer notify;
	 
	public FrameNotifier ( DoubleConsumer notify)
	{
		this.notify = notify;
	}

	@Override
	public void update(double dt) {
		notify.accept( dt);
	}

	@Override
	public boolean isChildOf(Updateable u) {
		return false;
	}

	@Override
	public boolean isTop() {
		return true;
	}

	@Override
	public Updateable getParent ( ) {
		return null;
	}
}
