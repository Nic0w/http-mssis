package fr.esiea.mssis.http.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

	public Main() {
		
		
		
	}

	public static void main(String[] args) {
		
		String url = args[0];
		
		
		Pattern urlPattern = Pattern.compile("http://(([a-z]+)+\\.)?(([a-z-]+)\\.([a-z]{2,3}))(:([0-9]+))?(/(([a-z-]+/)*)([a-z\\.]+)?)");
		
		
		Matcher urlMatcher = urlPattern.matcher(url);
		

		if(!urlMatcher.matches()) {
			
			throw new IllegalArgumentException("Bad URL !");
		}
		
		String host = urlMatcher.group(3);
		int port = urlMatcher.group(7) == null ? 80 : Integer.parseInt(urlMatcher.group(7));
		String uri  = urlMatcher.group(8);
		String filename  = urlMatcher.group(11) == null ? "index.html" : urlMatcher.group(11);
	
		
		Socket httpSocket;
		
		try {
			
			httpSocket = new Socket(InetAddress.getByName(host), port);
			
			
			PrintStream socketOut = new PrintStream(httpSocket.getOutputStream());
			
			
			socketOut.
				format("GET %s HTTP/0.9", uri).
				format("Host: %s", host);
			
			socketOut.flush();
			
			
			File outFile = new File(filename);
			
			outFile.createNewFile();
			
			FileOutputStream fileOutStream = new FileOutputStream(outFile);
			
			
			InputStream socketInput = httpSocket.getInputStream();
			
			
			byte buffer[1024];
			
			
			
		} catch (UnknownHostException e) {
	
			e.printStackTrace();
			
			return;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return;
		}
		
		
		
		
	}

}
