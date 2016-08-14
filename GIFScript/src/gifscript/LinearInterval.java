
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Implementation of linear value generator for tweened properties       */

package gifscript;

public class LinearInterval extends ValueGenerator<Double> {

	private double start, end, speed;
	private boolean toInfinity;
	private boolean positiveDirection;
	private double timeElapsed;
	private final double duration;
	
	public LinearInterval ( UpdateManager manager, double start, double end, double speed)
	{
		super( manager);
		
		toInfinity = false;
		this.end = end;
		this.speed = speed;
		this.start = start;
		
		this.setRepetitions( false);
		
		timeElapsed = speed;
		
		duration = Math.abs( ( start - end) / speed);
	}
	
	public LinearInterval ( UpdateManager manager, double start, double speed, boolean positive)
	{
		super( manager);
		
		toInfinity = true;
		positiveDirection = positive;
		this.speed = speed;
		this.start = start;
		
		timeElapsed = speed;
		
		this.setRepetitions( false);
		
		duration = -1;
	}
	
	@Override
	public void update ( double dt) {
		if ( !isRunning())
			return;
		
		timeElapsed += dt;
		
		if ( !toInfinity && timeElapsed >= duration && getRepetition())
		{
			timeElapsed = timeElapsed - duration;
		}
	}

	@Override
	public void revert( double dt) {
		timeElapsed -= dt;
	}

	@Override
	public boolean isComplete() {
		return !toInfinity && !getRepetition() && timeElapsed >= duration;
	}

	@Override
	public Double getValue() {
		if ( !toInfinity)
		{
			return isComplete() ? end : start + ( end - start) * timeElapsed / duration;
		}
		else if ( toInfinity && positiveDirection)
		{
			return start + speed * timeElapsed;
		}
		else{	// negative direction
			return start - speed * timeElapsed;
		}
	}

	@Override
	public void reset() {
		timeElapsed = 0;
	}

	@Override
	public boolean isChildOf ( Updateable u) {
		return false;
	}

	@Override
	public boolean isTop() {
		return true;
	}

	@Override
	public Updateable getParent() {
		return null;
	}

}
