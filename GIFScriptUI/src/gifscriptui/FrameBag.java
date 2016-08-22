package gifscriptui;

import java.util.ArrayList;
import java.util.List;

public class FrameBag {
	private List<FrameCache> cachedFrames;
	private boolean cachingComplete;
	
	public FrameBag ( )
	{
		this.cachedFrames = new ArrayList<>();
		this.cachingComplete = false;
	}
	
	public synchronized void addFrame ( FrameCache f)
	{
		cachedFrames.add( f);
		notifyAll();
	}
	
	public synchronized FrameCache getFrame ( int i)
	{
		return cachedFrames.get( i);
	}
	
	public synchronized int getFrameCount ( )
	{
		return cachedFrames.size();
	}
	
	public synchronized boolean isCachingComplete ( )
	{
		return cachingComplete;
	}
	
	public synchronized void raiseCacheComplete ( )
	{
		this.cachingComplete = true;
		notifyAll();
	}
}
