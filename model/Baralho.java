package Model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Baralho {
    protected ArrayList<carta> completo = new ArrayList<carta>();

    Baralho() {
        ArrayList<String> numeros = new ArrayList<String>();
        ArrayList<String> naipes = new ArrayList<String>();
        numeros.add("A"); numeros.add("2"); numeros.add("3"); numeros.add("4");
        numeros.add("5"); numeros.add("6"); numeros.add("7"); numeros.add("8");
        numeros.add("9"); numeros.add("10"); numeros.add("J"); numeros.add("Q"); numeros.add("K");
        naipes.add("COPAS"); naipes.add("OURO"); naipes.add("ESPADA"); naipes.add("PAUS");
        carta carta;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                carta = new carta(naipes.get(i), numeros.get(j));
                completo.add(carta);
            }
        }
    }

    public void embaralhar() {
        Collections.shuffle(completo);
    }

    /*define quantas cartas serão embaralhadas
     * 
     */
    public void embaralhar(int emb) {
        for (int i = 0; i < emb; i++) {
            Random rand = new Random();
            carta carta;
            int num = rand.nextInt(50);
            carta = completo.remove(num);
            completo.add(carta);
        }
    }
    
    public carta getPrimeiraCarta() {
        return completo.remove(0);
    }
}
