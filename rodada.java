package Model;

public class rodada {
	protected fichas [] apostas;
	protected jogador [] jogadores;
	protected baralho [] cartas;
	
	public rodada(String jog) {
		jogadores[0] = new jogador(jog);
	}
	public rodada(String jog1, String jog2) {
		jogadores[0] = new jogador(jog1);
		jogadores[1] = new jogador(jog2);
	}
	public rodada(String jog1, String jog2, String jog3) {
		jogadores[0] = new jogador(jog1);
		jogadores[1] = new jogador(jog2);
		jogadores[2] = new jogador(jog3);
	}
	public rodada(String jog1, String jog2, String jog3, String jog4) {
		jogadores[0] = new jogador(jog1);
		jogadores[1] = new jogador(jog2);
		jogadores[2] = new jogador(jog3);
		jogadores[3] = new jogador(jog4);
	}
}
