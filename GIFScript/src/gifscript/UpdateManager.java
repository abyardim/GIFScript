
/* Copyright (c) 2016 Ali Batuhan Yardim                                 */
/* This work is available under the MIT License.                         */
/* Please see the file LICENSE in this distribution for license details. */

/* An interface for a collection of Updateables */
package gifscript;

import java.util.ArrayList;

// an interface for classes which update
// a collection of Updatable interfaces each frame

public interface UpdateManager {
	public void addUpdateable ( Updateable r);
	public void removeUpdateable ( Updateable r);
	public  ArrayList<Updateable> getUpdateables ( );
	public void update ( double dt);
}
