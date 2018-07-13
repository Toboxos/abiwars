package de.toboxos.abi.cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import de.toboxos.abi.Logger;

public class StudentCard extends Card implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int power;
	protected int normal_power;
	protected int energy;
	protected int slot;
	protected boolean protect = false;
	protected boolean canAttack = false;
	protected boolean draw = true;
	
	public void setDraw(boolean draw) {
		Logger.logMessage("Draw " + name + ": " + draw );
		this.draw = draw;
	}
	
	public boolean getDraw() {
		return this.draw;
	}
	
	public int setDamage(int p, StudentCard c) {
		return p;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public void changePower(int power) {
		this.power = power;
	}
	
	public void changeNormalPoer(int power) {
		this.normal_power = power;
	}
	
	public void changeEnegery(int energy) {
		this.energy = energy;
	}
	
	public int getPower(StudentCard c) {
		return power;
	}
	
	public int getNormalPower() {
		return normal_power;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public boolean doProtect() {
		return protect;
	}
	
	public boolean canAttack() {
		return canAttack;
	}
	
	public void attacked() {
		canAttack = false;
	}
	
	public void nextTurn() {
		canAttack = true;
	}
	
	public void yourTurn() {
		
	}
	
	public void dead() {
		
	}
	
	@Override
	public void drawIcon(Graphics2D g2d, int x, int y, int width, int height) {
		
		super.drawIcon(g2d, x, y, width, height);
		Font oldFont = g2d.getFont();
		
		// Polygon
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		int sizePolygon = (int) (0.9*height / 10);
				
		// Set Font
		Font f = new Font(Font.SANS_SERIF, Font.PLAIN, (int) (0.8*height / 10));
		g2d.setFont( f );
				
		// Set Polygon
		xPoints[0] = x;
		yPoints[0] = y + height - sizePolygon;
				
		xPoints[1] = x + sizePolygon;
		yPoints[1] = y + height;
				
		xPoints[2] = x;
		yPoints[2] = y + height + sizePolygon;
				
		xPoints[3] = x - sizePolygon;
		yPoints[3] = y + height;
				
		// Draw Polygon
		g2d.setColor( Color.RED );
		g2d.fillPolygon(xPoints, yPoints, 4);
				
		// Draw Power
		g2d.setColor( Color.BLACK );
		Rectangle2D boundsPower = f.getStringBounds( Integer.toString(power), g2d.getFontRenderContext() );
		g2d.drawString( Integer.toString(power), x - (int) boundsPower.getWidth() / 2, y + height + (int) (boundsPower.getHeight() * 0.75) / 2 ); 		
				
		// Set Polygon
		xPoints[0] = x + width;
		yPoints[0] = y + height - sizePolygon;
						
		xPoints[1] = x + width + sizePolygon;
		yPoints[1] = y + height;
						
		xPoints[2] = x + width;
		yPoints[2] = y + height + sizePolygon;
						
		xPoints[3] = x + width - sizePolygon;
		yPoints[3] = y + height;
				
		// Draw Polygon
		g2d.setColor( Color.GREEN );
		g2d.fillPolygon(xPoints, yPoints, 4);
				
		// Draw Energy
		g2d.setColor( Color.BLACK );
		Rectangle2D boundsEnergy = f.getStringBounds( Integer.toString(energy), g2d.getFontRenderContext() );
		g2d.drawString( Integer.toString(energy), x + width - (int) boundsEnergy.getWidth() / 2, y + height + (int) (boundsEnergy.getHeight() * 0.75) / 2 ); 
				
		g2d.setFont( oldFont );
	}
	
	@Override
	public void drawCard(Graphics2D g2d, int x, int y, int width, int height) {
		
		super.drawCard(g2d, x, y, width, height);
		Font oldFont = g2d.getFont();
		
		// Polygon
		int[] xPoints = new int[4];
		int[] yPoints = new int[4];
		int sizePolygon = (int) (0.9*height / 10);
		
		// Set Font
		Font f = new Font(Font.SANS_SERIF, Font.PLAIN, (int) (0.8*height / 10));
		g2d.setFont( f );
		
		// Set Polygon
		xPoints[0] = x;
		yPoints[0] = y + height - sizePolygon;
				
		xPoints[1] = x + sizePolygon;
		yPoints[1] = y + height;
				
		xPoints[2] = x;
		yPoints[2] = y + height + sizePolygon;
				
		xPoints[3] = x - sizePolygon;
		yPoints[3] = y + height;
		
		// Draw Polygon
		g2d.setColor( Color.RED );
		g2d.fillPolygon(xPoints, yPoints, 4);
		
		// Draw Power
		g2d.setColor( Color.BLACK );
		Rectangle2D boundsPower = f.getStringBounds( Integer.toString(power), g2d.getFontRenderContext() );
		g2d.drawString( Integer.toString(power), x - (int) boundsPower.getWidth() / 2, y + height + (int) (boundsPower.getHeight() * 0.75) / 2 ); 		
		
		// Set Polygon
		xPoints[0] = x + width;
		yPoints[0] = y + height - sizePolygon;
								
		xPoints[1] = x + width + sizePolygon;
		yPoints[1] = y + height;
								
		xPoints[2] = x + width;
		yPoints[2] = y + height + sizePolygon;
								
		xPoints[3] = x + width - sizePolygon;
		yPoints[3] = y + height;
		
		// Draw Polygon
		g2d.setColor( Color.GREEN );
		g2d.fillPolygon(xPoints, yPoints, 4);
		
		// Draw Energy
		g2d.setColor( Color.BLACK );
		Rectangle2D boundsEnergy = f.getStringBounds( Integer.toString(energy), g2d.getFontRenderContext() );
		g2d.drawString( Integer.toString(energy), x + width - (int) boundsEnergy.getWidth() / 2, y + height + (int) (boundsEnergy.getHeight() * 0.75) / 2 ); 
				
		g2d.setFont( oldFont );
	}

}
