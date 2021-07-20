//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package controller;

import view.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import model.ModelFacade;

public class Partida implements SubjectPartida{
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension screenSize = tk.getScreenSize();
	int larg = screenSize.width; int alt = screenSize.height;

	ArrayList<JanelaJogador> janelasJog = new ArrayList<JanelaJogador>();
	JanelaBanca banca;
	private ArrayList<ObserverPartida> observers;
	
	ModelFacade facade = new ModelFacade();	//todas as regras do jogo se encontram nessa variável façade; ela será fundamental em todas as operações com os jogadores
	
	private static int jogadorVez = 0;	//indica o jogador da vez
	private static int numj;	//indica o número de jogadores criados para a partida
	private static int cartasDistribuidas = 0;
	private int jogadas = 1;	//guarda o número de jogadas do jogador da vez; quando o jogador faz um split, ele ganha direito a mais uma jogada
	
	String msgCaixa = "";	//variável que guarda texto a ser printado na JOptionPane ao final de cada rodada
	String result = "";	//variável que gaurda texto a ser printado no lado direito da Janela da Banca ao final de cada rodada
	String vez = "";	//variável que guarda texto a ser printado para indicar quem está jogando nesse momento
	int ficha = 0;	
	int aposta = 0;	//variável que serve unicamente para guardar o valor de uma aposta inicial e ativar ou não o botão "Deal"
	boolean botaoS = true;	//variável que habilita ou não o botão de Salvar da Janela da Banca
	
	//opção de iniciar Novo Jogo foi selecionada 
	public Partida(ArrayList<String> n) throws Exception {
		numj = n.size();
		
		observers = new ArrayList<ObserverPartida>();
		
		banca = new JanelaBanca(facade.getDealer(), this, this);
		facade.criaJogadores(n);
		
		inicializaGraficos();
	}
	
	//opção de "carregar um jogo salvo" foi selecionada
	public Partida(File file) throws IOException {
		jogadorVez = facade.CarregaJogo(file);
		numj = facade.numJog();
		
		observers = new ArrayList<ObserverPartida>();
		
		banca = new JanelaBanca(facade.getDealer(), this, this);
		
		inicializaGraficos();
		verificaSplits();
	}
	
