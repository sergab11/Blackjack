//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package model;

class Carta {
	private String naipe;
	private String numero;

	Carta(String num, String np) {
		numero = num;
	    naipe = np;
	}

	String getNaipe() {
	    return naipe;
	}

	String getNumero() {
	    return numero;
	}
	
	String getNumeroNaipe() {
		return numero.concat(naipe);
	}

	protected void setNaipe(String np) {
		naipe = np;
	}

	protected void setNumero(String num) {
	    numero = num;
	}
}