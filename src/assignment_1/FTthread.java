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
import java.net.Socket;

public class FTthread extends Thread {
	private Socket socket;
	private static String fileDirectory = getDirectoryName();


	public FTthread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {						
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

			String clientRequest = reader.readLine();
			String method = clientRequest.substring(0, 5).toUpperCase();
			String body = clientRequest.substring(5);
			
			switch(method) {
				case "POST ":
					writer.println("201 CREATED: " + body );
					break;
				case "GET  ":
					String fileContents = getFileContents(body);
					writer.println("200 OK: " + fileContents);
					break;
				default:
					writer.println("501 NOT IMPLEMENTED: " + method);
			}

			socket.close();
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
