package Model;
import java.util.ArrayList;

public class rodada {
	protected fichas Apostas;
	protected ArrayList<jogador> jogadores = new ArrayList<jogador>();
	protected ArrayList<baralho> baralhos = new ArrayList<baralho>();
	protected dealer Dealer;
	static int jogadorVez = 0;
	static int numj;
	
	public rodada(String jog) {
		jogador j0 = new jogador(jog); 
		jogadores.add(j0);
		numj = 1;
	}
	public rodada(String jog1, String jog2) {
		jogador j1 = new jogador(jog1);
		jogador j2 = new jogador(jog2);
		jogadores.add(j1);
		jogadores.add(j2);
		numj = 2;
	}
	public rodada(String jog1, String jog2, String jog3) {
		jogador j1 = new jogador(jog1);
		jogador j2 = new jogador(jog2);
		jogador j3 = new jogador(jog3);
		jogadores.add(j1);
		jogadores.add(j2);
		jogadores.add(j3);
		numj = 3;
	}
	public rodada(String jog1, String jog2, String jog3, String jog4) {
		jogador j1 = new jogador(jog1);
		jogador j2 = new jogador(jog2);
		jogador j3 = new jogador(jog3);
		jogador j4 = new jogador(jog4);
		jogadores.add(j1);
		jogadores.add(j2);
		jogadores.add(j3);
		jogadores.add(j4);
		numj = 4;
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
