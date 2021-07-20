//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

class Baralho {
    protected ArrayList<Carta> completo = new ArrayList<Carta>();	//completo é a variável que guarda os 4 baralhos

    Baralho() {
        ArrayList<String> numeros = new ArrayList<String>();
        ArrayList<String> naipes = new ArrayList<String>();

        numeros.add("a"); numeros.add("2"); numeros.add("3"); numeros.add("4");
        numeros.add("5"); numeros.add("6"); numeros.add("7"); numeros.add("8");
        numeros.add("9"); numeros.add("t"); numeros.add("j"); numeros.add("q"); numeros.add("k");
        naipes.add("h"); naipes.add("d"); naipes.add("s"); naipes.add("c");
        Carta carta;
        for(int k = 0; k < 4; k++) {
	        for (int i = 0; i < naipes.size(); i++) {
	            for (int j = 0; j < numeros.size(); j++) {
	                carta = new Carta(numeros.get(j), naipes.get(i));	//cartas são criadas no formato xy, onde x é o número da carta e y é o naipe da mesma
	                completo.add(carta);
	            }
	        }
        }
    }

    void embaralhar() {
        Collections.shuffle(completo);
    }

    /*define quantas cartas serão embaralhadas
     * 
     */
    void embaralhar(int emb) {
        for (int i = 0; i < emb; i++) {
            Random rand = new Random();
            Carta carta;
            int num = rand.nextInt(50);
            carta = completo.remove(num);
            completo.add(carta);
        }
    }
    
    Carta getPrimeiraCarta() {
        return completo.remove(0);
    }
    
    Carta getCarta(String s) {
    	String num = ""+s.charAt(0);
		String naipe = ""+s.charAt(1);
		for(int i=0; i<completo.size(); i++) {
			String numB = completo.get(i).getNumero();
			String naipeB = completo.get(i).getNaipe();
    		if(num.equals(numB) && naipe.equals(naipeB)) {
    			return completo.remove(i);
    		}
    	}
    	return null;
    }
    
    
    //método para devolver carta ao final do baralho 
    void devolveCarta(Carta c) {
    	completo.add(c);
    }
}