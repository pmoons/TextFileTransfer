/*	Name: Peter Mooney
	Course: 563
	Assignment: #1
*/

package assignment_1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FTserver {
	private static final int PORT = 3000;
	private static ServerSocket serverSocket;

	public static void main(String[] args) {
		System.out.println("Listening for connections on port " + PORT);

		while (true) {
			try {
				serverSocket = new ServerSocket(PORT);
				Socket socket = serverSocket.accept();

				System.out.println("Client connected from " + socket.getInetAddress());

				FTthread thread = new FTthread(socket);
				thread.start();
				
				serverSocket.close();
			} catch (IOException e) {
				System.out.println("Error starting server on port: " + PORT);
				e.printStackTrace();
			}
		}
	}
}