	//opção de salvar o jogo corrente foi selecionada
	public void salvaContexto(File file) {
		try {
			facade.salvaContexto(file, jogadorVez);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//método que adiciona janelas, que "observam" os jogadores da partida, e cria a janela da banca, que "observa" o Dealer  
	public void inicializaGraficos() {
		for(int i=0; i<numj; i++) {
			JanelaJogador jj = new JanelaJogador(facade.getJogador(i), this, facade.getNome(i), i, 0);
			janelasJog.add(jj);
		}

		vez = facade.getNome(jogadorVez);
		notifyObservers();
		facade.iniciaGraphics();
		
		for(int k=0; k<numj; k++)
			janelasJog.get(k).setLocation(330*k, alt-400);
		
		banca.setVisible(true);
		for(int j=0; j<janelasJog.size(); j++)
			janelasJog.get(j).setVisible(true);
	}
	
	//verifica se existem splits em jogadores de um jogo recém-carregado 
	public void verificaSplits() {
		for(int j=0; j<numj; j++) {
			int numJanelas = facade.numJogadas(j);
			if(numJanelas > 1) {
				for(int k=1; k<numJanelas; k++) {
				JanelaJogador jj = new JanelaJogador(facade.getJogador(j), this, facade.getNome(j), j, k);
				janelasJog.add(jj);
				facade.iniciaGraphics(j, k);
				janelasJog.get(janelasJog.size()-1).setLocation(330*j, alt-600);
				janelasJog.get(janelasJog.size()-1).setVisible(true);
				}
			}
		}
	}
	
	public void comeca() throws Exception {
		int k;
		jogadorVez = 0;
		vez = facade.getNome(jogadorVez);
		
		k = facade.comeca(facade.getDealer());
		
		cartasDistribuidas += 2*(numj+1);
		testaDistribuidas();
		
		if(k >= 10 && k < 14) {
			msgCaixa = "Empate do Dealer com o "+facade.getNome(k)+"!";
			result = "Empate!";
		}
		else if(k >= 0 && k < 4) {
			msgCaixa = facade.getNome(k)+" fez Blackjack!";
			result = facade.getNome(k)+" ganhou!";
		}
		else if(k == 25){
			result = "Dealer ganhou!";
			msgCaixa = "Dealer fez Blackjack!";
		}
		
		notifyObservers();
	}
	
	//método para testar se já foram distribuídas 10% das cartas do baralho; caso sim, as cartas são embaralhadas
	void testaDistribuidas() {
		if(cartasDistribuidas >= 21) {
			facade.embaralhar();
			cartasDistribuidas = 0;
		}
	}
	
	public void hit(int j, int numMao) throws Exception {
		boolean q = facade.getPrimeiraCarta(j, "Hit", numMao);
	
		cartasDistribuidas++;
		if(cartasDistribuidas >= 21) {
			facade.embaralhar();
			cartasDistribuidas = 0;
		}
		
		if(q == false)
			proximoJogo();
	}
	
	public void dobra(int j, int numMao) {
		try {
			facade.getPrimeiraCarta(j, "Double", numMao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		cartasDistribuidas++;
		if(cartasDistribuidas >= 21) {
			facade.embaralhar();
			cartasDistribuidas = 0;
		}
		
		proximoJogo();
	}
	public void stand(int numMao) throws Exception {
		proximoJogo();
	}
	
	public void surrender(int j, int numMao) {
		facade.surrender(j, numMao);
		proximoJogo();
	}
	
	public void split(int j, int numMao) {
		facade.split(j, numMao);
		jogadas = facade.numJogadas(jogadorVez); //retorna o número de mãos do jogador da vez e dá direito a mais uma jogada para o mesmo
		
		if(jogadas < 4) {
			if(numMao == 0 && jogadas == 3) {
				JanelaJogador jj = new JanelaJogador(facade.getJogador(j), this, facade.getNome(j), j, numMao+2);
				janelasJog.add(jj);
				facade.iniciaGraphics(j, numMao+2);
			}
			else {
				JanelaJogador jj = new JanelaJogador(facade.getJogador(j), this, facade.getNome(j), j, numMao+1);
				janelasJog.add(jj);
				facade.iniciaGraphics(j, numMao+1);
			}
			janelasJog.get(janelasJog.size()-1).setLocation(330*j, alt-600);
			janelasJog.get(janelasJog.size()-1).setVisible(true);
		}
	}
	
	//método para o caso do jogador da vez resolver deixar a mesa
	public void quit(int j) {
		numj--;
		
		janelasJog.get(j).dispose();
		facade.removeJog(j);
		
		if(facade.numJog() == 0) {
			new JanelaInicial();
			banca.dispose();
		}
		else {
			vez = facade.getNome(j);
			notifyObservers();
		}
	}
	
	//método para controlar de quem é a vez ainda na parte inicial da rodada onde todos os jogadores estão apostando e não possuem cartas ainda
	public void proximoApostas() {
		jogadorVez++;
		aposta = 0;
		if(jogadorVez == numj) {
			vez = "i";	//quando vez="i", o texto "Iniciar Rodada!" é printado no campo abaixo de vez
			notifyObservers();
		}
		else
			vez = facade.getNome(jogadorVez);
		notifyObservers();
	}
	
	//método para controlar de quem é a vez quando a rodada já começou e os jogadores estão realizando operações com suas respectivas "mãos"
	public void proximoJogo() {
		ArrayList<Integer> c;
		jogadas--;
		
		if(jogadas == 0) { 
			jogadorVez++;
			jogadas = 1;
		}
		
		//testa se chegou ao final da rodada, ou seja, se chegou ao momento em que os jogadores já fizeram suas apostas e operações; portanto, chega a vez do Dealer
		if(jogadorVez == numj) {
			c = new ArrayList<Integer>();
			c = facade.VezDealer();
			cartasDistribuidas += c.get(0);
			vez = "r";	//quando vez="r", o texto "Rodada acabou é printado!" no campo abaixo de Vez
			String empate = "";
			String venceu = "";
			
			if(c.size() > 2) {
				for(int i=1; i<c.size(); i++) {
					if(c.get(i) >= 10 && c.get(i) < 14) {
						if(i == c.size()-1)
							empate += facade.getNome(c.get(i)-10);
						else
							empate += facade.getNome(c.get(i)-10)+(", ");
					}
					else {
						if(i == c.size()-1)
							venceu += facade.getNome(c.get(i));
						else
							venceu += facade.getNome(c.get(i))+", ";
					}
				}
				if(venceu != "" && empate != "")
					msgCaixa = venceu+" ganhou(aram) a rodada e "+empate+" empatou(aram)!";
				else if(venceu == "" && empate != "")
					msgCaixa = empate+" empatou(aram)!";
				else
					msgCaixa = venceu+" ganhou(aram) a rodada!";
				
				if(venceu == "")
					result = "Empate!";
				else
					result = "Dealer perdeu!";
			}
			else {
				if(c.get(1) >= 10 && c.get(1) < 14) {
					msgCaixa = "Empate do Dealer com o "+facade.getNome(c.get(1)-10)+"!";
					result = "Empate!";
				}
				else if(c.get(1) >= 0 && c.get(1) < 4) {
					msgCaixa = facade.getNome(c.get(1))+" ganhou!";
					result = facade.getNome(c.get(1))+" ganhou!";
				}
				else {
					result = "Dealer ganhou!";
					msgCaixa = "Dealer ganhou!";
				}
			}
			notifyObservers();
		}
		else
			vez = facade.getNome(jogadorVez);
		botaoS = true;
		notifyObservers();
	}
	
	public void encerra() {
		ArrayList<Integer> jogSaldoZero = facade.verificaSaldoZero();
		
		if(janelasJog.size()>numj) {
			for(int k=numj; k<janelasJog.size(); k++) 
				janelasJog.get(k).dispose();
		}
		
		jogadorVez = 0;
		facade.recolheMaos();
		
		if(jogSaldoZero != null) {	//remove do jogo todos os jogadores com saldo 0
			for(int i=0; i<jogSaldoZero.size(); i++) {
				janelasJog.get(jogSaldoZero.get(i)).dispose();
				facade.removeJog(jogSaldoZero.get(i));	
			}
		}
		
		numj = facade.numJog();
		msgCaixa = "";
		result = "";
		vez = "A";	//quando vez="A", o texto "Iniciar Apostas!" é printado
		testaDistribuidas();
		botaoS = true;
		notifyObservers();
	}
	
	//método para incrementar aposta do jogador da vez na mão "m" do mesmo (caso seja somente uma mão para o jogador, m será igual a 0)
	public void incrementaAposta(int n, int m) {
		facade.incrementaAposta(n, jogadorVez, 0);
	}
	
	public void decrementaAposta(int n) {
		facade.devolveFicha(n, jogadorVez);
	}
	
	@Override
	public void addObserver(ObserverPartida obs) {
		observers.add(obs); 
	}
	
	@Override
	public void notifyObservers() {
		for(ObserverPartida o: observers)
			o.update(vez, result, msgCaixa, ficha, aposta, botaoS);
	}
	
	@Override
	//método que trata o evento de clique na Janela da Banca para adicionar fichas à aposta do jogador da vez
	public void eventoIncrementaAposta(int n) {	
		incrementaAposta(n, 0);
		ficha = n;
		aposta += n;
		notifyObservers();
	}
	
	@Override
	public void eventoDecrementaAposta(int n) {
		decrementaAposta(n);
		ficha = facade.getUltimaFichaAposta(jogadorVez);
		aposta -= 20;
		notifyObservers();
	}
	
	@Override
	//método para tratar os eventos dos botões da Janela do Jogador
	public void operacao(String s, int jog, int numMao) {
		botaoS = false;
		notifyObservers();
		if(s.equals("Hit")) {
			try {
				this.hit(jog, numMao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(s.equals("Double")) {
			try {
				this.dobra(jog, numMao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(s.equals("Surrender")) {
			try {
				this.surrender(jog, numMao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(s.equals("Quit")) {
			this.quit(jog);
		}
		
		if(s.equals("Deal")) {
			this.proximoApostas();
		}
		
		if(s.equals("Stand")) {
			try {
				this.stand(numMao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(s.equals("Split")) {
			this.split(jog, numMao);
		}
	}
}