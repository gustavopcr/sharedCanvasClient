import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import java.util.ArrayList;

public class Canvas extends JFrame {
    boolean mPressed = false;
    ArrayList<Drawing> drawings = new ArrayList<>();
    TCPHandler tcp = new TCPHandler("localhost", 9999);
    UDPHandler udp = new UDPHandler(9008);
    public static String title = "";
    
    public static void setTitle(String s, Canvas c) {
    	c.setTitle(s);
    }
    public Canvas() {
        addMouseListener(new MyMouseListener());
        addMouseMotionListener(new MyMouseListener());

        Thread receiveThread = new Thread(new Receive());
        receiveThread.start();
        
        //Thread receiveUDPThread = new Thread(new ReceiveUDP());
        //receiveUDPThread.start();

        setSize(1200, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public class Drawing {
        int x, y;

        public Drawing(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void paint(Graphics g) {
        for (int i = 1; i < drawings.size(); i++) {
            int x1 = drawings.get(i).x;
            int y1 = drawings.get(i).y;
            int x2 = drawings.get(i - 1).x;
            int y2 = drawings.get(i - 1).y;
            g.drawLine(x2, y2, x1, y1);
        }
    }

    public static void main(String[] args) {
        Canvas c = new Canvas();
    }
    
    public class Receive implements Runnable {
        int[] data;

        @Override
        public void run() {
            while (true) {
                if ((data = tcp.receiveData(2)) != null) {
                    drawings.add(new Drawing(data[0], data[1]));
                }
                repaint();
            }
        }
    }
    
    public class ReceiveUDP implements Runnable {
        
        @Override
        public void run() {
            while (true) {
            	//System.out.println("alo");
            	//String usuarios = udp.receiveData();
                //System.out.println("usuario: " + udp.receiveData());
            }
        }
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            drawings.clear();
            mPressed = false;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            drawings.clear();
            mPressed = true;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (mPressed) {
                int x = e.getX();
                int y = e.getY();
                drawings.add(new Drawing(x, y));
                tcp.sendData(x, y);
                System.out.println("x: " + x + " y: " + y);
                repaint();
            }
        }
    }
    
}
