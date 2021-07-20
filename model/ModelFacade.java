//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package model;

import java.io.*;
import java.util.*;

public class ModelFacade {
	private ArrayList<Jogador> jogadores = new ArrayList<Jogador>();
	private Baralho b = new Baralho();
	private Dealer dealer = new Dealer();
	
	public void criaJogadores(ArrayList<String> n) {
		for (int i = 0; i < n.size(); i++) {
			Jogador aux = new Jogador(n.get(i));
			jogadores.add(aux);
		}
		b.embaralhar();
	}
	
	public int numJog() {
		return jogadores.size();
	}
	
	public void embaralhar() {
		b.embaralhar();
	}
	
	//método que recebe um arquivo de jogo salvo e carrega os parâmetros necessários para garantir a persistência do jogo
	//os parâmetros são: o jogador da vez, nomes dos jogadores, suas respectivas mãos, apostas e saldos
	public int CarregaJogo(File file) throws IOException {
		int vez = 0;	//carrega o jogador da vez
		int num = 0;	//representa o número de jogadores
		int mao = 0;	//número de mãos do jogador
		boolean q = true;
		Scanner s = null;
		String conteudo = new String();
		Carta c;
		
		try {
			s = new Scanner(new BufferedReader(new FileReader(file)));
			vez = Integer.valueOf(s.next());
			while(s.hasNext()) {
				conteudo = s.next();
				Jogador aux = new Jogador(conteudo);
				jogadores.add(aux);
				
				while(q) {
					conteudo = s.next();
					if(conteudo.equals("o")) {
						mao++;
						conteudo = s.next();
						c = b.getCarta(conteudo);
						jogadores.get(num).inicializaMao();
						jogadores.get(num).operacao(c, "Hit", mao);
					}
					else if(conteudo.equals("fmaos")) {
						break;
					}
					else {
						c = b.getCarta(conteudo);
						jogadores.get(num).operacao(c, "Hit", mao);
					}
				}
				int saldo = Integer.valueOf(s.next());
				jogadores.get(num).setSaldo(saldo);
					
				if(mao != 0) {
					for(int i=0; i<mao+1; i++)
						jogadores.get(num).setAposta(i, Integer.valueOf(s.next()));
				}
				else
					jogadores.get(num).setAposta(0, Integer.valueOf(s.next()));	
				mao = 0;
				num++;
				s.next();
			}
    	}
		finally {
        if(s != null)
        	s.close();
		}
		
		b.embaralhar();
		return vez;
	}
	
	//método que "pega" o contexto do jogo atual e salva no arquivo passado como parâmetro
	public void salvaContexto(File file, int vez) throws IOException{
		BufferedWriter s = new BufferedWriter(new FileWriter(file));
		int i, j, k;
		
		try {
			s.write(Integer.toString(vez));
			s.newLine();
			for(i=0; i<numJog(); i++) {
				s.write(jogadores.get(i).getNome());
				s.newLine();
				ArrayList<String> inter = new ArrayList<String>();
				if(jogadores.get(i).numMaos() == 1) {
					inter = jogadores.get(i).getMaoString(0);
					for(j=0; j<jogadores.get(i).tamMao(0); j++) { 
						s.write(inter.get(j));
						s.newLine();
					}
					s.write("fmaos");
					s.newLine();
						
					s.write(Integer.toString(jogadores.get(i).getSaldo()));
					s.newLine();
						
					s.write(Integer.toString(jogadores.get(i).getAposta(0)));
					s.newLine();
				}
				else {
					for(k=0; k<jogadores.get(i).numMaos(); k++) {
						inter = jogadores.get(i).getMaoString(k);
						for(j=0; j<jogadores.get(i).tamMao(0); j++) { 
							s.write(inter.get(j));
							s.newLine();
						}
						if((k+1) == jogadores.get(i).numMaos()) {
							s.write("fmaos");
							s.newLine();
						}
						else {
						s.write("o");
						s.newLine();
						}
					}
						
					s.write(Integer.toString(jogadores.get(i).getSaldo()));
					s.newLine();
						
					for(k=0; k<jogadores.get(i).numMaos(); k++) {
						s.write(Integer.toString(jogadores.get(i).getAposta(k)));
						s.newLine();
					}
				}
					
				s.write("---");
				s.newLine();
			}
			
		} finally {
			s.close();
		}
		
	}
	
