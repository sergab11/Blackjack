//Conrado Costa
//S�rgio Gabriel Vieira Bou�o

package view;

import java.util.*;

public interface ObserverJogador {
	public void update(int numMao, ArrayList<String> mao, int somaPontosMao, int valorAposta, int valorSaldo);
}