package de.toboxos.abi;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JList;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

import java.awt.GridBagLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionListener;

import de.toboxos.abi.cards.Card;
import de.toboxos.abi.cards.CardGenerator;

import javax.swing.event.ListSelectionEvent;

public class ChooseDeck extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;	
	private JList<String> listCards;
	private JList<String> listDeck;
	private MyListModel<String> cardsModel;
	private DefaultListModel<String> deckModel;
	
	
	ChooseDeck instance = null;

	private Runnable callback;
	private JLabel lblCount;
	private Card showCard;
	private JButton btnAdd;
	private JButton btnRem;
	
	private String[] cards = new String[] {"Georg", "Axel", "General", "Arwen", "Cooper", "Flamur", "Daniel", "Emilia", "Felix", "Manu", "Felix G.", "Dubowy", "Steffen", "Franzi", "Marike", "Nils", "Sandro", "Zelenka", "Allgeier", "Taraschewski", "Forstner", "Schellmann", "Wiech", "Tornau", "Kreuzer", "Mischo"};
	private int[] amount = new int[] 	  {		 3, 	 3,  		3,		 3, 	   2,		 2,		   3, 		 3, 	  3, 	  4, 		  3, 		3, 		   2, 		 4,		   3,	   3,		 2,			2, 			4,				4,			3,			  4, 	   2,		 4, 		2,		  4};
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;
	private JLabel lblNewLabel_7;
	private JLabel lblNewLabel_8;
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		if( showCard == null ) return;
		
		int x = listDeck.getX() - listCards.getWidth() + 20;
		int width = listDeck.getX() - x - 20;
		int y = 70;
		showCard.drawCard((Graphics2D) g, x, y, width, showCard.getHeight(width));
	}
	
	/**
	 * Create the frame.
	 */
	public ChooseDeck() {
		setResizable(false);
		setTitle("Deck w\u00E4hlen");
		instance = this;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		
		
		cardsModel = new MyListModel<String>();
		for( int i = 0; i < cards.length; i++ ) {
			cardsModel.addElement(cards[i] + " (" + amount[i] + ")");
		}
		
		listCards = new JList<String>();
		listCards.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = listCards.getSelectedIndex();
				
				showCard = CardGenerator.getCardFromName( cards[index] );
				repaint();
			}
		});
		listCards.setModel(cardsModel);
		contentPane.add(listCards);
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{79, 0, 79, 0};
		gbl_panel.rowHeights = new int[]{37, 205, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		btnAdd = new JButton("Add ->");
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = listCards.getSelectedIndex();
				if( amount[index] == 0 ) return;
				if( deckModel.size() == 30 ) return; 
				amount[index]--;
					
				deckModel.addElement(cards[index]);
				cardsModel.setElement(index, cards[index] + " (" + amount[index] + ")");
				lblCount.setText("" + deckModel.size() + " / 30");
				repaint();
			}
		});
		
		lblCount = new JLabel("0 / 30");
		GridBagConstraints gbc_lblCount = new GridBagConstraints();
		gbc_lblCount.insets = new Insets(0, 0, 5, 5);
		gbc_lblCount.gridx = 1;
		gbc_lblCount.gridy = 0;
		panel.add(lblCount, gbc_lblCount);
		
		GridBagConstraints gbc_btnAdd = new GridBagConstraints();
		gbc_btnAdd.fill = GridBagConstraints.BOTH;
		gbc_btnAdd.insets = new Insets(0, 0, 5, 5);
		gbc_btnAdd.gridx = 1;
		gbc_btnAdd.gridy = 2;
		panel.add(btnAdd, gbc_btnAdd);
		
		btnRem = new JButton("<- Remove");
		btnRem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for( int i = 0; i < cards.length; i++ ) {
					if( cards[i] == listDeck.getSelectedValue() ) {
						amount[i]++;
						cardsModel.setElement(i, cards[i] + " (" + amount[i] + ")");
						break;
					}
				}
				
				deckModel.removeElement( listDeck.getSelectedValue() );
				lblCount.setText("" + deckModel.size() + " / 30");
				repaint();
			}
		});
		GridBagConstraints gbc_btnRem = new GridBagConstraints();
		gbc_btnRem.insets = new Insets(0, 0, 5, 5);
		gbc_btnRem.fill = GridBagConstraints.BOTH;
		gbc_btnRem.gridx = 1;
		gbc_btnRem.gridy = 3;
		panel.add(btnRem, gbc_btnRem);
		
		JButton btnFertig = new JButton("Fertig");
		btnFertig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				callback.run();
			}
		});
		GridBagConstraints gbc_btnFertig = new GridBagConstraints();
		gbc_btnFertig.insets = new Insets(0, 0, 5, 5);
		gbc_btnFertig.gridx = 1;
		gbc_btnFertig.gridy = 4;
		panel.add(btnFertig, gbc_btnFertig);
		
		lblNewLabel = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 5;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		lblNewLabel_1 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 6;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 7;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		lblNewLabel_3 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 8;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		lblNewLabel_4 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 9;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		lblNewLabel_5 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 1;
		gbc_lblNewLabel_5.gridy = 10;
		panel.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		lblNewLabel_6 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 1;
		gbc_lblNewLabel_6.gridy = 11;
		panel.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		lblNewLabel_7 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 1;
		gbc_lblNewLabel_7.gridy = 12;
		panel.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		lblNewLabel_8 = new JLabel(" ");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 1;
		gbc_lblNewLabel_8.gridy = 13;
		panel.add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		JButton btnBilderSpeichern = new JButton("Bilder speichern");
		btnBilderSpeichern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for( String name : Abi.allCards ) {
					Card c = CardGenerator.getCardFromName(name);
						
					BufferedImage bi = new BufferedImage(650, c.getHeight(650), BufferedImage.TYPE_INT_RGB);
					c.drawCard((Graphics2D) bi.getGraphics(), 75, 75, 500, c.getHeight(500));
						
					try {
						ImageIO.write(bi, "jpg", new File(name + ".jpg"));
						System.out.println("Saved " + name);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		GridBagConstraints gbc_btnBilderSpeichern = new GridBagConstraints();
		gbc_btnBilderSpeichern.insets = new Insets(0, 0, 0, 5);
		gbc_btnBilderSpeichern.gridx = 1;
		gbc_btnBilderSpeichern.gridy = 16;
		panel.add(btnBilderSpeichern, gbc_btnBilderSpeichern);
		
		deckModel = new DefaultListModel<String>();
		listDeck = new JList<String>();
		listDeck.setModel(deckModel);
		contentPane.add(listDeck);
	}

	public void setCallback(Runnable r) {
		this.callback = r;
	}
	
	public List<String> getDeck() {
		List<String> l = new ArrayList<>();
		
		for( int i = 0; i < deckModel.size(); i++ ) {
			l.add(deckModel.get(i));
		}
		
		return l;
	}
	
	private class MyListModel<E> extends AbstractListModel<E>{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		private List<E> elements = new ArrayList<>();
		
		@Override
		public E getElementAt(int index) {
			return elements.get(index);
		}

		@Override
		public int getSize() {
			return elements.size();
		}
		
		public void setElement(int index, E element) {
			elements.set(index, element);
		}
		
		public void addElement(E element) {
			elements.add(element);
		}
	}
	
}