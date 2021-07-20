//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class JogadorTest {

	@Test
	public void testeCriacaoJogador() {
		Jogador j = new Jogador("ABC");
		
		assertEquals(500, j.getSaldo());
	}
	
	@Test
	public void testeSomaMao() {
		Jogador j = new Jogador("ABC");
		Carta c1 = new Carta("a", "d");
		Carta c2 = new Carta("j", "d");
		j.adicionaCarta(c1, 0); 
		j.adicionaCarta(c2, 0);
		int soma = j.somaMao(0);
		assertEquals(21, soma);
	}
	
	@Test
	public void testeSomaMao2() {
		Jogador j = new Jogador("ABC");
		Carta c1 = new Carta("a", "d"); // primeiro as com valor 11
		Carta c2 = new Carta("a", "h"); // segundo com valor 1
		Carta c3 = new Carta("a", "s"); // terceiro com valor 1
		j.adicionaCarta(c1, 0); 
		j.adicionaCarta(c2, 0);
		j.adicionaCarta(c3, 0);
		int soma = j.somaMao(0);
		assertEquals(13, soma);
	}
	
	@Test
	public void testeSomaMao3() {
		Jogador j = new Jogador("ABC");
		Carta c1 = new Carta("k", "d"); // 10
		Carta c2 = new Carta("j", "h"); // 10
		Carta c3 = new Carta("a", "s"); // 1
		j.adicionaCarta(c1, 0); 
		j.adicionaCarta(c2, 0);
		j.adicionaCarta(c3, 0);
		int soma = j.somaMao(0);
		assertEquals(21, soma);
	}
	
	@Test
	public void testeSomaMao4() {
		Jogador j = new Jogador("ABC");
		Carta c1 = new Carta("k", "d"); // 10
		Carta c2 = new Carta("3", "h"); // 3
		Carta c3 = new Carta("9", "s"); // 9
		j.adicionaCarta(c1, 0); 
		j.adicionaCarta(c2, 0);
		j.adicionaCarta(c3, 0);
		int soma = j.somaMao(0);
		assertEquals(22, soma);
	}
	
	@Test
	public void testeDobra() throws Exception {
		Jogador j1 = new Jogador("ABC");
		j1.incrementaAposta(20, 0);
		
		Carta c1 = new Carta("6", "d");
		Carta c2 = new Carta("7", "s");
		j1.adicionaCarta(c1, 0);
		j1.adicionaCarta(c2, 0);
		
		Carta c3 = new Carta ("3", "c");
		
		j1.dobra(c3, 0);
		assertEquals(40, j1.getAposta(0));
		assertEquals(16, j1.somaMao(0));
	}
	
	@Test
	public void testeSurrender() throws Exception {
		Jogador j1 = new Jogador("ABC");
		j1.incrementaAposta(5, 0);
		j1.incrementaAposta(5, 0);
		j1.incrementaAposta(20, 0);
		j1.incrementaAposta(20, 0);
		
		/*
		 * O jogador j1 desistiu da mesa quando recebeu suas duas primeiras cartas; portanto, deve ser restituído em 50% da sua aposta. Seu saldo deverá ser de 475.
		 */
		j1.surrender(0);
		assertEquals(475, j1.getSaldo());
	}
	
	@Test
	public void testeHit() throws Exception{
		Jogador j1 = new Jogador("ABC");
		Carta c1 = new Carta("6", "d");
		Carta c2 = new Carta("7", "s");
		Carta c3 = new Carta("4", "c");
		j1.adicionaCarta(c1, 0);
		j1.adicionaCarta(c2, 0);
		
		j1.hit(c3, 0);
		assertEquals(17, j1.somaMao(0));
	}
	
	@Test
	public void testeSplit1() {
		Jogador j1 = new Jogador("ABC");
		Carta c1 = new Carta("2", "d");
		Carta c2 = new Carta("2", "s");
		
		j1.adicionaCarta(c1, 0);
		j1.adicionaCarta(c2, 0);
		
		Carta c3 = new Carta("4", "d");
		Carta c4 = new Carta("5", "s");
		
		j1.split(0, c3, c4);
		
		assertEquals(6, j1.somaMao(0));
		assertEquals(7, j1.somaMao(1));
	}
	
	@Test
	public void testeSplit2() {
		Jogador j1 = new Jogador("ABC");
		Carta c1 = new Carta("2", "d");
		Carta c2 = new Carta("2", "s");
		
		j1.adicionaCarta(c1, 0);
		j1.adicionaCarta(c2, 0);
		
		Carta c3 = new Carta("8", "d");
		Carta c4 = new Carta("9", "s");
		
		j1.split(0, c3, c4);
		
		assertEquals(10, j1.somaMao(0));
		assertEquals(11, j1.somaMao(1));
	}

}