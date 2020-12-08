package server;

import java.util.List;

public abstract class Observable {
	
	List<IObserver> obs;
	
	public void attach(IObserver observer) {
		obs.add(observer);
	}

	public void detach(IObserver observer) {
		obs.remove(observer);
	}
	
	public void notifyObs(String msg) {
		obs.forEach(observer -> observer.update(msg));
	}
	
}
