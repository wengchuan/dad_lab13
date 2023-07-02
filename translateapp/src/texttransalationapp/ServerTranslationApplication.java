package texttransalationapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerTranslationApplication {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		try {
			// Bind Serversocket to a port

			int portNo = 4228;
			serverSocket = new ServerSocket(portNo);
			ServerTranslationApplication serverTranslationApplication = new ServerTranslationApplication(); 

		
			System.out.println("Waiting for request");

			while (true) {
				// Accept client request for connection
				Socket clientSocket = serverSocket.accept();

				// Getting client text
				String inputText;
				String targetLanguage;
				DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
				inputText = inputStream.readUTF();
				targetLanguage = inputStream.readUTF();
				
				String translatedText="";
				System.out.println("Read from client Text:"+inputText+" "+" Language: "+targetLanguage);
				translatedText=serverTranslationApplication.TextTranslate(inputText,targetLanguage);
				
				// Create stream to write data on the network
				DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
				// Send the translated text to the client
				outputStream.writeUTF(new String(translatedText.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1));
				System.out.println("sending to client Text:"+translatedText+" "+" Language: "+targetLanguage);
				// Close the socket
				clientSocket.close();
			}
			// Closing is not necessary because the code is unreachable
		} catch (IOException ioe) {
			if (serverSocket != null)
				serverSocket.close();
			ioe.printStackTrace();
		}

	}

	
	public String TextTranslate(String text,String language) {
		String translatedText="";
		if(language.equals("malay")){
			if(text.equals("Good morning")) {
				translatedText= "Selamat pagi";
			}
			else if(text.equals("Good night")){
				translatedText="Selamat malam";
			}
			else if(text.equals("How are you?")){
				translatedText="Apa khabar?";
			}
			else if(text.equals("Thank you")){
				translatedText="Terima kasih";
			}
			else if(text.equals("Goodbye")){
				translatedText="Selamat tingga";
			}
			else if(text.equals("What’s up?")){
				translatedText="Ada apa?";
			}
			
		}
		else if(language.equals("arabic")){
			if(text.equals("Good morning")) {
				translatedText= " ﺻﺒﺎح اﻟﺨﯿﺮ ";
				}
				else if(text.equals("Good night")){
					translatedText=" طﺎب ﻣﺴﺎؤكا";
				}
				else if(text.equals("How are you?")){
					translatedText="ﻛﯿﻒ ﺣﺎﻟﻚ";
				}
				else if(text.equals("Thank you")){
					translatedText=" ﺷﻜﺮا ﻟﻚ ";
				}
				else if(text.equals("Goodbye")){
					translatedText=" ﻣﻊ اﻟﺴﻼﻣﺔ";
				}
				else if(text.equals("What’s up?")){
					translatedText=" ﻣﺎ أﺧﺒﺎرك";
				}
		}
		else if(language.equals("korean")){
			if(text.equals("Good morning")) {
				translatedText= "좋은 아침";
				}
				else if(text.equals("Good night")){
					translatedText="안녕히 주무세요 ";
				}
				else if(text.equals("How are you?")){
					translatedText="어떻게 지내세요? ";
				}
				else if(text.equals("Thank you")){
					translatedText="감사합니다";
				}
				else if(text.equals("Goodbye")){
					translatedText="안녕 ";
				}
				else if(text.equals("What’s up?")){
					translatedText="뭐야? ";
				}
		}
		
		return translatedText;
		
		
		
	}

}
