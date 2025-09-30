package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import businessLogic.BLFacade;

import domain.*;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;


public class LoginGUI extends JFrame {
	private JTextField textField;
	private JTextField textField2;
	protected JLabel jLabelSelectOption;
	private User us;
	
	private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
		
	public LoginGUI() {
		this.setSize(new Dimension(700, 500));
		
		getContentPane().setForeground(new Color(255, 0, 128));
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel();
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Email"));
		lblNewLabel.setBounds(27, 95, 120, 30);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel();lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Login"));
		lblNewLabel_1.setBounds(0, 0, 686, 43);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel();
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1_1.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));
		lblNewLabel_1_1.setBounds(27, 152, 120, 30);
		getContentPane().add(lblNewLabel_1_1);
		
		JLabel lblerror = new JLabel(); 
		lblerror.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror.setVisible(false);
		lblerror.setForeground(new Color(255, 0, 0));
		lblerror.setLocation(208, 235);
		lblerror.setSize(262, 20);
		lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.error"));
		
		getContentPane().add(lblerror);
		
		
		JButton btnLogin = new JButton();
		btnLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Login"));
		btnLogin.setBackground(new Color(240, 240, 240));
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnLogin.setBounds(248, 276, 196, 43);
		getContentPane().add(btnLogin);
		btnLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				us = MainDriverGUI.getBusinessLogic().login(textField.getText(),textField2.getText());
				if(us != null) {
					JFrame a;
					if(us instanceof Driver) {
						a = new MainDriverGUI((Driver)us);
					}else if (us instanceof Passenger){
						a = new MainPassengerGUI((Passenger)us);
					}else {
						a = new MainAdminGUI((Admin)us);
					}
					a.setVisible(true);
					dispose();
				}else {
					lblerror.setVisible(true);
				}
			}
		});
		
		
		
		JButton btnSingUp = new JButton("SING UP");
		btnSingUp.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.SingUp"));
		btnSingUp.setBounds(10, 397, 142, 40);
		getContentPane().add(btnSingUp);
		btnSingUp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SignUpGUI();
				a.setVisible(true);
				dispose();
			} 
		});
		
		JButton btnQueryRides = new JButton("QUERY RIDES");
		btnQueryRides.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		btnQueryRides.setBounds(534, 397, 142, 40);
		getContentPane().add(btnQueryRides);
		btnQueryRides.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI(0);
				a.setVisible(true);
				dispose();
			}
		});
		
		textField = new JTextField();
		textField.setBounds(270, 98, 142, 30);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField2 = new JTextField();
		textField2.setBounds(270, 155, 142, 30);
		getContentPane().add(textField2);
		textField2.setColumns(10);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
}
