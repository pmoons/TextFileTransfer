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
	private static String fileDirectory = getDirectoryName();
	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;

	public FTthread(Socket socket) {
		try {
			this.socket = socket;
			this.reader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			this.writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		try {						
			String clientRequest = reader.readLine();
			String method = clientRequest.substring(0, 5).toUpperCase();
			String body = clientRequest.substring(5);
			
			switch(method) {
				case "POST ":
					System.out.println("POST: " + body);
					writer.println("201 CREATED: " + body );
					break;
				case "GET  ":
					System.out.println("GET: " + body);
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
	
	private String getFileContents(String fileName) {
		String fileContents = "";

		try {
			FileReader fileReader = new FileReader(fileDirectory + fileName);
			int c;

			while ((c = fileReader.read()) != -1) {
				fileContents += (char)c; // c is an integer representation of a char, need to cast it.
			}
			
			fileReader.close();
		} catch (FileNotFoundException e) {
			writer.println("404 NOT FOUND: " + fileName);
		} catch (IOException e) {
			writer.println("500 INTERNAL SERVER ERROR");
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
