import java.util.function.Function;

public abstract class ValueGenerator<T> implements Updateable {
	
	public abstract void revert ( double dt );
	public abstract boolean isComplete ( );
	public abstract void reset ( );
	public abstract T getValue ( );

	public <S> ValueGenerator<S> map ( Function<T,S> f)
	{
		return new Mapper<T,S>( this, f);
	}
	
	private boolean repetitions;
	
	public boolean getRepetition ( )
	{
		return repetitions;
	}
	
	public void setRepetitions ( boolean b)
	{
		repetitions = b;
	}
}
