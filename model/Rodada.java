package model;
import java.util.ArrayList;

class Rodada {
	protected Fichas Apostas;
	protected ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	protected ArrayList<Baralho> baralhos = new ArrayList<Baralho>();
	protected Dealer dealer;
	static int jogadorVez = 0;
	static int numj;
	
	Rodada(ArrayList<String> nomesJogadores) throws Exception {
		
		numj = jogadores.size();
		if (numj > 4) {
			throw new Exception("O limite de 4 jogadores foi excedido."); 
		}
		
		for (int i = 0; i < nomesJogadores.size(); i++) {
			Jogador aux = new Jogador(nomesJogadores.get(i));
			jogadores.add(aux);
		}
	}

	protected static void proximo() {
		jogadorVez++;
		if(jogadorVez == numj)
			jogadorVez = 0;
	}
	
	protected void adicionaAposta() {
		Fichas montante = jogadores.get(jogadorVez).getAposta();
		Apostas.adicionaConjunto(montante);
	}
	
	public void dobra() {
		Carta recebe = baralhos.get(0).getPrimeiraCarta();
		jogadores.get(jogadorVez).dobra(recebe);
	}
	
	public void hit() {
		Carta recebe = baralhos.get(0).getPrimeiraCarta();
		jogadores.get(jogadorVez).hit(recebe);
	}
	
	public void stand() {
		adicionaAposta();
		jogadores.get(jogadorVez).limpaAposta();
		proximo();
	}

}
