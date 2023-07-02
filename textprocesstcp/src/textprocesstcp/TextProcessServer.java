package textprocesstcp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class is the Server application
 * This class will receive the text from the user and translate it
 *
 * 
 * @author wengchuan
 *
 */
public class TextProcessServer {
    public static void main(String[] args) throws IOException {
        // Launch the server frame
        ServerFrame serverFrame = new ServerFrame();
        serverFrame.setVisible(true);
        
        // Binding to a port
        int portNo = 8980;
        ServerSocket serverSocket = new ServerSocket(portNo);

        TextProcessServer tpServer = new TextProcessServer();
        
        // Counter to keep track of the number of requested connections
        int totalRequest = 0;
        
        // Infinite loop for server
        while (true) {
            // Accept client request for connection
            Socket clientSocket = serverSocket.accept();

            // Getting client text
        
            String inputText;
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            inputText = inputStream.readUTF();

            // Calculate text length
            int length = tpServer.CountText(inputText);

            // Update the server frame with the calculated text length
            serverFrame.updateRequestStatus("Data sent to the client: " + length);

            // Create stream to write data on the network
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            

            // Send text length back to the client
            outputStream.writeBytes(Integer.toString(length));

            // Flush the output stream to ensure all data is sent
            outputStream.flush();

            // Close the socket
            clientSocket.close();
            
            // Update the request status
            serverFrame.updateRequestStatus("Data sent to the client: " + length);
            serverFrame.updateRequestStatus("Accepted connection from the client. Total requests = " + ++totalRequest);
          
        }
    }

    
    
    /**
     * This method will count the text 
     * 
     * @param text
     * @return
     */
    public int CountText(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }

        // Remove leading and trailing white spaces
        String trimmedText = text.trim();

        // Return the length of the trimmed text
        return trimmedText.length();
    }
}
