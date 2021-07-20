//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package model;

import java.util.*;
import view.ObserverJogador;

class Jogador implements SubjectJogador{
	private String nome;
	
	private ArrayList<ArrayList<Carta>> MAOS = new ArrayList<ArrayList<Carta>>();	//guarda as "mãos" do jogador, ou seja, guarda conjuntos de cartas 
	private ArrayList<Integer> APOSTAS = new ArrayList<Integer>();	//guarda as apostas para cada mão
	
	private int saldo;
	private ArrayList<ObserverJogador> observers;
	private ArrayList<Integer> fichasAposta;

	Jogador(String n) {
		nome = n;
		this.saldo = 500;
		observers = new ArrayList<ObserverJogador>();
		fichasAposta = new ArrayList<Integer>(); fichasAposta.add(0);
		
		inicializaMao();
	}
	
	@Override
	public void addObserver(ObserverJogador obs) {
		observers.add(obs); 
	}
	
	@Override
	//avisa aos observadores o status atual da mão especificada em numMao; lembrando que a variável numMao existe, pois um jogador pode ter mais de uma mão devido a um Split 
	public void notifyObservers(int numMao) {
		for(ObserverJogador o: observers)
			o.update(numMao, this.getMaoString(numMao), this.somaMao(numMao), this.getAposta(numMao), this.getSaldo());
	}
	
	void iniciaGraphics(int nMao) {
		notifyObservers(nMao);
	}
	
	void adicionaCarta(Carta c1, int numMao) {
		MAOS.get(numMao).add(c1);
		notifyObservers(numMao);
	}
			
	boolean dobra(Carta tira, int numMao) throws Exception {
		int n = this.APOSTAS.get(numMao);
		incrementaAposta(n, numMao);
		adicionaCarta(tira, numMao);
		return this.verificaMao(numMao);
	}
	
	boolean hit(Carta tira, int numMao) {
		adicionaCarta(tira, numMao);
		return this.verificaMao(numMao); 
	}
	
	//método que realiza a operação de split e adiciona uma carta para a mão que fez split e para a nova mão
	void split(int numMao, Carta tira1, Carta tira2){
		if(MAOS.size() < 3) {
			inicializaMao(); //cria uma nova mão para o jogador
			
			Carta c1 = MAOS.get(numMao).remove(1);	//pega a carta que irá para a nova mão
			adicionaCarta(tira1, numMao);
			
			//adiciona as cartas e aposta à nova mão
			if(MAOS.size() == 3 && numMao == 0) {
				MAOS.get(numMao+2).add(c1);
				MAOS.get(numMao+2).add(tira2);
				incrementaAposta(APOSTAS.get(numMao), numMao+2);
			}
			else {
				MAOS.get(numMao+1).add(c1);
				MAOS.get(numMao+1).add(tira2);
				incrementaAposta(APOSTAS.get(numMao), numMao+1);
			}
			
			for(int i=0; i<MAOS.size(); i++)
				notifyObservers(i);
		}
	}
	
	void surrender(int numMao) throws Exception {
		adicionaSaldo(APOSTAS.get(numMao)/2);
		APOSTAS.set(numMao, 0);
		this.MAOS.get(numMao).clear();
		notifyObservers(numMao);
	}
	
	void incrementaAposta(int n, int numMao) {
		if ((this.APOSTAS.get(numMao)+n) <= this.saldo) {
			int antes = APOSTAS.get(numMao);
			APOSTAS.set(numMao, antes+n);
			this.saldo -= n;
		}
		fichasAposta.add(n);
		notifyObservers(numMao);
	}
	
	void decrementaAposta(int n) {
		int antes = APOSTAS.get(0);
		APOSTAS.set(0, antes-n);
		this.saldo += n;
		fichasAposta.remove(fichasAposta.size()-1);
		notifyObservers(0);
	}
	
	void adicionaSaldo(int n) {
		this.saldo += n;
	}
	
	boolean verificaMao(int numMao) {
		int soma = somaMao(numMao);
		if(soma >= 21)
			return false;
		else
			return true;
	}
	
