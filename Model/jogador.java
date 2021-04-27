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
}
