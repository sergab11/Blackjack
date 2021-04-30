package model;
import java.util.ArrayList;

public class Rodada {
	protected Fichas Apostas;
	protected ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	protected ArrayList<Baralho> baralhos = new ArrayList<Baralho>();
	protected Dealer dealer;
	static int jogadorVez = 0;
	static int numj;
	
	public Rodada(String jog) {
		Jogador j0 = new Jogador(jog); 
		jogadores.add(j0);
		numj = 1;
	}
	public Rodada(String jog1, String jog2) {
		Jogador j1 = new Jogador(jog1);
		Jogador j2 = new Jogador(jog2);
		jogadores.add(j1);
		jogadores.add(j2);
		numj = 2;
	}
	public Rodada(String jog1, String jog2, String jog3) {
		Jogador j1 = new Jogador(jog1);
		Jogador j2 = new Jogador(jog2);
		Jogador j3 = new Jogador(jog3);
		jogadores.add(j1);
		jogadores.add(j2);
		jogadores.add(j3);
		numj = 3;
	}
	public Rodada(String jog1, String jog2, String jog3, String jog4) {
		Jogador j1 = new Jogador(jog1);
		Jogador j2 = new Jogador(jog2);
		Jogador j3 = new Jogador(jog3);
		Jogador j4 = new Jogador(jog4);
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
