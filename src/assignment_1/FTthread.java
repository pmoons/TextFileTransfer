/*	Name: Peter Mooney
	Course: 563
	Assignment: #!
*/

package assignment_1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class FTthread extends Thread {
	private ServerSocket serverSocket;
	private String action;	
	private static String fileDirectory = getDirectoryName();


	public FTthread(ServerSocket socket, String action) {
		this.serverSocket = socket;
		this.action = action;
	}
	
	public void run() {
		try {
			Socket socket = this.serverSocket.accept();
						
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			
			switch(action) {
				case "upload":
					getFile(reader, writer);
					break;
				case "download":
					sendFile(reader, writer);
					break;
				default:
					System.out.println("Unknown action: " + action);
			}

			socket.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getFile(BufferedReader reader, PrintWriter writer) {
		try {
			String file = reader.readLine(); // Receive file from client
			writer.println("File received: " + file); // Send response back to client
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendFile(BufferedReader reader, PrintWriter writer) {
		try {
			String fileName = reader.readLine(); // Receive fileName
			String fileContents = getFileContents(fileName);
			writer.println("File sent: " + fileContents); // Send response back to client
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String getFileContents(String fileName) {
		String fileContents = "";

		try {
			FileReader fileReader = new FileReader(fileDirectory + fileName);
			int c;

			while ((c = fileReader.read()) != -1) {
				fileContents += (char)c; // c is an integer representation of a char, need to cast it.
			}
			
			fileReader.close();
			System.out.println(fileContents);
		} catch (FileNotFoundException e) {
			System.out.println("Could not find file: " + fileName);
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Encountered an error reading file: " + fileName);
			e.printStackTrace();
		}
		
		return fileContents.toString();
	}
	
	private static String getDirectoryName() {
		FTclient client = new FTclient();
		Package pack = client.getClass().getPackage();
		String packName = pack.getName();
		return packName + '/';
	}
}
