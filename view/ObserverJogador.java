//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package view;

import java.util.*;

public interface ObserverJogador {
	public void update(int numMao, ArrayList<String> mao, int somaPontosMao, int valorAposta, int valorSaldo);
}