package fr.esiea.mssis.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main implements Runnable {

	private final int listeningPort;
	
	private boolean isRunning;
	
	public Main(int port) {
		
		
		this.listeningPort = port;
		
	}

	public static void main(String[] args) {
		
		Main httpServer = new Main(8080);
		
		httpServer.run();

	}

	@Override
	public void run() {
		
		ServerSocket listeningSocket = null;
		
		try {
			listeningSocket = new ServerSocket(this.listeningPort);
			
		} catch (IOException e) {

			e.printStackTrace();
			
			return;
		}
		
		System.out.println("Listening on port " + this.listeningPort + " !");
		
		this.isRunning = true;
		
		while(this.isRunning) {
			
			Socket clientSocket;
			
			try {
				
				clientSocket = listeningSocket.accept();
				
				new HttpServer_09_v3(clientSocket).run();
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
		
		
		
	}

}
