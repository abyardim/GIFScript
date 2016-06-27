
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* Notify an observer script when a frame is rendered                    */

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