	//método que começa a partida, onde o Dealer distribui duas cartas para cada jogador e verifica possíveis Blackjacks
	public int comeca(Dealer d) throws Exception {
		d.distribuiCartas(jogadores, b);
		d.setMao(b);
		
		int q = verificaBlackjackJ();
		boolean i = verificaBlackjackD();
		if(i == true) {
			dealer.setEscondeMao(false);
			if(q != 0 && q != 100) {
				for(int j=0; j<jogadores.size(); j++) {
					if(j == (q-1)) {	//empate dealer e jogador j, pois ambos fizeram BJ
						jogadores.get(j).empate(0);
					}
				}
			}
		}
		if(q != 0 && q != 100){
			dealer.setEscondeMao(false);
			for(int j=0; j<jogadores.size(); j++) {
				if(j == (q-1)) {	//jogador j fez Blackjack
					jogadores.get(j).vitoriaBlackjack();
				}
			}
		}
		
		if(i == true && q == 100) //dealer venceu
			return 25;
		
		if(q != 0 && q != 100 && i == true) { //empate do jogador q+1 e o dealer, pois foram 2 BJs
			return 10+q-1;
		}
		if(q != 0 && q != 100 && i == false) { //jogador q fez Blackjack
			return (q-1);
		}
		return 20; //ninguém venceu, partida continua
	}
	
