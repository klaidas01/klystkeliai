package Memento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {

	private List<Memento> mementoList = new ArrayList<Memento>();
	
	public void add(Memento state) {
		mementoList.add(state);
	}
	
	public void delete(int index) {
		if(index <= mementoList.size() - 1) {
			mementoList.remove(index);
		}
	}
	
	public Memento get(int index) {
		if(index > mementoList.size() - 1) {
			return null;
		}
		return mementoList.get(index);
	}
}