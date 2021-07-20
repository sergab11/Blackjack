//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package view;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import model.SubjectDealer;
import controller.*;

public class JanelaBanca extends JFrame implements ObserverDealer, MouseListener, ObserverPartida{
	SubjectDealer dealer;
	SubjectPartida p;
	Partida partida;
	
	Toolkit tk = Toolkit.getDefaultToolkit();
	Dimension screenSize = tk.getScreenSize();
	
	JLabel textoResultado = new JLabel("Resultado:");
	JLabel textoValorResultado = new JLabel();
	JLabel textoVez = new JLabel("Vez:");
	JLabel textoValorVez = new JLabel();
	JLabel textoPontos = new JLabel("Pontos: ");
	JLabel textoSomaMao = new JLabel();
	JLabel textoQuebrou = new JLabel();
	
	
	JPanel principal = new JPanel();
	JPanel ladoEsquerdo = new JPanel();
	JPanel ladoDireito = new JPanel();
	
	BotoesBanca botoes;

	public JanelaBanca(SubjectDealer d, SubjectPartida sp, Partida part){
		partida = part;
		this.botoes = new BotoesBanca(partida);
		
		configuraJanelaBanca();
		configuraTelaBanca();
		
		this.dealer = d;
		this.dealer.addObserver(this);
		
		this.p = sp;
		this.p.addObserver(this);
	}
	
