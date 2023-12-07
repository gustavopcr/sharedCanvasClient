import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.io.*;
import java.net.*;

public class TCPHandler {
	private Socket socket;
	private OutputStream outToServer;
	private InputStream inFromServer;
	
	
	public TCPHandler(String server, int port){
		try {
			
			this.socket = new Socket(server, port);
			this.outToServer = socket.getOutputStream();
			this.inFromServer = socket.getInputStream();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendData(int x, int y) {
		byte[] bytesToSend = intArrayToBytes(new int[]{x, y});
		try {
			this.outToServer.write(bytesToSend);
			this.outToServer.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public int[] receiveData(int n) {
		byte[] receivedBytes = new byte[4*n]; // Adjust buffer size as needed
		try {
			int bytesRead = inFromServer.read(receivedBytes); // Read bytes into the buffer
	        if (bytesRead == Integer.BYTES * n) {
	            // Convert bytes back to individual integers
	            int[] receivedIntegers = bytesToIntArray(receivedBytes);
	            // Print received integers
	            System.out.print("Received integers: ");
	            for (int num : receivedIntegers) {
	                System.out.print(num + " ");
	            }
	            System.out.println();
	            return receivedIntegers;
	        } else {
	            System.out.println("Insufficient bytes received for n integers.");
	        }
        }catch(Exception e) {
        	e.printStackTrace();
        }
		return null;
	}
	
	private void closeSocket() {
		try {
			this.socket.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/*
	public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999); // Connect to server on localhost and port 9999

            // Get the input and output streams for byte-oriented communication
            OutputStream outToServer = socket.getOutputStream();
            InputStream inFromServer = socket.getInputStream();

            // Send bytes to the server
            //byte[] bytesToSend = "Hello, Server!".getBytes(); // Convert string to bytes
            
            // Four integers to be sent
            int number1 = 3000;
            int number2 = 4150;
            int number3 = 134;
            int number4 = 1025;

            // Convert integers to byte array
            byte[] bytesToSend = intArrayToBytes(new int[]{number1, number2, number3, number4});

            outToServer.write(bytesToSend);
            outToServer.flush(); // Flush the output stream

            // Receive bytes from the server
            byte[] receivedBytes = new byte[4*4]; // Adjust buffer size as needed
            int bytesRead = inFromServer.read(receivedBytes); // Read bytes into the buffer
             if (bytesRead == Integer.BYTES * 4) {
                // Convert bytes back to individual integers
                int[] receivedIntegers = bytesToIntArray(receivedBytes);

                // Print received integers
                System.out.print("Received integers: ");
                for (int num : receivedIntegers) {
                    System.out.print(num + " ");
                }
                System.out.println();
            } else {
                System.out.println("Insufficient bytes received for 4 integers.");
            }

            // Close the socket
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    // Utility method to convert an array of integers to bytes
    private byte[] intArrayToBytes(int[] numbers) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(byteStream);

        try {
            for (int number : numbers) {
                dataStream.writeInt(number);
            }
            dataStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return byteStream.toByteArray();
    }

        // Convert byte array to an array of integers
    private int[] bytesToIntArray(byte[] bytes) {
        int[] integers = new int[bytes.length / Integer.BYTES];
        for (int i = 0; i < bytes.length; i += Integer.BYTES) {
            integers[i / Integer.BYTES] = byteArrayToInt(bytes, i);
        }
        return integers;
    }

    // Convert 4 bytes starting from the offset to an integer
    private int byteArrayToInt(byte[] bytes, int offset) {
        return (bytes[offset] << 24) | ((bytes[offset + 1] & 0xFF) << 16) | ((bytes[offset + 2] & 0xFF) << 8) | (bytes[offset + 3] & 0xFF);
    }
}