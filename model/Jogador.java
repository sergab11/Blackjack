package model;
import java.util.ArrayList;
import java.util.Collections;

class Jogador {
	protected String nome;
	protected ArrayList<Carta> mao = new ArrayList<Carta>();
	protected Fichas conj;
	protected Fichas aposta;
	
	Jogador(String dnome) {
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
		int aux = 0;
		
		ArrayList<Integer> somas = new ArrayList<Integer>();
		somas.add(0);
		
		for(int i = 0; i < mao.size(); i++) {
			if (mao.get(i).getNumero() == "A") {
				int aux_tamanho = somas.size();
				for(int j = 0; j < aux_tamanho; j++) {
					aux = somas.get(j)+1;
					somas.add(aux + 11);
				}
			}
			else if (mao.get(i).getNumero() == "J" || mao.get(i).getNumero() == "Q" || mao.get(i).getNumero() == "K") {
				for(int j = 0; j < somas.size(); j++) {
					aux = somas.get(j)+10;
					somas.set(j, aux);
				}
			}
			else {
				for(int j = 0; j < somas.size(); j++) {
					aux = somas.get(j) + Integer.valueOf(mao.get(i).getNumero());
					somas.set(j, aux);
				}
			}
		}
		
		Collections.sort(somas, Collections.reverseOrder());
		
		for (int i = 0; i < somas.size(); i++) {
			if (somas.get(i) <= 21) {
				return somas.get(i);
			}
		}
		
		return somas.get(0);
	}
	
	protected void limpaAposta() {
		aposta.limpa();
	}
}
