/*	Name: Peter Mooney
	Course: 563
	Assignment: #1
*/

File Transfer Client/Server README

To compile:
- Open code using Eclipse and build.
- Code will be output to /bin directory

To use:
- Navigate to assignment_1/bin
 -> cd assignment_1/bin
 
- Start FTserver on a separate main thread.
 -> java assignment_1/FTserver

- Open a new command prompt

- Start FTclient and pass it an action and applicable files to upload / download
 -> java assignment_1/FTclient -d file1.txt file2.txt file3.txt
 
About:

- Protocol used is loosely based on HTTP, and makes an attempt to emulate the HTTP methods, errors,
and success status codes that go along with it.  More information about these codes can be found here:
https://en.wikipedia.org/wiki/List_of_HTTP_status_codes.

- The HTTP methods supported by this project are GET and POST.  These methods are prepended to the 
request body.  The server will parse the first 5 characters of the request body to determine the action
to take.  If the action is unknown, it will throw a 501 NOT IMPLEMENTED error to the client.

- This project includes 3 sample files (file1.txt, file2.txt, file3.txt) that can be referred to for
uploading and downloading.