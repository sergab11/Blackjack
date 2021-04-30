package model;
import java.util.ArrayList;

public class Jogador {
	protected String nome;
	protected ArrayList<Carta> mao = new ArrayList<Carta>();
	protected Fichas conj;
	protected Fichas aposta;
	
	public Jogador(String dnome) {
		nome = dnome;
		conj = new Fichas();
	}
	
	public void dobra(Carta tira) {
		int tam = aposta.numFichas();
		for(int i=0; i<tam; i++) {
			int valor = aposta.getValorFicha(i);
			aposta.adicionaFicha(valor);
		}
		mao.add(tira);
		boolean q = this.verificaMao();
	}
	
	public void hit(Carta tira) {
		this.adicionaCartaMao(tira);
		boolean q = this.verificaMao();
	}
	
	public int getSaldo() {
		int saldo = conj.getSomatorio();
		return saldo;
	}
	
	public Fichas getAposta() {
		return this.aposta;
	}
	
	public void ganhouRodada(Fichas rodada) {
		this.conj.adicionaConjunto(rodada);
	}
	
	protected void adicionaCartaMao(Carta recebe) {
		mao.add(recebe);
	}
	
	public void adicionaFichaAposta(int n) {
		this.conj.removeFicha(n);
		this.aposta.adicionaFicha(n);
	}
	
	public void retornaFicha(int n) {
		this.conj.adicionaFicha(n);
		this.aposta.removeFicha(n);
	}
	
	public boolean verificaMao() {
		int soma = somaMao();
		if(soma > 21)
			return false;
		else
			return true;
	}
	
	protected int somaMao() {
		int tam = mao.size();
		int soma1 = 0, soma2 = 0;
		for(int i=0; i<tam; i++) {
			if (mao.get(i).getNumero() == "A") {
				soma1 += 11;
				soma2 += 1;
			}
			if(mao.get(i).getNumero() == "J" || mao.get(i).getNumero() == "Q" || mao.get(i).getNumero() == "K") {
				soma1 += 10;
				soma2 += 10;
			}
			else { 
				soma1 += Integer.valueOf(mao.get(i).getNumero());
				soma2 += Integer.valueOf(mao.get(i).getNumero());
			}
		}
		if(soma1 > 21) 
			return soma2;
		else
			return soma1;
	}
	
	protected void limpaAposta() {
		aposta.limpa();
	}
}