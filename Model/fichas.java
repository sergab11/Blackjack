package Model;
import java.util.ArrayList;

public class fichas {
	protected ArrayList<Integer> valores;
	
	public fichas() {
		valores = new ArrayList<Integer>();
		valores.add(100); valores.add(100); valores.add(50); valores.add(50); valores.add(20); valores.add(20); valores.add(20);
		valores.add(20); valores.add(20); valores.add(10); valores.add(10); valores.add(10); valores.add(10); valores.add(10); 
		valores.add(5); valores.add(5); valores.add(5); valores.add(5); valores.add(5); valores.add(5); valores.add(5); valores.add(5);
		valores.add(1); valores.add(1); valores.add(1); valores.add(1); valores.add(1); valores.add(1); valores.add(1); valores.add(1);
		valores.add(1); valores.add(1);
	}
	
	public int getSomatorio() {
		int tam = valores.size();
		int somatorio=0;
		for(int i=0; i<tam; i++)
			somatorio = somatorio + valores.get(i);
		return somatorio;
	}
	
	protected int numFichas() {
		return this.valores.size();
	}
	
	public int getPrimeiraFicha() {
		return this.valores.remove(0);
	}
	
	public void adicionaConjunto(fichas conj) {
		int tam = conj.numFichas();
		for(int i=0; i<tam; i++) {
			int valor = conj.getPrimeiraFicha();
			adicionaFicha(valor);
		}
	}
	
	public void removeFicha(int tira) {
		int pos = this.valores.indexOf(tira);
		this.valores.remove(pos);
	}
	public void adicionaFicha(int adic) {
		this.valores.add(adic);
	}
}
