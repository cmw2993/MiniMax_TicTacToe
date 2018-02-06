package ttt;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class TTTView extends JFrame implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TTTController controller;
	private TTTModel model;
	private Canvas canvas;
	private JPanel container;
	
	private final int SCREEN_WIDTH = 400;
	private final int SCREEN_HEIGHT = 300;
	
	private int fps = 12;
	
	private Timer timer;
	private BufferStrategy strategy;
	
	private Point locations[][], tileSize;
	private Sprite cross, naught, clear;
	
	public TTTView(TTTController controller, TTTModel model){
		super("Tic Tac Toe");
		this.controller = controller;
		this.model = model;
		
		cross = new Sprite("images/cross.png");
		naught = new Sprite("images/naught.png");
		clear = new Sprite("images/clear.png");
		
		setVisible(true);
		
		container = new JPanel(new BorderLayout());
		add(container);
		canvas = new Canvas();
		canvas.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		
		add(canvas);
		pack();
		
		canvas.createBufferStrategy(2);
		strategy = canvas.getBufferStrategy();
		canvas.addMouseListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		timer = new Timer ((int) 1000.0/fps, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				render();
				
			}
		});
		int dim = model.getDimension();
		locations = new Point[dim][dim];
		computeLocations(getBounds(), model.getDimension());
		timer.start();
		
	}
	
	public void computeLocations(Rectangle rect, int dim){
		tileSize = new Point(rect.width/dim, rect.height/dim);
		// Compute top left corner for each sprite
		for (int i = 0, xoff = 0, yoff = 0; i < dim; i++){
			for (int j = 0; j < dim; j++){
				xoff = j * tileSize.x;
				yoff = i * tileSize.y;
				locations[i][j] = new Point(xoff, yoff);
				
			}
		}
	}
	
	public void render(){
		
		Graphics2D gc = (Graphics2D) strategy.getDrawGraphics();
		drawBoard(gc);
		gc.dispose();
		strategy.show();
		
	}
	
	public void drawBoard(Graphics2D gc){
		Rectangle rect = getBounds();
		int dim = model.getDimension();
		
		gc.setColor(Color.WHITE);
		gc.fillRect(0, 0, rect.width, rect.height);
		
		Sprite sprite;
		
		
		// Put tiles on the canvas
		for (int i = 0; i < dim; i++){
			for (int j = 0; j < dim; j++){
				switch(model.getSquare(i, j)){
				
				case 'X':
					sprite = cross;
					break;
				case 'O':
					sprite = naught;
					break;
				default:
					sprite = clear;
					break;
				}
				Point loc = locations[i][j];
				sprite.drawImage(gc, loc.x, loc.y, tileSize.x, tileSize.y);
			}
		}
		
		// Now draw grid Lines
		gc.setColor(Color.blue);
		gc.setStroke(new BasicStroke(5));
		
		for (int i = 1; i < dim; i++){
			for (int j = 1; j < dim; j++){
				gc.drawLine(0, j * tileSize.y, dim * tileSize.x, j * tileSize.y);
				gc.drawLine(i * tileSize.x, 0, i * tileSize.x, dim * tileSize.y);

			}
		}
	}
	
	@Override
	public void repaint() {
		// handle resizes and overlays
		Rectangle bounds = getBounds();
		canvas.setSize(bounds.width, bounds.height);
		computeLocations(bounds, model.getDimension());
		render();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// get screen locations
		int xloc = e.getX();
		int yloc = e.getY();
		
		int row = yloc/tileSize.y;
		int col = xloc/tileSize.x;
		
		controller.squareSelected(row, col);

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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	
	public boolean message(String msg){
		
		int ret = JOptionPane.showConfirmDialog(this, msg, "A Question", JOptionPane.YES_NO_OPTION);
		
		return (ret == JOptionPane.YES_NO_OPTION);
	}

}
