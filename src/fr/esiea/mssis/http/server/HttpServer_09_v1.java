package fr.esiea.mssis.http.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class HttpServer_09_v1 implements Runnable {
	
	private static final String HELLO_WORLD = "<html>Hello world !</html>";
	
	
	private final Socket clientSocket;

	public HttpServer_09_v1(Socket client) {
		
		
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
			
			writer.println("HTTP/0.9 200 OK");
			writer.println("Content-Type: text/html");
			writer.println("Content-Length: " + HELLO_WORLD.length());
			writer.println();
			writer.println(HELLO_WORLD);
			
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
