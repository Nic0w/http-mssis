package fr.esiea.mssis.http.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;

public class HttpServer_09_v2 implements Runnable {
	
	private static final String HELLO_WORLD = "<html>Hello world !</html>";
	
	
	private final Socket clientSocket;

	public HttpServer_09_v2(Socket client) {
		
		
		this.clientSocket = client;
	}

	
	private static void sendFile(String filename, OutputStreamWriter writer) throws IOException {
		
		FileReader fileReader = new FileReader(filename);
		
		char buffer[] = new char[1024];
		
		int charRead = -1;
		
		while((charRead = fileReader.read(buffer)) != -1) {
		
			writer.write(buffer);
			
			System.out.println("read '"+ new String(buffer) +"'");
			System.out.println("read '"+ charRead +"'");
		}
			

		writer.flush();
		
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
			//writer.println("Content-Length: " + 125);
			writer.println();
			
			sendFile("index.html", new OutputStreamWriter(this.clientSocket.getOutputStream()));
			
			//writer.println();
			
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
