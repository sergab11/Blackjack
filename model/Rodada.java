package Model;
import java.util.ArrayList;

class Rodada {
	protected fichas Apostas;
	protected ArrayList<jogador> jogadores = new ArrayList<jogador>();
	protected ArrayList<baralho> baralhos = new ArrayList<baralho>();
	protected dealer Dealer;
	static int jogadorVez = 0;
	static int numj;
	
	Rodada(ArrayList<String> nomesJogadores) throws Exception {
		
		numj = jogadores.size();
		if (numj > 4) {
			throw new Exception("O limite de 4 jogadores foi excedido.") 
		}
		
		for (int i = 0; i < nomesJogadores.size(); i++) {
			jogador aux = new Jogador(nomesJogadores[i]);
			jogadores.add(aux);
		}
	}

	protected static void proximo() {
		jogadorVez++;
		if(jogadorVez == numj)
			jogadorVez = 0;
	}
	
	protected void adicionaAposta() {
		fichas montante = jogadores.get(jogadorVez).getAposta();
		Apostas.adicionaConjunto(montante);
	}
	
	public void dobra() {
		carta recebe = baralhos.get(0).getPrimeiraCarta();
		jogadores.get(jogadorVez).dobra(recebe);
	}
	
	public void hit() {
		carta recebe = baralhos.get(0).getPrimeiraCarta();
		jogadores.get(jogadorVez).hit(recebe);
	}
	
	public void stand() {
		adicionaAposta();
		jogadores.get(jogadorVez).limpaAposta();
		proximo();
	}

}
