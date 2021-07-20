//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package model;

import view.ObserverDealer;

public interface SubjectDealer {
	public void addObserver(ObserverDealer observer);
	public void notifyObservers();
}