	// método para retornar a pontuação das cartas do jogador
	int somaMao(int n) {
		int aux = 0;
		
		ArrayList<Integer> somas = new ArrayList<Integer>();
		somas.add(0);
		
		for(int i = 0; i < MAOS.get(n).size(); i++) {
			if (MAOS.get(n).get(i).getNumero() == "a") {
				int aux_tamanho = somas.size();
				for(int j = 0; j < aux_tamanho; j++) {
					aux = somas.get(j);
					somas.set(j, aux + 1);
					somas.add(aux + 11);
				}
			}
			else if (MAOS.get(n).get(i).getNumero() == "t" || MAOS.get(n).get(i).getNumero() == "j" || MAOS.get(n).get(i).getNumero() == "q" || MAOS.get(n).get(i).getNumero() == "k") {
				for(int j = 0; j < somas.size(); j++) {
					aux = somas.get(j)+10;
					somas.set(j, aux);
				}
			}
			else {
				for(int j = 0; j < somas.size(); j++) {
					aux = somas.get(j) + Integer.valueOf(MAOS.get(n).get(i).getNumero());
					somas.set(j, aux);
				}
			}
		}
		
		Collections.sort(somas, Collections.reverseOrder());
		
		for (int i = 0; i < somas.size(); i++) {
			if (somas.get(i) <= 21) {
				return somas.get(i);
			}
		}
		
		return somas.get(0);
	}
	
	int getSaldo() {
		return this.saldo;
	}
	
	int getAposta(int numMao) {
		return this.APOSTAS.get(numMao);
	}
	
	ArrayList<Carta> getMaoCartas(int numMao){
		return this.MAOS.get(numMao);
	}
	
	//retorna um array de string, onde cada uma das strings está no formato "xy", onde x é o número da carta e "y" é o naipe da mesma
	ArrayList<String> getMaoString(int numMao){
		ArrayList<String> ret = new ArrayList<String>();
		
		for(int i=0; i<MAOS.get(numMao).size(); i++) {
			String numcar = new String();
			numcar = MAOS.get(numMao).get(i).getNumeroNaipe();
			ret.add(numcar);
		}
		
		return ret;
	}
	
	boolean operacao(Carta carta, String op, int numMao) {
		boolean q = true;
		
		if(op.equals("Hit")) {
			q = this.hit(carta, numMao);
		}
		
		if(op.equals("Double")) {
			try {
				q = this.dobra(carta, numMao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(op.equals("Surrender")) {
			try {
				this.surrender(numMao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		notifyObservers(numMao);
		return q;
	}
	
	void vitoriaBlackjack() {
		this.saldo += 3*(this.APOSTAS.get(0));
		notifyObservers(0);
	}
	
	void vitoriaOrdinaria(int numMao) {
		this.saldo += 2*(this.APOSTAS.get(numMao));
		notifyObservers(0);
	}
	
	void empate(int numMao) {
		this.saldo += this.APOSTAS.get(numMao);
		notifyObservers(0);
	}
	
	void finalizaRodada() {
		this.fichasAposta.clear(); this.fichasAposta.add(0);
		MAOS.clear();
		APOSTAS.clear();
		
		inicializaMao();
		
		notifyObservers(0);
	}
	
	int getUltimaFicha() {
		if(fichasAposta.isEmpty())
			fichasAposta.add(0);
		return fichasAposta.get(fichasAposta.size()-1);
	}
	
	//método que retorna o número de mãos do jogador
	int numMaos() {
		return this.MAOS.size();
	}
	
	String getNome() {
		return this.nome;
	}
	
	void setSaldo(int n) {
		this.saldo = n;
	}
	
	void inicializaMao() {
		MAOS.add(new ArrayList<Carta>());
		APOSTAS.add(0);
	}
	
	int tamMao(int n) {
		return MAOS.get(n).size();
	}
	
	void setAposta(int nMao, int aposta) {
		APOSTAS.set(nMao, aposta);
	}
}