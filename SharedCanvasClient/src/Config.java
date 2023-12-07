import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Config extends JFrame implements ActionListener {

	int count = 0;
	private JFrame frame;
	private JPanel panel;
	
	public Config() {
		frame = new JFrame();
		JLabel serverLabel = new JLabel("<html>server:<br/></html>");
		serverLabel.setBounds(20, 20, 165, 30);
		JTextField serverText = new JTextField(20);
		serverText.setBounds(50, 20, 165, 30);
		
		JLabel portLabel = new JLabel("<html><br/>port:</html>");
		portLabel.setBounds(0, 20, 165, 30);
		JTextField portText = new JTextField(20);
		portText.setBounds(50, 150, 165, 30);
		

		JButton button = new JButton("SET VALUES");
		
		button.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				TCPHandler.server = serverText.getText();
				TCPHandler.port = Integer.parseInt(portText.getText());
				GUI gui = new GUI();
				gui.show();
				frame.dispose();
			}
			
		});
		
		panel = new JPanel();
		//panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0, 1));
		//panel.add(button);
		panel.add(serverLabel);
		panel.add(serverText);
		panel.add(portLabel);
		panel.add(portText);
		panel.add(button);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Our GUI");
		frame.add(panel);
		frame.setVisible(true);
		
		frame.setSize(500, 250);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Config();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