	void configuraJanelaBanca() {
		setTitle("Banca");
		setSize(1200, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocation(screenSize.width-1600, 10);
		setLayout(new BorderLayout());
		addMouseListener(this);
		setContentPane(new ImBancaBKG(false, false, null));
		setVisible(true);
	}
	
	void configuraTelaBanca() {
		setLayout(new BorderLayout());
		add(BorderLayout.LINE_START, ladoEsquerdo);
		add(BorderLayout.LINE_END, ladoDireito);
		
		configuraEsquerdo();
		configuraDireito();
	}
	
	void configuraEsquerdo() {
		ladoEsquerdo.setLayout(new BoxLayout(ladoEsquerdo, BoxLayout.Y_AXIS));
		
		ladoEsquerdo.add(botoes.getBotao("inicia"));
		ladoEsquerdo.add(botoes.getBotao("encerra"));
		ladoEsquerdo.add(botoes.getBotao("salva"));
		ladoEsquerdo.add(textoPontos);
		ladoEsquerdo.add(textoSomaMao);
		ladoEsquerdo.add(textoQuebrou);
		
		botoes.getBotao("inicia").setAlignmentX(Component.CENTER_ALIGNMENT);
		botoes.getBotao("encerra").setAlignmentX(Component.CENTER_ALIGNMENT);
		botoes.getBotao("salva").setAlignmentX(Component.CENTER_ALIGNMENT);
		textoPontos.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoSomaMao.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoQuebrou.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		textoPontos.setFont(new Font("Arial", Font.BOLD, 20)); textoPontos.setHorizontalAlignment(SwingConstants.CENTER);
		textoSomaMao.setFont(new Font("Arial", Font.BOLD, 20)); textoSomaMao.setHorizontalAlignment(SwingConstants.CENTER);
		textoQuebrou.setFont(new Font("Arial", Font.BOLD, 20)); textoQuebrou.setHorizontalAlignment(SwingConstants.CENTER);
		
		textoPontos.setVisible(false); textoSomaMao.setVisible(false); textoQuebrou.setVisible(false);
	}
	
	void configuraDireito() {
		ladoDireito.setLayout(new BoxLayout(ladoDireito, BoxLayout.Y_AXIS));
		ladoDireito.add(textoVez);
		ladoDireito.add(textoValorVez);
		ladoDireito.add(textoResultado);
		ladoDireito.add(textoValorResultado);
		ladoDireito.add(botoes.getBotao("Deal"));
		
		textoVez.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoValorVez.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoResultado.setAlignmentX(Component.CENTER_ALIGNMENT);
		textoValorResultado.setAlignmentX(Component.CENTER_ALIGNMENT);
		botoes.getBotao("Deal").setAlignmentX(Component.CENTER_ALIGNMENT);
		
		textoResultado.setFont(new Font("Arial", Font.BOLD, 20)); textoResultado.setHorizontalAlignment(SwingConstants.CENTER);
		textoValorResultado.setFont(new Font("Arial", Font.BOLD, 20)); textoValorResultado.setHorizontalAlignment(SwingConstants.CENTER);
		textoVez.setFont(new Font("Arial", Font.BOLD, 20)); textoVez.setHorizontalAlignment(SwingConstants.CENTER);
		textoValorVez.setFont(new Font("Arial", Font.BOLD, 20)); textoValorVez.setHorizontalAlignment(SwingConstants.CENTER);
		botoes.getBotao("Deal").setEnabled(false);
	}
	
	void escreveCaixa(String msg) {
		JOptionPane.showMessageDialog(this, msg);
	}
	
	void atualizaVez(String v, int aposta) {
		if(v.equals("i"))
			textoValorVez.setText("Iniciar Rodada!");
		else if(v.equals("r"))
			textoValorVez.setText("Rodada acabou!");
		else if(v.equals("A"))
			textoValorVez.setText("Iniciar Apostas!");
		else
			textoValorVez.setText(v);
		
		if(aposta >= 20)
			botoes.getBotao("Deal").setEnabled(true);
		else
			botoes.getBotao("Deal").setEnabled(false);
			
	}
	
	void atualizaResultado(String v, String c) {
		if(v != "") {
			textoValorResultado.setText(v);
			escreveCaixa(c);
		}
		else 
			textoValorResultado.setText("");
	}
	
	void atualizaJanelaMaoBanca(ArrayList<String> m, int pontos, boolean esconde) {
		String s = Integer.toString(pontos);
		
		if(m.size() != 0) 
			setContentPane(new ImBancaBKG(true, esconde, m));	//desenha background com fichas e cartas do Dealer
		else
			setContentPane(new ImBancaBKG(false, esconde, null)); //desenha background com fichas sem cartas
		
		configuraTelaBanca();
		textoSomaMao.setText(s);
		
		if(esconde == true) {
			textoQuebrou.setText("");
			textoPontos.setVisible(false); textoSomaMao.setVisible(false); textoQuebrou.setVisible(false);
		}
		else {
			textoPontos.setVisible(true); textoSomaMao.setVisible(true); textoQuebrou.setVisible(true);
			if(pontos > 21)
				textoQuebrou.setText("QUEBROU!");
		}
	}
	
	void atualizaFicha(int n) {
		
	}
	
	void atualizaSalvar(boolean q) {
		botoes.getBotao("Salva").setEnabled(q);
	}
	
	@Override
	public void update(String vez, String resultado, String msgCaixa, int ficha, int aposta, boolean botaoS) {	//Observer da Partida
		atualizaVez(vez, aposta);	//atualiza e mostra quem é o jogador da vez, além de ativar ou não o botão "Deal" 
		atualizaResultado(resultado, msgCaixa);	//mostra o resultado final do jogo
		atualizaFicha(ficha);
		atualizaSalvar(botaoS);
	}
	
	@Override
	public void update(ArrayList<String> mao, int somaPontosMao, boolean esconde) {	//Observer do Dealer
		atualizaJanelaMaoBanca(mao, somaPontosMao, esconde);	//atualiza a mão do Dealer e desenha a mesma; ao final da rodada, sua mão e sua pontuação devem ser reveladas
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		//Tratamento de eventos "Incrementa Aposta"
		if(e.getX()>955 && e.getX()<1055 && e.getY()>620 && e.getY()<680)
			this.p.eventoIncrementaAposta(5);
		
		if(e.getX()>955 && e.getX()<1055 && e.getY()>520 && e.getY()<580)
			this.p.eventoIncrementaAposta(10);
		
		if(e.getX()>955 && e.getX()<1055 && e.getY()>420 && e.getY()<480)
			this.p.eventoIncrementaAposta(20);
		
		if(e.getX()>955 && e.getX()<1055 && e.getY()>320 && e.getY()<380)
			this.p.eventoIncrementaAposta(50);
		
		if(e.getX()>955 && e.getX()<1055 && e.getY()>220 && e.getY()<280)
			this.p.eventoIncrementaAposta(100);
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
	
	class ImBancaBKG extends JPanel{
		int vetor[] = {5, 10, 20, 50, 100};
		String path = "src\\images\\";
		ArrayList<String> cartas = new ArrayList<String>();
		boolean temCarta = false;
		boolean esconde = true;
		
		public ImBancaBKG(boolean q, boolean esc, ArrayList<String> m) {
			temCarta = q;
			esconde = esc;
			cartas = m;
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			Image img = null;
			Image imgRed = null;
			try {
				img = ImageIO.read(new File(path+"blackjackBKG.png"));
			}
			catch(IOException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			}
			imgRed = img.getScaledInstance(super.getWidth(), super.getHeight(), Image.SCALE_SMOOTH);
			g2d.drawImage(imgRed, 0, 0, this);
			
			for(int i=0; i<5; i++) {
				String c = "ficha"+" "+vetor[i]+"$";
				try {
					img = ImageIO.read(new File(path+c+".png"));
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
					System.exit(1);
				}
				g2d.drawImage(img, 975, 580-100*i, this);
			}
			
			//Dealer já distribuiu as cartas; mão do Dealer deve ser escondida até o final da rodada
			if(temCarta == true) {
				int num = cartas.size();
				img = null;
				if(esconde == true) {
					String t = cartas.get(0);
					try {
						img = ImageIO.read(new File(path+t+".gif"));
					}
					catch(IOException e) {
						System.out.println(e.getMessage());
						System.exit(1);
					}
					g2d.drawImage(img, 550, 100, this);
						
					t = "deck1";
					try {
						img = ImageIO.read(new File(path+t+".gif"));
					}
					catch(IOException e) {
						System.out.println(e.getMessage());
						System.exit(1);
					}
					g2d.drawImage(img, 570, 100, this);
				}
				else {
					for(int i=0; i<num; i++) {
						String c = cartas.get(i);
						try {
							img = ImageIO.read(new File(path+c+".gif"));
						}
						catch(IOException e) {
							System.out.println(e.getMessage());
							System.exit(1);
						}
						g2d.drawImage(img, 550+20*i, 100, this);
					}
				}
			}
		}
	}
}