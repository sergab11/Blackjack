//Conrado Costa
//S�rgio Gabriel Vieira Bou�o

package model;

import view.*;

public interface SubjectJogador {
	public void addObserver(ObserverJogador observer);
	public void notifyObservers(int numMao);
}