	public void surrender(int n, int m) {
		try {
			jogadores.get(n).surrender(m);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void split(int j, int numMao) {
		Carta c1 = b.getPrimeiraCarta();
		Carta c2 = b.getPrimeiraCarta();
		jogadores.get(j).split(numMao, c1, c2);
	}

	public void removeJog(int jog) {
		jogadores.remove(jog);
	}
	
	public boolean getPrimeiraCarta(int n, String op, int numMao) throws Exception {
		 boolean q = jogadores.get(n).operacao(b.getPrimeiraCarta(), op, numMao);
		 return q;
	}

	int verificaBlackjackJ() {
		int k=100;
		for(int i=0; i<jogadores.size(); i++) {
			if(jogadores.get(i).somaMao(0) == 21) {
				k = i;
				break;
			}
		}
		if(k != 100)
			return k+1;
		else
			return k;
	}
	
	boolean verificaBlackjackD() {
		if(dealer.somaMao() == 21)
			return true;
		else 
			return false;
	}
	
	//jogadores já jogaram, Dealer/Banca começa sua vez e, depois da jogada do Dealer, o resultado é determinado
	public ArrayList<Integer> VezDealer() {	
		int i, j;
		int maior = 0;	//maior pontuação da rodada fica nessa variável
		int cartasD = 0;	//número de cartas distribuídas na rodada fica nessa variável
		boolean q = true;
		ArrayList<Integer> arrayResultado = new ArrayList<Integer>();	//esse array guarda na posição 0 o número de cartas que o dealer pediu e nas outras posições o resultado da partida
		
		for(i=0; i<jogadores.size(); i++) {
			for(j=0; j<jogadores.get(i).numMaos(); j++) {
				if(jogadores.get(i).somaMao(j) > maior && jogadores.get(i).somaMao(j) <= 21)
					maior = jogadores.get(i).somaMao(j);
			}
		}
		
		if(maior == 0 || dealer.somaMao() > maior) {	//todos os jogadores quebraram e o Dealer não
			arrayResultado.add(cartasD);
			arrayResultado.add(25);
			dealer.setEscondeMao(false);	//mão do Dealer é revelada
			return arrayResultado;
		}
		else {	//dealer pede cartas até atingir o maior número possível a partir de 16
			dealer.setEscondeMao(false);	//mão do Dealer é revelada
			while(dealer.somaMao() <= 17 && dealer.somaMao() <= 21) {
				Carta c = b.getPrimeiraCarta();
				cartasD ++;
				q = dealer.pedeCarta(c);
			}
		}
		arrayResultado.add(cartasD);
		
		if(q == false) {	//dealer quebrou
			for(i=0; i<jogadores.size(); i++) {
				for(j=0; j<jogadores.get(i).numMaos(); j++) {
					if(jogadores.get(i).somaMao(j) != 0 && jogadores.get(i).somaMao(j) <= 21) {	
						jogadores.get(i).vitoriaOrdinaria(j);
						arrayResultado.add(i);
					}
				}
			}
		}
		else if(q) {
			for(i=0; i<jogadores.size(); i++) {
				for(j=0; j<jogadores.get(i).numMaos(); j++) {
					if(jogadores.get(i).somaMao(j) == dealer.somaMao()) {
						arrayResultado.add(10+i);
						jogadores.get(i).empate(j);
					}
					else if(jogadores.get(i).somaMao(j) > dealer.somaMao() && jogadores.get(i).somaMao(j) <= 21) {
						jogadores.get(i).vitoriaOrdinaria(j); 
						arrayResultado.add(i);
					}
				}
			}
		}
		if(arrayResultado.size() == 1) {
			arrayResultado.add(25);	//dealer venceu todos os jogadores
		}
		
		for(int k=1; k<arrayResultado.size(); k++) {
			int u = arrayResultado.lastIndexOf(arrayResultado.get(k));
			if(u != k)
				arrayResultado.remove(u);
			u = arrayResultado.lastIndexOf(arrayResultado.get(k));
			if(u != k)
				arrayResultado.remove(u);
		}
	
		return arrayResultado;
	}
	
	//método que recolhe as cartas dos jogadores e do dealer, devolve-as ao baralho e zera as apostas
	public void recolheMaos() {
		ArrayList<Carta> recolhe = new ArrayList<Carta>();
		Carta rec;
		for(int i=0; i<jogadores.size(); i++) {
			for(int j=0; j<jogadores.get(i).numMaos(); j++) {
				recolhe = jogadores.get(i).getMaoCartas(j);
				for(int k=0; k<recolhe.size(); k++) {
					rec = recolhe.get(k);
					b.devolveCarta(rec);
				}
			}
			jogadores.get(i).finalizaRodada();
			recolhe.clear();
		}
		recolhe = dealer.getMaoCartas();
		for(int k=0; k<recolhe.size(); k++) {
			rec = recolhe.get(k);
			b.devolveCarta(rec);
		}
		recolhe.clear();
		dealer.setEscondeMao(true);	//mão do Dealer volta a ficar escondida
		dealer.finalizaRodada();
	}
	
	//adiciona a ficha de valor "n" ao jogador "v" em sua mão "m"
	public void incrementaAposta(int n, int v, int m) {
		jogadores.get(v).incrementaAposta(n, m);
	}
	
	public void devolveFicha(int ficha, int jogador) {
		jogadores.get(jogador).decrementaAposta(ficha);
	}
	
	public ArrayList<Integer> verificaSaldoZero(){
		ArrayList<Integer> jog = new ArrayList<Integer>();
		for(int i=0; i<jogadores.size(); i++) {
			if(jogadores.get(i).getSaldo() == 0)
				jog.add(i);
		}
		return jog;
	}
	
	public Jogador getJogador(int n) {
		return jogadores.get(n);
	}
	
	public Dealer getDealer() {
		return dealer;
	}
	
	public String getNome(int n) {
		return jogadores.get(n).getNome();
	}
	
	public int getUltimaFichaAposta(int j) {
		return jogadores.get(j).getUltimaFicha();
	}
	
	public int numJogadas(int j) {
		return jogadores.get(j).numMaos();
	}
	
	public void iniciaGraphics() {
		for(int i=0; i<jogadores.size(); i++)
			jogadores.get(i).iniciaGraphics(0);
	}
	
	public void iniciaGraphics(int j, int n) {
		jogadores.get(j).iniciaGraphics(n);
	}
}