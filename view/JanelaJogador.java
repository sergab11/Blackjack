//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package view;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import model.*;
import controller.*;

public class JanelaJogador extends JFrame implements ObserverJogador{
	JPanel principal = new JPanel();
	JPanel ladoEsquerdo = new JPanel();
	JPanel pbaixo = new JPanel();
	
	JLabel textoAposta = new JLabel("APOSTA");
	JLabel textoSaldo = new JLabel("SALDO");
	JLabel textoValorAposta = new JLabel();
	JLabel textoValorSaldo = new JLabel();
	JLabel textoPontos = new JLabel("Pontos: ");
	JLabel textoSomaMao = new JLabel();
	JLabel textoQuebrou = new JLabel();
	
	BotoesJogador botoes;
	
	SubjectJogador jog;
	SubjectPartida partida;
	
	int nJog = 0;
	int nMao = 0;
	
	public JanelaJogador(SubjectJogador j, SubjectPartida p, String n, int numJog, int numMao){
		this.partida = p;
		this.jog = j;
		
		this.jog.addObserver(this);
		this.botoes = new BotoesJogador(this.partida, numJog, numMao);
		
		nJog = numJog;
		nMao = numMao;
		
		setTitle(n);
		configuraJanelaJogador();
		configuraTelaJogador();
		
	}
	
	void configuraJanelaJogador() {
		setSize(320, 300);
		setLocationRelativeTo(null);
	}
	
	void configuraTelaJogador() {
		add(principal);
		principal.setLayout(new BorderLayout());
		
		principal.add(BorderLayout.LINE_START, ladoEsquerdo);
		configuraEsquerdo();
		
		principal.add(BorderLayout.PAGE_END, pbaixo);
		configuraBaixo();
	}
	
	void configuraEsquerdo() {
		ladoEsquerdo.setLayout(new BoxLayout(ladoEsquerdo, BoxLayout.Y_AXIS));
		ladoEsquerdo.add(textoAposta);
		ladoEsquerdo.add(textoValorAposta);
		ladoEsquerdo.add(textoSaldo);
		ladoEsquerdo.add(textoValorSaldo);
		ladoEsquerdo.add(textoPontos);
		ladoEsquerdo.add(textoSomaMao);
		ladoEsquerdo.add(textoQuebrou);
		
		textoAposta.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoValorAposta.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoSaldo.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoValorSaldo.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoPontos.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoSomaMao.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoQuebrou.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		textoAposta.setFont(new Font("Arial", Font.BOLD, 20)); textoAposta.setHorizontalAlignment(SwingConstants.CENTER);
		textoValorAposta.setFont(new Font("Arial", Font.BOLD, 20)); textoValorAposta.setHorizontalAlignment(SwingConstants.CENTER);
		textoSaldo.setFont(new Font("Arial", Font.BOLD, 20)); textoSaldo.setHorizontalAlignment(SwingConstants.CENTER);
		textoValorSaldo.setFont(new Font("Arial", Font.BOLD, 20)); textoValorSaldo.setHorizontalAlignment(SwingConstants.CENTER);
		textoPontos.setFont(new Font("Arial", Font.BOLD, 20)); textoPontos.setHorizontalAlignment(SwingConstants.LEFT);
		textoSomaMao.setFont(new Font("Arial", Font.BOLD, 20)); textoSomaMao.setHorizontalAlignment(SwingConstants.LEFT);
		textoQuebrou.setFont(new Font("Arial", Font.BOLD, 18)); textoQuebrou.setHorizontalAlignment(SwingConstants.LEFT);
	}
	
	public void configuraBaixo() {
		pbaixo.setLayout(new GridLayout(2, 3));
		
		pbaixo.add(botoes.getBotao("Hit")); pbaixo.add(botoes.getBotao("Split")); pbaixo.add(botoes.getBotao("Double")); 
		pbaixo.add(botoes.getBotao("Stand")); pbaixo.add(botoes.getBotao("Surrender")); pbaixo.add(botoes.getBotao("Quit"));
		
		botoes.getBotao("Hit").setEnabled(false); botoes.getBotao("Split").setEnabled(false); botoes.getBotao("Double").setEnabled(false);
		botoes.getBotao("Stand").setEnabled(false); botoes.getBotao("Surrender").setEnabled(false);
	}
	
