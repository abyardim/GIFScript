
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Abstract representation for a */

import java.util.function.Function;

public abstract class ValueGenerator<T> extends Updateable {
	
	public ValueGenerator ( UpdateManager manager) {
		super(manager);
	}
	public abstract void revert ( double dt );
	public abstract boolean isComplete ( );
	public abstract void reset ( );
	public abstract T getValue ( );

	public <S> ValueGenerator<S> map ( Function<T,S> f)
	{
		return new Mapper<T,S>( this.getManager(), this, f);
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
