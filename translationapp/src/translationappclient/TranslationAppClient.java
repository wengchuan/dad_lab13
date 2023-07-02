package translationappclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class TranslationAppClient {
  public static void main(String[] args) throws UnknownHostException, IOException {

    // Launch client-side frame
    TranslationClientFrame clientFrame = new TranslationClientFrame();
    clientFrame.setVisible(true);

    // Connect to the server @ localhost, port 4228
    Socket socket = new Socket(InetAddress.getLocalHost(), 4221);
    clientFrame.setSocket(socket);
    // Update the status of the connection
    clientFrame.updateConnectionStatus(socket.isConnected());

    // Read from network
    DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
    while (true) {
      // Display the translated text
      String translatedText = dataInputStream.readUTF();
      translatedText = new String(translatedText.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
      clientFrame.updateTranslatedText(translatedText);
      System.out.println(translatedText);
    }
  }
}
