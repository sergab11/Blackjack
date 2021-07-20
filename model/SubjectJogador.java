//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package model;

import view.*;

public interface SubjectJogador {
	public void addObserver(ObserverJogador observer);
	public void notifyObservers(int numMao);
}