package Model;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class Jogador {
	protected String nome;
	protected ArrayList<carta> mao = new ArrayList<carta>();
	protected fichas conj;
	protected fichas aposta;
	
	Jogador(String dnome) {
		nome = dnome;
		conj = new fichas();
	}
	
	public void dobra(carta tira) {
		int tam = aposta.numFichas();
		for(int i=0; i<tam; i++) {
			int valor = aposta.getValorFicha(i);
			aposta.adicionaFicha(valor);
		}
		mao.add(tira);
		boolean q = this.verificaMao();
	}
	
	public void hit(carta tira) {
		this.adicionaCartaMao(tira);
		boolean q = this.verificaMao();
	}
	
	public int getSaldo() {
		int saldo = conj.getSomatorio();
		return saldo;
	}
	
	public fichas getAposta() {
		return this.aposta;
	}
	
	public void ganhouRodada(fichas rodada) {
		this.conj.adicionaConjunto(rodada);
	}
	
	protected void adicionaCartaMao(carta recebe) {
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
		
		ArrayList<Integer> somas = new ArrayList<Integer>();
		somas.add(0);
		
		for(int i = 0; i < mao.size(); i++) {
			if (mao.get(i).getNumero() == "A") {
				int aux_tamanho = somas.size();
				for(int j = 0; j < aux_tamanho; j++) {
					somas[j] += 1;
					somas.add(somas[j] + 11);
				}
			}
			else if (mao.get(i).getNumero() == "J" || mao.get(i).getNumero() == "Q" || mao.get(i).getNumero() == "K") {
				for(int j = 0; j < somas.size(); j++) {
					somas[j] += 10;
				}
			}
			else {
				for(int j = 0; j < somas.size(); j++) {
					somas[j] += Integer.valueOf(mao.get(i).getNumero());
				}
			}
		}
		
		Arrays.sort(somas, Collections.reverseOrder());
		
		for (int i = 0; i < somas.size(); i++) {
			if (somas[i] <= 21) {
				return somas[i];
			}
		}
		
		return somas[0];
		
		/*	Só funciona para um AS		
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
		*/
	}
	
	protected void limpaAposta() {
		aposta.limpa();
	}
}
