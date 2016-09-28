/*	Name: Peter Mooney
	Course: 563
	Assignment: #!
*/

package assignment_1;

import java.util.HashMap;
import java.util.Map;

public class FTclient {
	public static void main(String[] args) throws Exception{
		final Map<String, Runnable> ACTIONS = new HashMap<>();
		
		// Available actions
		ACTIONS.put("-u", FTclient::upload);
		ACTIONS.put("--upload", FTclient::upload);
		ACTIONS.put("-d", FTclient::download);
		ACTIONS.put("--download", FTclient::download);
		ACTIONS.put("-h", FTclient::help);
		ACTIONS.put("--help", FTclient::help);
		
		if (args.length > 0) {
			String action = args[0];
			ACTIONS.get(action).run();
		} else {
			// FIRE UP THE GUI!
		}
	}
	
	public static void upload() {
		System.out.println("I am uploading!");
	}
	
	public static void download() {
		System.out.println("I am downloading!");
	}
	
	public static void help() {
		System.out.println("I am helping!");
	}

}
