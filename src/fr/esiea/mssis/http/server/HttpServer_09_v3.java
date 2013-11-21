package fr.esiea.mssis.http.server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpServer_09_v3 implements Runnable {
	
	private static final String HELLO_WORLD = "<html>Hello world !</html>";
	
	
	private final Socket clientSocket;

	public HttpServer_09_v3(Socket client) {
		
		
		this.clientSocket = client;
	}

	
	private static byte[] asBytes(String filename) throws IOException {
		
		FileInputStream fileStream = new FileInputStream(filename);
		
		byte buffer[] = new byte[1024];
		
		ByteArrayOutputStream content = new ByteArrayOutputStream();
		
		int bytesRead = -1;
		
		int totalByteRead = 0;
		
		while((bytesRead = fileStream.read(buffer)) != -1) {
		
			content.write(buffer, 0, bytesRead);
			
			totalByteRead += bytesRead;
		}
			
		System.out.println("read "+ totalByteRead +" bytes");
		
		
		return content.toByteArray();
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
		String file = "index.html";
		
		try {
			
			while(!(lineRead = reader.readLine()).contentEquals("")) {
				
				//System.out.println(lineRead);
				
				if(lineRead.startsWith("GET")) {
					
					String requestParts[] = lineRead.split(" ");
					
					file = requestParts[1].substring(1);
					
					System.out.println("User requests file '" + file +"'");
					
					if(file.length() == 0)
						file ="index.html";
				}
				
			}
			
			
			byte content[] = null;
			try {
				
				content = asBytes(file);
				
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
				
				writer.println("HTTP/0.9 404 File not found");
				
				this.clientSocket.close();
				
				return;
			}
			
			Path filePath = FileSystems.getDefault().getPath(file);
			
			String contentType = Files.probeContentType(filePath);
			
			System.out.println(contentType);
			
			writer.println("HTTP/0.9 200 OK");
			writer.println("Content-Type: "+ contentType);
			writer.println("Content-Length: " + content.length);
			writer.println();
			
			writer.write(content);
			
			writer.flush();
			
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
