package fr.esiea.mssis.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HttpServer_09_v0 implements Runnable {
	
	
	private final Socket clientSocket;

	public HttpServer_09_v0(Socket client) {
		
		
		this.clientSocket = client;
	}

	@Override
	public void run() {
		
		BufferedReader reader;
		PrintStream writer;
		
		try {
			reader = new BufferedReader(
					new InputStreamReader(this.clientSocket.getInputStream()));
			
			
			writer = new PrintStream(this.clientSocket.getOutputStream());
			
		} catch (IOException e) {
		
			e.printStackTrace();
			
			return;
		}
		
		
		String lineRead = null;
		
		try {
			
			while(!(lineRead = reader.readLine()).contentEquals("")) {
				
				System.out.println(lineRead);
			}
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			
		}
		finally {
			
			try {
				
				this.clientSocket.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
				
			}
			
		}
		
		
	}

	
	
}
