package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class PlayerClientTest {
	public static void main(String[] args) throws UnknownHostException, IOException {
		Socket s = new Socket("localhost", 6789);
		s.setKeepAlive(true);
		
		InputStream instream = s.getInputStream();
		OutputStream outstream = s.getOutputStream();
		
		Scanner in = new Scanner(instream);
		PrintWriter out = new PrintWriter(outstream);
		
		String command = "addPlayer Mikkel";
		System.out.println("Sending command");
		out.print(command);
		out.flush();
		
	}
}
