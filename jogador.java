package Model;

public class jogador {
	protected String nome;
	protected String [] mao;
	protected fichas conj;
	
	public jogador(String dnome) {
		nome = dnome;
		conj = new fichas();
	}
}