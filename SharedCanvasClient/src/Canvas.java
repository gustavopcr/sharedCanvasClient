import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Canvas extends JFrame{
	boolean mPressed = false;
	
	public Canvas() {
		
		
		addMouseListener(new MouseListener(){
			@Override
			public void mouseReleased(MouseEvent e) {
				mPressed = false;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				mPressed = true;
				drawings.clear();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		
		new Time().start();
		
		setSize(1200, 900);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	ArrayList<Drawing> drawings = new ArrayList<>();
	
	public class Drawing{
		int x, y;
		public Drawing(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public void paint(Graphics g) {
		for(int i=1; i<drawings.size(); i++) {
			int x1 = drawings.get(i).x;
			int y1 = drawings.get(i).y;
			int x2 = drawings.get(i-1).x;
			int y2 = drawings.get(i-1).y;
			g.drawLine(x2, y2, x1, y1);
		}
	}
	
	public static void main(String[] args) {
		new Canvas();
	}

	public class Time extends Thread {
		public void run() {
			while(true) {
				if(mPressed) {
					try {
					Point p = getMousePosition();
					drawings.add(new Drawing(p.x, p.y));
					}catch(Exception e) {};
				}
				repaint();	
			}
		}
	}
}
