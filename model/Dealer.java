//Conrado Costa
//S�rgio Gabriel Vieira Bou�o

package model;

import java.util.ArrayList;
import view.ObserverDealer;

class Dealer implements SubjectDealer{
	Jogador j = new Jogador("Dealer");	//ser� feita uma composi��o da classe Dealer com a classe Jogador, j� que alguns m�todos de Jogador ser�o usados pelo Dealer
	private ArrayList<ObserverDealer> observers;
	private boolean esconde = true;	//vari�vel que determina quando a m�o do Dealer � revelada ou n�o
	
	Dealer(){
		observers = new ArrayList<ObserverDealer>();
	}
	
	@Override
	public void addObserver(ObserverDealer obs) {
		observers.add(obs);
	}
	
	@Override
	public void notifyObservers() {
		for(ObserverDealer o: observers)
			o.update(this.getMaoString(), this.somaMao(), esconde);
	}
	
	//m�todo para distribuir as primeiras duas cartas para cada jogador
	public void distribuiCartas(ArrayList<Jogador> jogadores, Baralho b) {
		int numj = jogadores.size();
		
		for(int i=0; i<numj; i++) {
			Carta c1 = b.getPrimeiraCarta();
			Carta c2 = b.getPrimeiraCarta();
			jogadores.get(i).adicionaCarta(c1, 0);
			jogadores.get(i).adicionaCarta(c2, 0);
		}
	}
	
	//m�todo para dar duas cartas para si mesmo e compor a m�o inicial
	void setMao(Baralho b) {
		Carta c1 = b.getPrimeiraCarta();
		Carta c2 = b.getPrimeiraCarta();
		j.adicionaCarta(c1, 0);
		j.adicionaCarta(c2, 0);
		notifyObservers();
	}
	
	boolean pedeCarta(Carta c) {
		boolean q = j.hit(c, 0);
		if(this.somaMao() == 21)
			q = true;
		notifyObservers();
		return q;
	}
	
	//m�todo para retornar a soma das cartas do Dealer
	int somaMao() { 
		return j.somaMao(0);
	}
	
	ArrayList<Carta> getMaoCartas(){
		return j.getMaoCartas(0);
	}
	
	ArrayList<String> getMaoString(){
		return j.getMaoString(0);
	}
	
	//m�todo que controla a exibi��o ou n�o das cartas da m�o do Dealer
	void setEscondeMao(boolean 	q) {
		esconde = q;
		notifyObservers();
	}
	
	void finalizaRodada() {
		j.finalizaRodada();
		notifyObservers();
	}
}