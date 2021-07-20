//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package controller;

import java.util.ArrayList;
import javax.swing.*;
import view.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


public class CaixasBotaoIniciais extends JButton{
	
	private int MAX_JOGADORES = 4;
	
	ArrayList <JTextField> listaCampos = new ArrayList<JTextField>();

	JButton botaoContinuar = new JButton("Continuar");
	JButton botaoCarregar = new JButton("Carregar");
	JanelaInicial j;
	ArrayList<String> nomes = new ArrayList<String>();
	
	public CaixasBotaoIniciais(JanelaInicial k){
		j = k;
		
		for (int i = 0; i < MAX_JOGADORES; i++) {
			listaCampos.add(new JTextField(15));
		}
				
		j.addCaixasBotao(listaCampos, botaoContinuar);
		botaoContinuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < MAX_JOGADORES; i++) {
					String aux_nome = listaCampos.get(i).getText().trim();
					if (!aux_nome.isEmpty())
						nomes.add(aux_nome);
				}

				if (nomes.isEmpty()) {
					return;
				}
				
				try {
					new Partida(nomes);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				j.setVisible(false);
				j.dispose();
			}
		});
		
		j.addBotaoCarregar(botaoCarregar);
		botaoCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = null;
				JFileChooser chooser = new JFileChooser();
				
				chooser.setDialogTitle("Carregar Jogo");
		        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        
		        int i = chooser.showOpenDialog(null);
		        
		        try {
		        	if(i != JFileChooser.APPROVE_OPTION) {
		            	return;
		        	}
		        	else {
				        file = chooser.getSelectedFile();
				        try {
							new Partida(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		        	} 
		        } finally {
		        	j.setVisible(false);
					j.dispose();
				}
			}
		});
		
	}
}