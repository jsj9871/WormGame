package wormGame;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WormGame {
	
	JFrame frame[] = new JFrame[2];
	int width1 = 80;
	int height1 = 60;
	int size = 10;		// 뱀 마디의 크기, 즉 가로, 세로 길이
	
	int width2 = 800;
	int height2 = 600;
	
	int resultX = 360;
	int resultY = 240;
	
	ArrayList<Madi> foods = new ArrayList<Madi>();
	
	int speed = 50;		// 쓰레드 슬립을 0.05초
	
	Worm1 worm1;
	Worm2 worm2;
	
	Madi food;
	
	int xDirection1 = 1;
	int yDirection1 = 0;
	int xDirection2 = 1;
	int yDirection2 = 0;
		
	public WormGame() {
		frame[0] = new JFrame("WORM GAME");
		frame[0].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame[0].setSize(width1 * size, height1 * size);
		
		frame[0].getContentPane().add(new GamePanel1());
		frame[0].addKeyListener(new MyKeyListener());
		
		worm1 = new Worm1();
		worm2 = new Worm2();
		
		food = new Madi(30, 30);
		
		frame[0].setVisible(true);
		
		backGroundMusic();
		
		Timer timer = new Timer();
		TimerTask timerTask = new TimerTask() {
			public void run() {
				
				frame[1] = new JFrame("GAME RESULT");
				frame[1].setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame[1].setSize(width2, height2);
				frame[1].getContentPane().add(new GamePanel2());
				
				frame[1].setVisible(true);
				
			}
		};
		
		timer.schedule(timerTask, 32000);
	}
	
	public void backGroundMusic() {
		File file = new File("C:\\Users\\jsh95\\eclipse-workspace\\wormGame\\src\\sound\\WormGameBGM.wav");
		
		try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            Timer timer = new Timer();
    		TimerTask timerTask = new TimerTask() {
    			public void run() {
    				clip.stop();
    				clip.close();
    			}
    		};
    		
    		timer.schedule(timerTask, 32000);
			} catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	
	
	// 게임 진행 
	public void play() {
		ArrayList<Madi> list = new ArrayList<Madi>();
		Madi head1 = (Madi) worm1.getList().get(0);
		Madi head2 = (Madi) worm2.getList().get(0);
		
		
		
		while (true) {
			
				if ((head1.getX() == food.getX()) && (head1.getY() == food.getY())) {
					worm1.growAndForward();
					food.setX((int) (Math.random() * width1));
					food.setY((int) (Math.random() * height1));
				} else if ((head1.getX() == worm2.) && (head1.getY() == worm2.)) {
					worm1.growAndForward();
				} else if ((head2.getX() == worm1.) && (head2.getY() == worm1.)) {
					worm2.growAndForward();
				} else if ((head2.getX() == food.getX()) && (head2.getY() == food.getY()) ) {
					worm2.growAndForward();
					food.setX((int) (Math.random() * width1));
					food.setY((int) (Math.random() * height1));
				} else {
					worm1.forward();
					worm2.forward();
				}
				
				frame[0].repaint();
				
				try {
					Thread.sleep(speed);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
	}
	
	private class GamePanel1 extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			for (Madi madi : (ArrayList<Madi>) worm1.getList()) {
				g.setColor(Color.red);
				g.fillRect(madi.getX() * size, madi.getY() * size, size, size);
				
				g.setColor(Color.black);
				g.drawRect(madi.getX() * size, madi.getY() * size, size, size);				
			}
			
			for (Madi madi : (ArrayList<Madi>) worm2.getList()) {
				g.setColor(Color.blue);
				g.fillRect(madi.getX() * size, madi.getY() * size, size, size);
				
				g.setColor(Color.black);
				g.drawRect(madi.getX() * size, madi.getY() * size, size, size);				
			}
			
				g.setColor(Color.black);
				g.fillRect(food.getX() * size, food.getY() * size, size, size);
		}
	}
	
	private class GamePanel2 extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (worm1.list.size() > worm2.list.size()) {
				g.drawString("[RED WORM WIN]", resultX, resultY);
			} else if (worm1.list.size() < worm2.list.size()) {
				g.drawString("[BLUE WORM WIN]", resultX, resultY);
			} else {
				g.drawString("[DREW]", resultX, resultY);
			}
			
		}
	}
	
	private class MyKeyListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					yDirection1 = -1;
					xDirection1 = 0;
					break;
				case KeyEvent.VK_DOWN:
					yDirection1 = 1;
					xDirection1 = 0;					
					break;
				case KeyEvent.VK_LEFT:
					yDirection1 = 0;
					xDirection1 = -1;					
					break;
				case KeyEvent.VK_RIGHT:
					yDirection1 = 0;
					xDirection1 = 1;					
					break;
				case KeyEvent.VK_W:
					yDirection2 = -1;
					xDirection2 = 0;
					break;
				case KeyEvent.VK_S:
					yDirection2 = 1;
					xDirection2 = 0;					
					break;
				case KeyEvent.VK_A:
					yDirection2 = 0;
					xDirection2 = -1;					
					break;
				case KeyEvent.VK_D:
					yDirection2 = 0;
					xDirection2 = 1;					
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	
	private class Worm1 {
		
		ArrayList<Madi> list = new ArrayList<Madi>();
		
		public Worm1() {
			list.add(new Madi(10, 10));
			list.add(new Madi(9, 10));
			list.add(new Madi(8, 10));
			list.add(new Madi(7, 10));
		}
		
		public ArrayList getList() {
			return this.list;
		}
		
		public void forward() {
			int madiCount = list.size();
			for (int i = madiCount - 1; i > 0; i--) {
				list.get(i).setX(list.get(i-1).getX());		// 바로 앞 마디의 x 좌표를 가져옴
				list.get(i).setY(list.get(i-1).getY());		// 바로 앞 마디의 y 좌표를 가져옴
			}
			
			list.get(0).setX(list.get(0).getX() + xDirection1);	// 머리 마디의 x 좌표 = (현재 x 좌표 + xDirection)
			list.get(0).setY(list.get(0).getY() + yDirection1);	// 머리 마디의 x 좌표 = (현재 x 좌표 + xDirection)
		}
		
		public void growAndForward() {
			int madiCount = list.size();
			
			Madi newLastMadi = new Madi(list.get(madiCount - 1).getX(), list.get(madiCount - 1).getY());
			
			forward();
			
			list.add(newLastMadi);
		}
	}
	
	private class Worm2 {
		ArrayList<Madi> list = new ArrayList<Madi>();
		
		public Worm2() {
			list.add(new Madi(10, 2));
			list.add(new Madi(9, 2));
			list.add(new Madi(8, 2));
			list.add(new Madi(7, 2));
		}
		
		public ArrayList getList() {
			return this.list;
		}
		
		public void forward() {
			int madiCount = list.size();
			for (int i = madiCount - 1; i > 0; i--) {
				list.get(i).setX(list.get(i-1).getX());		// 바로 앞 마디의 x 좌표를 가져옴
				list.get(i).setY(list.get(i-1).getY());		// 바로 앞 마디의 y 좌표를 가져옴
			}
			
			list.get(0).setX(list.get(0).getX() + xDirection2);	// 머리 마디의 x 좌표 = (현재 x 좌표 + xDirection)
			list.get(0).setY(list.get(0).getY() + yDirection2);	// 머리 마디의 x 좌표 = (현재 x 좌표 + xDirection)
		}
		
		public void growAndForward() {
			int madiCount = list.size();
			
			Madi newLastMadi = new Madi(list.get(madiCount - 1).getX(), list.get(madiCount - 1).getY());
			
			forward();
			
			list.add(newLastMadi);
		}
	}
}
