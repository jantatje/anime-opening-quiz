package main.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageButton extends UIObject{
	
	private BufferedImage[] images;
	private ClickListener clickListener;
	
	public ImageButton(float x, float y, int width, int height,BufferedImage[] images) {
		super(x, y, width, height);
		this.images=images;
	}

	public ImageButton(float x, float y, int width, int height) {
		super(x, y, width, height);
	}
	
	@Override
	public void render(Graphics g) {
		if(hovering){
			g.drawImage(images[1], (int) getX(),(int) getY(), getWidth(), getHeight(),null);
		}else{
			g.drawImage(images[0], (int) getX(),(int) getY(), getWidth(), getHeight(),null);
		}
	}

	@Override
	public void tick() {}

	@Override
	public void onClick() {
		clickListener.onClick();
	}
	
	public void setClickListener(ClickListener clickListener) {
		this.clickListener=clickListener;
	}

	public BufferedImage[] getImages() {
		return images;
	}

	public void setImages(BufferedImage[] images) {
		this.images = images;
	}
	
}
