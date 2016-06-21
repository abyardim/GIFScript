import java.util.ArrayList;
import java.util.stream.Collectors;


// an interface for classes which update
// a collection of Updatable interfaces each frame

public interface UpdateManager {
	public void addUpdateable ( Updateable r);
	public void removeUpdateable ( Updateable r);
	public  ArrayList<Updateable> getUpdateables ( );
	public void update ( double dt);
}
