//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package controller;

import view.ObserverPartida;

public interface SubjectPartida {
	public void addObserver(ObserverPartida observer);
	public void notifyObservers();
	public void eventoIncrementaAposta(int x);
	public void eventoDecrementaAposta(int x);
	public void operacao(String s, int jog, int numMao);
}