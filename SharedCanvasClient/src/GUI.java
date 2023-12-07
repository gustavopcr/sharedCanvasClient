import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

	int count = 0;
	private JLabel label;
	private JFrame frame;
	private JPanel panel;
	
	public GUI() {
		frame = new JFrame();
		JButton buttonCanvas = new JButton("Canvas");
		buttonCanvas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Canvas canvas = new Canvas();
				canvas.show();
				frame.dispose();
			}
			
		});
		JButton buttonConfig = new JButton("Config");
		buttonConfig.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Config config = new Config();
				config.show();
				frame.dispose();
			}
			
		});
		
		label = new JLabel("Number of clicks: 0");
		
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(buttonCanvas);
		panel.add(buttonConfig);
		
		panel.add(label);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Our GUI");
		frame.add(panel);
		frame.setVisible(true);
		
		frame.setSize(1200, 900);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		count++;
		Canvas canvas = new Canvas();
		canvas.show();
		frame.dispose();
		//label.setText("Number of clicks: " + count);
	}
}
