import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Canvas extends JFrame{
	boolean mPressed = false;
	TCPHandler tcp;
	
	public Canvas() {
		
		tcp = new TCPHandler("localhost", 9999);
		
		addMouseListener(new MouseListener(){
			
			/* drawings.clear() está tanto no released quanto pressed pois os diferentes clients não executam essas ações em sincronia, logo
			 * é preciso manter drawings sempre clear para nao traçar o desenho de um ponto diferente do mouse.
			 */
			@Override
			public void mouseReleased(MouseEvent e) {
				mPressed = false;
				drawings.clear();
				
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
		
		Thread sendThread = new Thread(new Send());
        sendThread.start();
        Thread receiveThread = new Thread(new Receive());
        receiveThread.start();
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

    public class Send implements Runnable {
        @Override
        public void run() {
            while (true) {
                if (mPressed) {
                    try {
                        Point p = getMousePosition();
                        //drawings.add(new Drawing(p.x, p.y));
                        tcp.sendData(p.x, p.y);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                repaint();

                try {
                    Thread.sleep(10); // Optional delay to reduce CPU usage
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public class Receive implements Runnable {
        
        int[] data;
        @Override
        public void run() {
        	while(true) {
	        	if( ( data = tcp.receiveData(2) ) != null) {
	        		drawings.add(new Drawing(data[0], data[1]));
	        	}	
        	}
        }
    }
}
