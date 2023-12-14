import java.net.*;

public class UDPHandler {
	public DatagramSocket receiverSocket;
	
	
	public UDPHandler(int port) {
		try {
			
			this.receiverSocket = new DatagramSocket(port);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String receiveData() {
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		try {
			this.receiverSocket.receive(receivePacket);
		}catch(Exception e) {
			e.printStackTrace();
		}
		String receivedMessage = new String(receivePacket.getData(), 0, receiveData.length);
		return receivedMessage;
	}
}
