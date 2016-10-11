package server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import game2016.Player;

public class PlayerService implements Runnable {
	private Socket s;
	private Scanner in;
	private PrintWriter out;

	
	public PlayerService(Socket aSocket) {
		this.s = aSocket;
	}

	@Override
	public void run() {
		try {
			try {
				in = new Scanner(s.getInputStream());
				out = new PrintWriter(s.getOutputStream());
				doService();
			} finally {
//				s.close();
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	public void doService() throws IOException {
		while (true) {
			if (!in.hasNext()) {
				return;
			}
			String command = in.next();
			if (command.equals("QUIT")) {
				return;
			} else
				executeCommand(command);
		}
	}
	
	public void executeCommand(String command) throws IOException {
		if(command.equals("addPlayer")) {
			String newPlayerName = in.next();
			int xPos = in.nextInt();
			int yPos = in.nextInt();
			String direction = in.next();
			sendToAllClients("addPlayer " + newPlayerName + " " + xPos + " " +  yPos + " " + direction);
		} else if(command.equals("playerMoved")) {
			String player = in.next();
			int xPos = in.nextInt();
			int yPos = in.nextInt();
			String direction = in.next();
			sendToAllClients("playerMoves " + player + " " + xPos + " " + yPos + " " + direction);
		} 
	}
	
	public void sendToSingleSocket(String response) {
		out.write(response + "\n");
		out.flush();
	}
	
	private void sendToAllClients(String response) throws IOException {
		if(GameServer.getSockets().size() > 0) {
			for (Socket socket : GameServer.getSockets()) {
					out = new PrintWriter(socket.getOutputStream());
					out.write(response + "\n");
					out.flush();	
			}	
		}
	}
}
