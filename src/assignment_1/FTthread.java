/*	Name: Peter Mooney
	Course: 563
	Assignment: #!
*/

package assignment_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FTthread extends Thread {
	private ServerSocket serverSocket;

	public FTthread(ServerSocket socket) {
		this.serverSocket = socket;
	}
	
	public void run() {
		try {
			Socket socket = this.serverSocket.accept();
						
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			String file = reader.readLine(); // Receive file from client
			writer.println("File received: " + file); // Send response back to client
			
			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
