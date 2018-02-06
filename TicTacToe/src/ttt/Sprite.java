package ttt;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class Sprite {
	
	private int frameCount;
	private int[] src;
	private int curr = 0;
	private Image image;
	
	public Sprite(String resid){
		this(resid, 1);
	}
	
	public Sprite(String resid, int frames){
		frameCount = frames;
		try {
			URL url = this.getClass().getClassLoader().getResource(resid);
			if (url == null){
				System.err.println("Sprite not found!");
			}
			image = ImageIO.read(url);
		} catch(IOException e){
			System.err.println("Failed to load sprite!");
		}
		src = new int[frameCount];
		
		for (int i = 0; i < src.length; i++){
			int w = image.getWidth(null)/frameCount;
			src[i] = i * w;
		}
		
	}
	
	public void drawImage(Graphics2D gc, int rx, int ry, int width, int height, boolean active){
		int sw = image.getWidth(null)/frameCount + src[curr];
		gc.drawImage(image, rx, ry, width,height,
				src[curr], 0, sw, image.getHeight(null), null);
		if (active){
			curr++;
			curr %= frameCount;
		}
	}
	
	public void drawImage(Graphics2D gc, int rx, int ry, int width, int height){

		gc.drawImage(image, rx, ry, rx + width,  ry + height,
				0, 0, image.getWidth(null), image.getHeight(null), null);
	}


}