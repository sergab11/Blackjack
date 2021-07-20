//Conrado Costa
//S�rgio Gabriel Vieira Bou�o

package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class BotoesBanca extends JButton{
	JButton inicia = new JButton("Inicia");
	JButton encerra = new JButton("Clear");
	JButton salva = new JButton("Salva");
	JButton deal = new JButton("Deal");
	
	Partida partida;
	
	//logo na cria��o dos bot�es, j� � feito o tratamento dos mesmos. Esses bot�es s�o aqueles manipulados pelo Dealer, 
	//exceto o bot�o "Deal" que indica a confirma��o do valor da aposta do jogador da vez
	public BotoesBanca(Partida p) {
		this.partida = p;
		
		inicia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					partida.comeca();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		deal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partida.proximoApostas(); 
			}
		});
		
		salva.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = null;
				JFileChooser chooser = new JFileChooser();
				
				chooser.setDialogTitle("Salvar Jogo");
		        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        
		        int i = chooser.showSaveDialog(null);
		        
		        try {
		        	if(i != JFileChooser.APPROVE_OPTION) {
		            	return;
		        	}
		        	else {
				        file = chooser.getSelectedFile();
				        partida.salvaContexto(file);
		        	} 
		        } finally {
				}
			}
		});
		
		encerra.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				partida.encerra();
			}
		});
	}
	
	public JButton getBotao(String s) {
		if(s.equals("inicia"))
			return inicia;
		if(s.equals("encerra"))
			return encerra;
		if(s.equals("Deal"))
			return deal;
		else
			return salva;
	}
}