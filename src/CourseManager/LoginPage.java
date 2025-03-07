package CourseManager;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.swing.*;

public class LoginPage implements ActionListener{

	private JFrame frame = new JFrame();
	private JLabel label = new JLabel("CourseManager Login");
	private JButton loginButton = new JButton("Login");
	private JButton signupButton = new JButton("Sign Up");
	private JTextField userIDField = new JTextField();
	private JPasswordField userPasswordField = new JPasswordField();
	private JLabel userIDLabel = new JLabel("userID:");
	private JLabel userPasswordLabel = new JLabel("password:");
	private JLabel messageLabel = new JLabel();
	private HashMap<String,String> logininfo = new HashMap<String,String>();

	LoginPage(HashMap<String,String> loginInfo, int xCoord, int yCoord){

		this.logininfo = loginInfo;

		this.label.setBounds(80,20,400,60);
		this.label.setFont(new Font(null,Font.PLAIN,25));

		this.userIDLabel.setBounds(50,100,75,25);
		this.userPasswordLabel.setBounds(50,150,75,25);

		this.messageLabel.setBounds(125,250,250,35);
		this.messageLabel.setFont(new Font(null,Font.ITALIC,25));

		this.userIDField.setBounds(125,100,200,25);
		this.userPasswordField.setBounds(125,150,200,25);

		this.loginButton.setBounds(225,200,100,25);
		this.loginButton.setFocusable(false);
		this.loginButton.addActionListener(this);

		this.signupButton.setBounds(125,200,100,25);
		this.signupButton.setFocusable(false);
		this.signupButton.addActionListener(this);

		this.frame.add(label);
		this.frame.add(userIDLabel);
		this.frame.add(userPasswordLabel);
		this.frame.add(messageLabel);
		this.frame.add(userIDField);
		this.frame.add(userPasswordField);
		this.frame.add(loginButton);
		this.frame.add(signupButton);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setBounds(xCoord, yCoord, Main.SIDE_SIZE, Main.SIDE_SIZE);
		this.frame.setLayout(null);
		this.frame.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//redirects to signup page
		if(e.getSource()==signupButton) {
			frame.dispose();
			new SignupPage(logininfo, frame.getX(), frame.getY());
		}
		//login validation, checks if user is within the database
		if(e.getSource()==loginButton) {

			String userID = userIDField.getText();
			String password = String.valueOf(userPasswordField.getPassword());

			try {
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/course_manager", "root", "Bellators@612"); //change

				PreparedStatement pst = con.prepareStatement("SELECT student_ID, password FROM student WHERE student_id=? and password=?");
				pst.setString(1, userID);
				pst.setString(2, password);
				ResultSet rs = pst.executeQuery();
				if(rs.next()){
					frame.dispose();
					new WelcomePage(userID); //MADE CHANGES
				}
				else{
					JOptionPane.showMessageDialog(signupButton, "Incorrect Credentials");
				}
			}
			catch (SQLException sqlException){
				sqlException.printStackTrace();
			}

		}
	}
}