	void atualizaValorSaldo(int saldo) {
		String s = Integer.toString(saldo);
		textoValorSaldo.setText(s);
	}
	
	void atualizaValorAposta(int aposta) {
		String a = Integer.toString(aposta);
		textoValorAposta.setText(a);
		
		if(aposta == 0) {
			botoes.getBotao("Hit").setEnabled(false); botoes.getBotao("Split").setEnabled(false); botoes.getBotao("Double").setEnabled(false);
			botoes.getBotao("Stand").setEnabled(false); botoes.getBotao("Surrender").setEnabled(false);
		}
	}
	
	void atualizaMaoApostaJogador(int numMao, ArrayList<String> m, int pontos) {
		String s = Integer.toString(pontos);
		if(pontos > 21 && m.size()>2)
			textoQuebrou.setText("Quebrou!");
		else
			textoQuebrou.setText("");
		
		if(m.isEmpty() == false) {
			botoes.getBotao("Hit").setEnabled(true); botoes.getBotao("Double").setEnabled(true);
			botoes.getBotao("Stand").setEnabled(true); botoes.getBotao("Surrender").setEnabled(true);
			botoes.getBotao("Split").setEnabled(false);
			if(m.size() == 2) {
				char prim = m.get(0).charAt(0);
				char seg = m.get(1).charAt(0);
				if(prim == seg || 
						(prim == 't' && seg == 'j') || (prim == 't' && seg == 'q') || (prim == 't' && seg == 'k') || 
						(prim == 'j' && seg == 't') || (prim == 'j' && seg == 'q') || (prim == 'j' && seg == 'k') ||
						(prim == 'q' && seg == 't') || (prim == 'q' && seg == 'j') || (prim == 'q' && seg == 'k') ||
						(prim == 'k' && seg == 't') || (prim == 'k' && seg == 'j') || (prim == 'k' && seg == 'q'))
					botoes.getBotao("Split").setEnabled(true);
			}
		}
		
		if(numMao == 2)
			botoes.getBotao("Split").setEnabled(false);
		
		ImCartas ca = new ImCartas(m);
		principal.add(ca);
			
		textoSomaMao.setText(s);
	}
	
	@Override
	public void update(int numMao, ArrayList<String> mao, int somaPontosMao, int valorAposta, int valorSaldo) {
		atualizaValorSaldo(valorSaldo);
		if(numMao == nMao) {	//atualiza apenas a janela do jogador que lhe foi atribuída no momento de sua criação
			atualizaValorAposta(valorAposta);	//atualiza o valor da aposta do jogador na rodada corrente
			atualizaMaoApostaJogador(numMao, mao, somaPontosMao);	//atualiza a pontuação da(s) mão(s) do jogador, desenha as cartas e ativa/desativa os botões relativos às operações
		}
	}
	
	class ImCartas extends JPanel{
		ArrayList<String> cartas = new ArrayList<String>();
		int ficha = 0;
		public ImCartas(ArrayList<String> c) {
			cartas = c;;
		}
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			String path = "src\\images\\";
			Image img = null;
			
			if (cartas.isEmpty() == false) {
				for(int i=0; i<cartas.size(); i++) {
					String c = cartas.get(i);
					try {
						img = ImageIO.read(new File(path+c+".gif"));
					}
					catch(IOException e) {
						System.out.println(e.getMessage());
						System.exit(1);
					}
					g2d.drawImage(img, 20*i, 15, this);
				}
			}
			
			if(ficha != 0) {
				String c = "ficha"+" "+ficha+"$";
				try {
					img = ImageIO.read(new File(path+c+".png"));
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
					System.exit(1);
				}
				img = img.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
				g2d.drawImage(img, 20, 150, this);
			}
		}
	}
}