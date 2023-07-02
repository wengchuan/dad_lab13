package textprocesstcpclient;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class is the client side application
 * This class will send the text to the server
 * This class will receive the number of character from server
 * @author wengchuan
 *
 */
public class TextProcessClient {

    public static void main(String[] args) throws UnknownHostException, IOException {

        // Launch client-side frame
        ClientFrame clientFrame = new ClientFrame();
        clientFrame.setVisible(true);
        

        // Connect to the server @ localhost, port 8980
        Socket socket = new Socket(InetAddress.getLocalHost(), 8980);
        clientFrame.setSocket(socket);
        // Update the status of the connection
        clientFrame.updateConnectionStatus(socket.isConnected());

    	// Read from network
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		
		// Display the length date
		String textLength = bufferedReader.readLine();
		clientFrame.updateServerDate(textLength);
       
		
		// Close everything
		bufferedReader.close();
		
    }
}
