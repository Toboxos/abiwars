package de.toboxos.abi;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

import de.toboxos.abi.events.InterfaceListener;

public class GUI extends JFrame implements MouseListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	
	
	public StageGame stageGame;

	/**
	 * Create the frame.
	 */
	public GUI(InterfaceListener e ) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		//setSize(new Dimension(1280, 720));
		setResizable(false);
		setExtendedState( JFrame.MAXIMIZED_BOTH );
		setUndecorated( true );
		
		this.setLayout( new GridLayout(1, 1) );
		
		this.addMouseListener( this );
		this.addMouseMotionListener( this );
		this.addKeyListener( this );
		
		stageGame = new StageGame( e );
		this.add( stageGame );
		this.setIgnoreRepaint( false );
		this.repaint();
	
		this.setIgnoreRepaint( false );
		setVisible( true );
		
		GUI instance = this;
		Timer t = new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				instance.repaint();
			}
		});
		t.setRepeats(true);
		t.start();
	}
	
	public void close() {
		this.dispose();
	}
	
	public StageGame getStageGame() {
		return stageGame;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		stageGame.mouse( e );
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if( e.getKeyCode() == KeyEvent.VK_ESCAPE ) {
			this.close();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		stageGame.mouse( e );
		repaint();
	}

}
