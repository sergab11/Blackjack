package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaralhoTest {

	@Test
	public void testeGetCarta() {
		Baralho b = new Baralho();
		
		Carta c1 = b.getPrimeiraCarta();
		String s = c1.getNumeroNaipe();
		b.devolveCarta(c1);
		Carta c2 = b.getCarta(s);
		
		assertEquals(c1.getNumeroNaipe(), c2.getNumeroNaipe());
	}
	
	@Test
	public void testeStringCarta() {
		Baralho b = new Baralho();
		String s = "as";
		Carta c = b.getCarta(s);
		
		assertEquals(s, c.getNumeroNaipe());
	}
}
