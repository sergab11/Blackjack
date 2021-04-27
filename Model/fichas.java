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
	
	public int getSaldo() {
		int tam = valores.size();
		int saldo=0;
		for(int i=0; i<tam; i++)
			saldo = saldo + valores.get(i);
		return saldo;
	}
}
