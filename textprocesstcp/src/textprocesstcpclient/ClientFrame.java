package textprocesstcpclient;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.net.Socket;
import java.net.InetAddress;
/**
 * This class represent the window for the client side TCP application. It
 * display the date retrieve from the server.
 * 
 * @author emalianakasmuri
 *
 */
public class ClientFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// Private frame components
	private JLabel lblEnterText;
	private JLabel lblStatusValue;
	private JLabel lblTextLength;
	private JTextField tbInput;
	private JButton btnSend;

	// Private attributes for frame size
	private int width = 700;
	private int height = 200;
	
	public Socket socket;

	/**
	 * The constructor that initialize and organize the Swing components on the
	 * frame.
	 */
	public ClientFrame() {

		// Default frame setting
		this.setLayout(new BorderLayout());
		this.setTitle("TCP Application: Client Side");
		this.setSize(width, height);

		// Center the frame on the screen
		this.setLocationRelativeTo(null);

		// Initialize default value for label
		lblEnterText = new JLabel("Enter the Text: ");
		lblTextLength = new JLabel("-");
		lblStatusValue = new JLabel("-");
		tbInput = new JTextField();
		
		btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String inputData = tbInput.getText();
		        // Pass the inputData to the main class or perform any other actions
		        passData(inputData);
		    }
		});
		
		// Must close on X
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Organize components
		loadComponent();

	}

	/**
	 * This method update the value of date on the frame
	 * 
	 * @param serverDate: Server's date
	 */
	public void updateServerDate(String serverDate) {

		this.lblTextLength.setText(serverDate);

	}

	/**
	 * This method update the status of connection to the server.
	 * 
	 * @param connStatus: Connection status (true/false)
	 */
	public void updateConnectionStatus (boolean connStatus) {
		

		// Default status. Assuming for the worst case scenario.
		String status = "No connection to server.";
		
		// Validate status of connection
		if (connStatus)
			status = "Connection has established.";
		
				
		// Update the status on frame
		this.lblStatusValue.setText(status);
	}
	

	/**
	 * This method creates and arrange Swing components to display status of
	 * client's connection to the server.
	 * 
	 * @param font
	 * @return Swing components organized in a panel.
	 */
	private JPanel getConnectionStatusPanel(Font font) {

		// Create component
		JPanel panel = new JPanel();
		JLabel lblEnterText = new JLabel("Enter the Text: ");

		// Style the component
		lblEnterText.setFont(font);
		tbInput.setFont(font);
		lblEnterText.setBackground(Color.WHITE);
		lblEnterText.setOpaque(true);
		tbInput.setOpaque(true);
		tbInput.setColumns(20);

		// Organize components into panel
		panel.add(lblEnterText);
		panel.add(tbInput);

		return panel;

	}

	/**
	 * This method creates and arrange Swing components to date retrieve from the
	 * server.
	 *
	 * @param font
	 * @return Swing components organized in panel
	 */
	private JPanel getServerLengthPanel(Font font) {

		// Create component to display date retrieve from the server
		JPanel panel = new JPanel();
		JLabel lblLength = new JLabel("The text length is: ");

		// Style the component
		lblLength.setFont(font);
		lblTextLength.setFont(font);
		lblLength.setBackground(Color.WHITE);
		lblLength.setOpaque(true);
		lblTextLength.setBackground(Color.WHITE);
		lblTextLength.setOpaque(true);

		// Organize components into panel
		panel.add(lblLength);
		panel.add(lblTextLength);

		return panel;
	}

	/**
	 * This method arrange the Swing components on the frame.
	 */
	private void loadComponent() {

	    // Get font
	    Font font = this.getFontStyle();

	    // Get server status's panel and add to frame
	    JPanel northPanel = this.getConnectionStatusPanel(font);
	    this.add(northPanel, BorderLayout.NORTH);

	    // Get server date's panel and add to frame
	    JPanel center = getServerLengthPanel(font);
	    this.add(center, BorderLayout.CENTER);

	    // Create JPanel instance for southPanel
	    JPanel southPanel = new JPanel();

	    // Add components to southPanel
	    southPanel.add(btnSend);
	    southPanel.add(lblStatusValue);

	    // Add southPanel to frame
	    this.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * This method define a font to a generic style.
	 * 
	 * @return font object
	 */
	private Font getFontStyle() {

		Font font = new Font("Serif", Font.PLAIN, 30);

		return font;

	}
	
	/**
	 * This method will send the string to the server 
	 * @param text
	 */
	public void passData(String text) {
	    try {
	        // Create streams for communication
	        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

	        // Send the input text to the server
	        outputStream.writeUTF(text);
	        outputStream.flush();

	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
	/**
	 * get the socket from main function
	 * 
	 * @param socket
	 */
	public void setSocket(Socket socket) {
		this.socket=socket;
	}
	
}	
