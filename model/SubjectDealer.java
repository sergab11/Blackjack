//Conrado Costa
//S�rgio Gabriel Vieira Bou�o

package model;

import view.ObserverDealer;

public interface SubjectDealer {
	public void addObserver(ObserverDealer observer);
	public void notifyObservers();
}