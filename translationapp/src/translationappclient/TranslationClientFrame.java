package translationappclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class TranslationClientFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	// Store the selected language
	private String selectedLanguage="";

	// Private frame components
	private JLabel lblEnterText;
	private JLabel lblStatusValue;
	private JLabel lblTranslatedText;
	private JTextField tbInput;
	private JButton btnSend;
	private DataOutputStream outputStream;

	// Private attributes for frame size
	private int width = 700;
	private int height = 200;

	public Socket socket;

	/**
	 * The constructor that initialize and organize the Swing components on the
	 * frame.
	 */
	public TranslationClientFrame() {

		// Default frame setting
		this.setLayout(new BorderLayout());
		this.setTitle("TCP Application: Client Side");
		this.setSize(width, height);

		// Center the frame on the screen
		this.setLocationRelativeTo(null);

		// Initialize default value for label
		lblEnterText = new JLabel("Enter the Text: ");
		lblTranslatedText = new JLabel("-");
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
	 * @param translatedText: Server's date
	 */
	public void updateTranslatedText(String translatedText) {

		this.lblTranslatedText.setText(translatedText);

	}

	/**
	 * This method update the status of connection to the server.
	 * 
	 * @param connStatus: Connection status (true/false)
	 */
	public void updateConnectionStatus(boolean connStatus) {

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
	private JPanel enterTranslateTextPanel(Font font) {

		// Create component
		JPanel panel = new JPanel();
		JLabel lblEnterText = new JLabel("Enter the Text: ");
		JRadioButton rbLanguageMalay = new JRadioButton("Malay");
		JRadioButton rbLanguageArabic = new JRadioButton("Arabic");
		JRadioButton rbLanguageKorean = new JRadioButton("Korean");
		// add radio button into button group
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(rbLanguageMalay);
		buttonGroup.add(rbLanguageArabic);
		buttonGroup.add(rbLanguageKorean);
		// add radio button event handler that will return the select language in string
		rbLanguageMalay.addActionListener(e -> handleLanguageSelection("malay"));
		rbLanguageArabic.addActionListener(e -> handleLanguageSelection("arabic"));
		rbLanguageKorean.addActionListener(e -> handleLanguageSelection("korean"));

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
		panel.add(rbLanguageMalay);
		panel.add(rbLanguageArabic);
		panel.add(rbLanguageKorean);

		return panel;

	}

	/**
	 * This method creates and arrange Swing components to date retrieve from the
	 * server.
	 *
	 * @param font
	 * @return Swing components organized in panel
	 */
	private JPanel getServerTranslatedLanguagePanel(Font font) {

		// Create component to display date retrieve from the server
		JPanel panel = new JPanel();
		JLabel lblLength = new JLabel("The translated Text is: ");
		Font koreanFont = new Font("Malgun Gothic", Font.PLAIN, 12); // Replace with an appropriate Korean font
		Font arabicFont = new Font("Arabic", Font.PLAIN, 12);
		
		// Set the font based on the language
		if (selectedLanguage.equals("Korean")) {
			lblTranslatedText.setFont(koreanFont);
		}
		else if(selectedLanguage.equals("arabic")) {
			lblTranslatedText.setFont(arabicFont);
		}
		// Style the component
		lblLength.setFont(font);
		
		lblLength.setBackground(Color.WHITE);
		lblLength.setOpaque(true);
		lblTranslatedText.setBackground(Color.WHITE);
		lblTranslatedText.setOpaque(true);

		// Organize components into panel
		panel.add(lblLength);
		panel.add(lblTranslatedText);

		return panel;
	}

	/**
	 * This method arrange the Swing components on the frame.
	 */
	private void loadComponent() {

		// Get font
		Font font = this.getFontStyle();

		// Get server status's panel and add to frame
		JPanel northPanel = this.enterTranslateTextPanel(font);
		this.add(northPanel, BorderLayout.NORTH);

		// Get server date's panel and add to frame
		JPanel center = getServerTranslatedLanguagePanel(font);
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

		Font font = new Font("Malgun Gothic", Font.PLAIN, 30);

		return font;

	}

	// This method will send the string to the server
	public void passData(String text) {
		try {
			
			// Send the input text and selected language to the server
			outputStream.writeUTF(text);
			outputStream.writeUTF(selectedLanguage);
			//outputStream.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// get the socket from main function
	public void setSocket(Socket socket) {
		this.socket = socket;
		// Create streams for communication
		try {
			 outputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
	}

	// set the selected language
	public void handleLanguageSelection(String language) {
		selectedLanguage = language; // Update the selected language

	}

}
