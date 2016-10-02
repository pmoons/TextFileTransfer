/*	Name: Peter Mooney
	Course: 563
	Assignment: #!
*/

package assignment_1;

import java.io.IOException;
import java.net.ServerSocket;

public class FTserver {
	private static final int PORT = 3000;

	public static void start() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			FTthread thread = new FTthread(serverSocket);
			thread.start();
		} catch (IOException e) {
			System.out.println("Error starting server on port: " + PORT);
			e.printStackTrace();
		}
		
	}
}
