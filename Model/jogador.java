package Model;
import java.util.ArrayList;

public class jogador {
	protected String nome;
	protected ArrayList<carta> mao = new ArrayList<carta>();
	protected fichas conj;
	
	public jogador(String dnome) {
		nome = dnome;
		conj = new fichas();
	}
	public int getSaldo() {
		int saldo = conj.getSomatorio();
		return saldo;
	}
	
	public fichas getfichas() {
		return this.conj;
	}
	public void ganhouRodada(fichas rodada) {
		conj.adicionaConjunto(rodada);
	}
}
