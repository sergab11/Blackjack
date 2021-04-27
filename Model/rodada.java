package Model;
import java.util.ArrayList;

public class rodada {
	protected fichas apostas;
	protected ArrayList<jogador> jogadores = new ArrayList<jogador>();
	protected ArrayList<baralho> baralhos = new ArrayList<baralho>();
	
	public rodada(String jog) {
		jogador j0 = new jogador(jog); 
		jogadores.add(j0);
	}
	public rodada(String jog1, String jog2) {
		jogador j1 = new jogador(jog1);
		jogador j2 = new jogador(jog2);
		jogadores.add(j1);
		jogadores.add(j2);
	}
	public rodada(String jog1, String jog2, String jog3) {
		jogador j1 = new jogador(jog1);
		jogador j2 = new jogador(jog2);
		jogador j3 = new jogador(jog3);
		jogadores.add(j1);
		jogadores.add(j2);
		jogadores.add(j3);
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
	}
}
