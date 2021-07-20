//Conrado Costa
//Sérgio Gabriel Vieira Bouço

package controller;

import javax.swing.*;
import java.awt.event.*;

public class BotoesJogador extends JButton{
	JButton bSplit = new JButton("Split");
	JButton bHit = new JButton("Hit");
	JButton bStand = new JButton("Stand");
	JButton bDouble = new JButton("Double");
	JButton bSurrender = new JButton("Surrender");
	JButton bQuit = new JButton("Quit");
	
	SubjectPartida partida;
	
	boolean q = true;
	String op = null;
	
	int jog = 0;
	int nMao = 0;
	
	//logo na criação desses botões, trata-se eventos de clique nos mesmos, onde "op" é a operação do clique, ou seja, o que o jogador decidiu fazer com a mão dele,
	//"jog" é o jogador que chama esses eventos e "numMao" indica em qual mão será essa operação, tendo em vista que o mesmo jogador pode ter mais de uma mão devido a um split anterior
	public BotoesJogador(SubjectPartida p, int numJ, int numMao) {
		this.partida = p;
		this.jog = numJ;
		this.nMao = numMao;
		
		bHit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "Hit";
				try {
					partida.operacao(op, jog, nMao);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				op = null; 
			}
		});
		
		bStand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "Stand";
				try {
					partida.operacao(op, jog, nMao);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				op = null; 
			}
		});
		
		bDouble.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "Double";
				try {
					partida.operacao(op, jog, nMao);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				op = null;
			}
		});
		
		bSurrender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "Surrender";
				partida.operacao(op, jog, nMao);
				op = null; 
			}
		});
		
		bSplit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "Split";
				partida.operacao(op, jog, nMao);
				op = null; 
			}
		});
		
		bQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				op = "Quit";
				partida.operacao(op, jog, nMao);
				op = null; 
			}
		});
	}
	
	//método para auxiliar a adição dos botões na janela
	public JButton getBotao(String s) {
		if(s == "Split")
			return bSplit;
		
		if(s == "Hit")
			return bHit;
		
		if(s == "Stand")
			return bStand;
		
		if(s == "Double")
			return bDouble;
		
		if(s == "Surrender")
			return bSurrender;
		
		return bQuit;
	}
}