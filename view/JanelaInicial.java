//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package view;

import javax.swing.*;
import java.awt.*;
import controller.*;
import java.util.ArrayList;


public class JanelaInicial extends JFrame{
	
	private int MAX_JOGADORES = 4;
	
	JPanel Inicial = new JPanel();
	JPanel opcaoNovo = new JPanel();
	JPanel opcaoCont = new JPanel();
	
	public JanelaInicial(){
		configuraJanelaInicial();
		configuraTelaInicial();
		new CaixasBotaoIniciais(this);
	}
	
	void configuraJanelaInicial() {
		setTitle("Blackjack");
		setSize(350, 320);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	void configuraTelaInicial() {
		add(Inicial);
		Inicial.setLayout(new BoxLayout(Inicial, BoxLayout.Y_AXIS));

		
		Inicial.add(opcaoNovo);
		opcaoNovo.setAlignmentX(Component.CENTER_ALIGNMENT);
		configuraOpcaoNovo();
		
		Inicial.add(opcaoCont);
		opcaoCont.setAlignmentX(Component.CENTER_ALIGNMENT);
		configuraOpcaoCont();
		
	}
	
	void configuraOpcaoNovo() {
		opcaoNovo.setLayout(new BoxLayout(opcaoNovo, BoxLayout.Y_AXIS));
		
		JLabel textoNovo = new JLabel("Novo Jogo");
		textoNovo.setFont(new Font("Arial", Font.BOLD, 20)); textoNovo.setHorizontalAlignment(SwingConstants.CENTER);
		
		opcaoNovo.add(textoNovo);
		textoNovo.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		opcaoNovo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	}
	
	public void addCaixasBotao(ArrayList <JTextField> listaCampos, JButton con) {
		JPanel CaixasB = new JPanel();
		CaixasB.setLayout(new BoxLayout(CaixasB, BoxLayout.Y_AXIS));
		
		for (int i = 0; i < MAX_JOGADORES; i++) {
			listaCampos.get(i).setAlignmentX(Component.CENTER_ALIGNMENT);
			CaixasB.add(listaCampos.get(i));
		}
		
		
		JPanel inter = new JPanel();
		con.setSize(100, 100);
		inter.add(con);
		
		opcaoNovo.add(CaixasB);
		CaixasB.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		opcaoNovo.add(inter);
		inter.setAlignmentX(Component.CENTER_ALIGNMENT);
		
	}
	
	void configuraOpcaoCont() {
		opcaoCont.setLayout(new BorderLayout());
		
		JLabel textoCont = new JLabel("Jogo salvo");
		textoCont.setFont(new Font("Arial", Font.BOLD, 20)); textoCont.setHorizontalAlignment(SwingConstants.CENTER);
		opcaoCont.add(BorderLayout.PAGE_START, textoCont); 
	}
	
	public void addBotaoCarregar(JButton carrega) {
		JPanel inter = new JPanel();
		
		carrega.setSize(100, 100);
		inter.add(carrega);
		
		opcaoCont.add(BorderLayout.CENTER, inter);
	}
}