package de.toboxos.abi.cards;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String name = "";
	protected String description = "";
	protected int cost;
	protected int image = 0;
	
	private Color colorHighlight;
	private boolean selected = false;
	protected Color background = new Color(255, 255, 255);
	protected Color originalColor = new Color(255, 255, 255);
	
	public void played() {
		
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}
	
	public void changeCost(int cost) {
		this.cost = cost;
	}
	
	public int getWidth(int height) {
		return 2*height / 3;
	}
	
	public int getHeight(int width) {
		return 3*width / 2;
	}
	
	public void setHighlightedColor(Color c) {
		colorHighlight = c;
	}
	
	public void setOriginalColor(Color c) {
		setHighlightedColor(c);
		originalColor = c;
	}
	
	public Color getHighlightedColor() {
		return colorHighlight;
	}
	
	public Color getOriginalColor() {
		return originalColor;
	}
	
	public void select() {
		selected = true;
	}
	
	public void unselect() {
		selected = false;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public void drawIcon(Graphics2D g2d, int x, int y, int width, int height) {
		Font oldFont = g2d.getFont();
		
		// Draw Card
		g2d.setColor( background );
		g2d.fillRect(x, y, width, height);
		g2d.setColor( Color.BLACK );
		g2d.drawRect(x, y, width, height);
		
		// Draw Highlight
		if( selected ) {
			g2d.setColor(Color.BLUE);
			for( int i = 0; i < 4; i++ ) {
				g2d.drawRect(x-i, y-i, width+2*i, height+2*i);
			}
		} else if( colorHighlight != null ) {
			g2d.setColor( colorHighlight );
			for( int i = 0; i < 4; i++ ) {
				g2d.drawRect(x-i, y-i, width+2*i, height+2*i);
			}
			g2d.setColor( Color.BLACK );
		}
		
		// Draw Name
		Font fName = new Font(Font.SANS_SERIF, Font.PLAIN, height / 10);
		g2d.setFont( fName );
		g2d.setColor( Color.BLACK );
		
		Rectangle2D boundsName = fName.getStringBounds( name, g2d.getFontRenderContext() );
		g2d.drawString( name, x + (width - (int) boundsName.getWidth()) / 2, y + (int) boundsName.getHeight() );
		
		// Draw Image
		g2d.drawRect(x + width / 10 - 1, y + 2*(height / 2 - (8*width / 10) / 2) / 3 - 1, (8*width / 10) + 1, (8*width / 10) / 2 + 1);
		if( ImageLoader.getImage(image) != null ) g2d.drawImage(ImageLoader.getImage(image), x + width / 10, y + 2*(height / 2 - (8*width / 10) / 2) / 3, (8*width / 10), (8*width / 10) / 2, null );
		
		// Draw Description
		Font fDes = new Font(Font.SANS_SERIF, Font.PLAIN, height / 20);
		g2d.setFont(fDes);
		g2d.setColor( Color.BLACK );
		
		// Brake Description into lines
		List<String> lines = new ArrayList<>();
		
				
		String line = "", temp = "";
		int lineCount = 0;
		for( char c : description.toCharArray() ) {
			if( c == '\n' ) {
				line += temp;
				lines.add( line );
				line = "";
				temp = "";
				lineCount = 0;
			}
					
			else if( c == ' ' ) {
				if( lineCount >= 20 ) {
					lines.add(line);
					temp += c;
					line = temp;
					temp = "";
					lineCount = line.length();
				} else {
					temp += c;
					line += temp;
					temp = "";
					lineCount++;
				}
			}
					
			else {
				temp += c;
				lineCount++;
			}
		} 
		line += temp;
		lines.add(line);
			
		int lc = 0;
		for( String l : lines ) {
			Rectangle2D boundsDes = fDes.getStringBounds( l, g2d.getFontRenderContext() );
			g2d.drawString(l, x + ((width) - (int) boundsDes.getWidth() ) / 2, y + height / 2 + (lc+1) * (int) boundsDes.getHeight() );
			lc++;
		}
		
		g2d.setFont( oldFont );
	}
	
	public void drawBackground(Graphics2D g2d, int x, int y, int width, int height) {
		Font oldFont = g2d.getFont();
		
		// Draw Card
		g2d.setColor( Color.WHITE );
		g2d.fillRect(x, y, width, height);
		g2d.setColor( Color.BLACK );
		g2d.drawRect(x, y, width, height);
	
		// Draw Text
		Font abiFont = new Font(Font.SANS_SERIF, Font.PLAIN, (int) (2.5*height / 10) );
		g2d.setFont( abiFont );
		Rectangle2D boundsAbi = abiFont.getStringBounds( "ABI", g2d.getFontRenderContext() );
		g2d.drawString( "ABI", x + (width - (int) boundsAbi.getWidth()) / 2, y + height / 2 );
		
		Font yearFont = new Font(Font.SANS_SERIF, Font.PLAIN, (int) (1.5*height / 10) );
		g2d.setFont( yearFont );
		Rectangle2D boundsYear = yearFont.getStringBounds( "2018", g2d.getFontRenderContext() );
		g2d.drawString( "2018", x + (width - (int) boundsYear.getWidth()) / 2, y + height / 2 + (int) boundsYear.getHeight() );
		
		g2d.setFont(oldFont);
	}
	
	public void drawCard(Graphics2D g2d, int x, int y, int width, int height) {
		Font oldFont = g2d.getFont();
		
		// Draw Highlight
		if( selected ) {
			g2d.setColor(Color.BLUE);
			for( int i = 0; i < 4; i++ ) {
				g2d.drawRect(x-i, y-i, width+2*i, height+2*i);
			}
		} else if( colorHighlight != null ) {
			g2d.setColor( colorHighlight );
			for( int i = 0; i < 4; i++ ) {
				g2d.drawRect(x-i, y-i, width+2*i, height+2*i);
			}
			g2d.setColor( Color.BLACK );
		}
		
		// Draw Card
		g2d.setColor( background );
		g2d.fillRect(x, y, width, height);
		g2d.setColor( Color.BLACK );
		g2d.drawRect(x, y, width, height);
		
		// Draw Name
		Font fName = new Font(Font.SANS_SERIF, Font.PLAIN, height / 10);
		g2d.setFont( fName );
		g2d.setColor( Color.BLACK );
		
		Rectangle2D boundsName = fName.getStringBounds( name, g2d.getFontRenderContext() );
		g2d.drawString( name, x + (width - (int) boundsName.getWidth()) / 2, y + (int) boundsName.getHeight() );
		
		
		// Draw Images
		g2d.drawRect(x + width / 10 - 1, y + 2*(height / 2 - (8*width / 10) / 2) / 3 - 1, (8*width / 10) + 1, (8*width / 10) / 2 + 1);
		if( ImageLoader.getImage(image) != null ) g2d.drawImage(ImageLoader.getImage(image), x + width / 10, y + 2*(height / 2 - (8*width / 10) / 2) / 3, (8*width / 10), (8*width / 10) / 2, null );
		
		// Draw Description
		Font fDes = new Font(Font.SANS_SERIF, Font.PLAIN, height / 20);
		g2d.setFont(fDes);
		g2d.setColor( Color.BLACK );
		
		// Brake Description into lines
		List<String> lines = new ArrayList<>();
		
		String line = "", temp = "";
		int lineCount = 0;
		for( char c : description.toCharArray() ) {
			if( c == '\n' ) {
				line += temp;
				lines.add( line );
				line = "";
				temp = "";
				lineCount = 0;
			}
			
			else if( c == ' ' ) {
				if( lineCount >= 20 ) {
					lines.add(line);
					temp += c;
					line = temp;
					temp = "";
					lineCount = line.length();
				} else {
					temp += c;
					line += temp;
					temp = "";
					lineCount++;
				}
			}
			
			else {
				temp += c;
				lineCount++;
			}
		} 
		line += temp;
		lines.add(line);
 		
		int lc = 0;
		for( String l : lines ) {
			Rectangle2D boundsDes = fDes.getStringBounds( l, g2d.getFontRenderContext() );
			g2d.drawString(l, x + ((width) - (int) boundsDes.getWidth() ) / 2, y + height / 2 + (lc+1) * (int) boundsDes.getHeight() );
			lc++;
		}
		
		// Draw Cost
		int radius = (int) (0.75*height / 10);
		g2d.setColor( new Color(67, 167, 255) );
		g2d.fillOval( x - radius, y - radius, radius*2, radius*2);
		g2d.drawOval( x - radius, y - radius, radius*2, radius*2);
		 
		Font fCost = new Font(Font.SANS_SERIF, Font.CENTER_BASELINE, (int) (1*height / 10));
		g2d.setFont( fCost );
		
		g2d.setColor( Color.BLACK );
		Rectangle2D boundsCost = fCost.getStringBounds( Integer.toString(cost), g2d.getFontRenderContext() );
		g2d.drawString( Integer.toString(cost), x - (int) boundsCost.getWidth() / 2, y + (int) (boundsCost.getHeight() * 0.75) / 2 );
		
		g2d.setFont( oldFont );
	}

	
	@Override
	public String toString() {
		return this.name;
	}
}