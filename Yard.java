package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * This class stands for the site for snake to move
 * 
 *@author 王思聪的老爸 
 * 
 * */

public class Yard extends Frame{
	
	PaintThread paintThread = new PaintThread();
	private boolean gameOver = false;
	
	public static final int ROWS =30;
	public static final int COLS = 30;
	public static final int BLOCK_SIZE = 24;
	
	private Font fontGameOver = new Font("宋体",Font.BOLD,50);
	
	private int score = 0;
	
	Snake s = new Snake(this);
	Egg e = new Egg();
	
	Image offScreenImage = null;
	
	public void launch(){
		
		this.setLocationRelativeTo(null);
		this.setSize(COLS*BLOCK_SIZE,ROWS*BLOCK_SIZE);
		this.addWindowListener(new WindowAdapter(){
			
			public void windowClosing(WindowEvent e){
				
				System.exit(0);
			}
			
		});
		
		this.setVisible(true);
		
		new Thread(paintThread).start();
		
	}
	
	
	
	public static void main(String[] args) {
		
		new Yard().launch();
	}
	
	public void stop(){
		
		gameOver = true;
	}
	
	public void paint(Graphics g){
		
		Color c= g.getColor();
		g.setColor(Color.DARK_GRAY);
		//画出横线
		for(int i=1; i<ROWS; i++) {
			g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
		}
		for(int i=1; i<COLS; i++) {
			g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, BLOCK_SIZE * ROWS);
		}
		
		g.setColor(Color.YELLOW);
		g.drawString("score:" + score, 10, 60);
		
		if(gameOver) {
			g.setFont(fontGameOver);
			g.drawString("游戏结束", 120, 180);
			
			paintThread.pause();
		}
		
		g.setColor(c);
		
		s.eat(e);
		e.draw(g);
		s.draw(g);
		
	
		
		
	}
	
	
	class PaintThread extends Thread{
	    
		private boolean running = true;
		private boolean pause = false;
		public void run(){
			
			while(running){
				
				if(pause)continue;
				
				else repaint();
				
				try{
					
					Thread.sleep(1000);
					
				}catch(Exception e){
					
					e.printStackTrace();
					
				}
			}
		}
		
		public void pause(){
			
			
			this.pause = true;
			
		}
		
		public void reStart(){
			
			this.pause = false;
			s = new Snake(Yard.this);
			gameOver = false;
			
		}
		
		public void GameOver(){
			
			running = false;
		}
	}
	
	private class KeyMonitor extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_F2){
				
				paintThread.reStart();
			}
			
			s.keyPressed(e);
		}
		
		
		
	}
	
	public int getScore(){
		
		return score;
	}
	
	public void setScore(int score){
		
		this.score = score;
		
	}

}

