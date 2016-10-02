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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FTclient {
	private static String[] fileList;
	private static String fileDirectory = getDirectoryName();

	public static void main(String[] args) {
		final Map<String, Runnable> ACTIONS = new HashMap<>();
		Integer numArgs = args.length;

		// Available commands and their respective actions
		ACTIONS.put("-u", FTclient::upload);
		ACTIONS.put("--upload", FTclient::upload);
		ACTIONS.put("-d", FTclient::download);
		ACTIONS.put("--download", FTclient::download);
		ACTIONS.put("-h", FTclient::help);
		ACTIONS.put("--help", FTclient::help);
		
		if (numArgs > 0) {
			String action = args[0];
			fileList = Arrays.copyOfRange(args, 1, numArgs);
			ACTIONS.get(action).run();
		} else {
			// Fire up the GUI!
		}
	}

	private static void upload() {
		if (fileList.length == 0) {
			System.out.println("No file names provided. Aborting upload.");
			System.exit(1);
		}
		
		for (String fileName : fileList) {
			String fileContents = getFileContents(fileName);
			System.out.println("Uploading: " + fileName);
			
			try {
				Socket socket = new Socket("localhost", 3000);
				BufferedReader socketReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));
				
				PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true);
				socketWriter.println(fileContents); // Send file to server

				String response = socketReader.readLine(); // Receive response
				System.out.println("Server response: " + response);
				
				socket.close();
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	} 
	
	private static String getDirectoryName() {
		FTclient client = new FTclient();
		Package pack = client.getClass().getPackage();
		String packName = pack.getName();
		return packName + '/';
	}
	
	private static String getFileContents(String fileName) {
		String fileContents = null;

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
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Encountered an error reading file: " + fileName);
			e.printStackTrace();
		}
		
		return fileContents.toString();
	}
	
	private static void download() {
		System.out.println("I am downloading!");
	}
	
	private static void help() {
		System.out.print(
				"\nFile Transfer Client Commands:\n"
				+ "Usage: java assignment_1/FTclient [command] [file1 file2 ...]\n"
				+ "upload: --upload | -u\n"
				+ "download: --download | -d\n"
				+ "help: --help | -h\n"
			);
	}
}
