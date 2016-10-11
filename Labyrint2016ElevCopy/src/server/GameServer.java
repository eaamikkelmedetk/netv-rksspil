package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class GameServer {
	private static ArrayList<Socket> sockets;
	public static void main(String[] args) throws IOException {
		sockets = new ArrayList<Socket>();
		ServerSocket server = new ServerSocket(6789);
		System.out.println("Waiting for clients to connect . . . ");
		
		while (true) {
			Socket s = server.accept();
			sockets.add(s);
			System.out.println("Client connected.");
			PlayerService service = new PlayerService(s);
			Thread t = new Thread(service);
			t.start();
		}
	}
	
	public static void addSocket(Socket s) {
		sockets.add(s);
	}
	
	public static ArrayList<Socket> getSockets() {
		return new ArrayList<Socket>(sockets);
	}
	
}
