package texttransalationapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientTranslationApplication {
	public static void main(String[] args) {
		System.out.println("Select the Text To Translate:");
		System.out.println("1. Good morning");
		System.out.println("2. Good night");
		System.out.println("3. How are you? ");
		System.out.println("4. Thank you");
		System.out.println("5. Goodbye");
		System.out.println("6. What’s up?");
		System.out.println("Please enter 1-6");
		
		Scanner scanner = new Scanner(System.in);
		String inputText = scanner.nextLine();
		
		System.out.println("Select the Language To Translate:");
		System.out.println("1. Bahasa Malaysia");
		System.out.println("2. Arabic ");
		System.out.println("3. Korean ");
		System.out.println("Please enter 1-3");
		String language = scanner.nextLine();
		
		String text="",translateLanguage;
		
		switch (language) {
		case "1": {
			translateLanguage="malay";
			break;
			
		}
		case "2": {
			translateLanguage="arabic";
			break;
			
		}
		case "3": {
			translateLanguage="korean";
			break;
			
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + language);
			
		}
		
		switch (inputText) {
		case "1": {
			text = "Good morning";
			break;
			
		}
		case "2": {
			text = "Good night";
			break;
			
		}
		case "3": {
			text = "How are you?";
			break;
			
		}
		case "4": {
			text = "Thank you";
			break;
			
		}
		case "5": {
			text = "Goodbye";
			break;
			
		}
		case "6": {
			text = "What’s up?";
			break;
			
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + inputText);
		}
		
		
		
		
		try {
			// Connect to the server at localhost, port 4228
			Socket socket = new Socket(InetAddress.getLocalHost(), 4228);
			 // Create streams for communication
	        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

	        // Send the input text to the server
	        outputStream.writeUTF(text);
	        outputStream.writeUTF(translateLanguage);
	        outputStream.flush();
	    	
			// Create input stream
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// Read from the network and display the current date
			String translatedText = bufferedReader.readLine();
			System.out.println(new String(translatedText.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
			// Close everything
			bufferedReader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
