import java.util.function.Function;

public class Mapper<S,T> extends ValueGenerator<T> {

	private Function<S,T> map;
	private ValueGenerator<S> generator;
	
	public Mapper ( UpdateManager manager, ValueGenerator<S> source, Function<S,T> f)
	{
		super( manager);
		
		map = f;
		generator = source;
	}
	
	@Override
	public void update( double dt) {
		if ( !isRunning())
			return;
		
		generator.update( dt);
	}

	@Override
	public void revert  ( double dt) {
		generator.revert( dt);
	}

	@Override
	public boolean isComplete() {
		return generator.isComplete();
	}

	@Override
	public T getValue() {
		return map.apply( generator.getValue());
	}

	@Override
	public void reset() {
		generator.reset();
	}

	@Override
	public boolean isTop() {
		return false;
	}

	@Override
	public Updateable getParent() {
		return generator;
	}

	@Override
	public boolean isChildOf ( Updateable u) {
		return u == generator || generator.isChildOf( u);
	}

}
