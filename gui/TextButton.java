package main.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class TextButton extends ImageButton{
	
	private String text;
	private int textX,textY,textWidth,textHeight;
	private int fontSize;
	private int textType;
	private String font;
	private Color textColor;
	
	public TextButton(float x, float y, int width, int height, String text) {
		super(x, y, width, height);
		setText(text);
		fontSize=30;
		textType = Font.PLAIN;
		font = "Arial";
		textColor = Color.BLACK;
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		
		g.setFont(new Font(font, textType,fontSize));
		FontMetrics fm = g.getFontMetrics();
		textWidth = fm.stringWidth(getText());
		textHeight = fm.getMaxAscent();
	
		textX = (int)(this.getX()+(this.getWidth()-textWidth)/2);
		textY = (int)(this.getY()+(this.getHeight()+textHeight)/2);
		
		if(textWidth>getWidth()-10){
			for(int textSize=this.fontSize;textWidth>getWidth()-10;textSize--){
				g.setFont(new Font(font, textType,textSize));
			fm = g.getFontMetrics();
			textWidth = fm.stringWidth(getText());
			textHeight = fm.getMaxAscent();
		
			textX = (int)(this.getX()+(this.getWidth()-textWidth)/2);
			textY = (int)(this.getY()+(this.getHeight()+textHeight)/2);
			}
		}
		
		
		g.setColor(textColor);
		g.drawString(this.getText(), this.textX, this.textY);
		
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public int getTextType() {
		return textType;
	}

	public void setTextType(int textType) {
		this.textType = textType;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}
	
}
