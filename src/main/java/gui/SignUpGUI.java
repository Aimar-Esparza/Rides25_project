package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import businessLogic.BLFacade;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JRadioButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ButtonGroup;
import domain.*;
import javax.swing.SwingConstants;


public class SignUpGUI extends JFrame {
	private JTextField temail;
	private JTextField tuser;
	private JTextField tpassword;
	protected JLabel jLabelSelectOption;
	private User us;
	
	private static BLFacade appFacadeInterface;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
		
	public SignUpGUI() {
		this.setSize(new Dimension(700, 500));
		
		getContentPane().setForeground(new Color(255, 0, 128));
		
		
		getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel();
		lblLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.SingUp"));
		lblLogin.setBounds(0, 0, 686, 40);
		getContentPane().add(lblLogin);
		
		JLabel lblUssername = new JLabel();
		lblUssername.setText(ResourceBundle.getBundle("Etiquetas").getString("SingUpGUI.Username"));
		lblUssername.setBounds(161, 133, 100, 20);
		getContentPane().add(lblUssername);
		
		JLabel lblPassword = new JLabel();
		lblPassword.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Password"));
		lblPassword.setBounds(161, 187, 100, 20);
		getContentPane().add(lblPassword);	

		JRadioButton rdbtnPassenger = new JRadioButton();
		rdbtnPassenger.setText(ResourceBundle.getBundle("Etiquetas").getString("SingUpGUI.Passenger"));
		rdbtnPassenger.setBounds(302, 238, 100, 20);
		buttonGroup.add(rdbtnPassenger);
		
		JRadioButton rdbtnDriver = new JRadioButton();
		rdbtnDriver.setText(ResourceBundle.getBundle("Etiquetas").getString("SingUpGUI.Driver"));
		rdbtnDriver.setBounds(419, 238, 100, 20);
		buttonGroup.add(rdbtnDriver);
		
		JLabel lblerror = new JLabel(); 
		lblerror.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror.setVisible(false);
		lblerror.setForeground(new Color(255, 0, 0));
		lblerror.setBounds(248, 300, 196, 13);
		lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.error"));
		
		getContentPane().add(lblerror);
		
		JButton btnSingUp = new JButton();
		btnSingUp.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.SingUp"));
		btnSingUp.setBounds(248, 323, 196, 43);
		btnSingUp.setBackground(new Color(240, 240, 240));
		btnSingUp.setForeground(new Color(0, 0, 0));
		btnSingUp.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnSingUp.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if (rdbtnDriver.isSelected()) {
					us = MainDriverGUI.getBusinessLogic().createUser(temail.getText(),tuser.getText(),tpassword.getText(),0,true);
				}else {
					us = MainDriverGUI.getBusinessLogic().createUser(temail.getText(),tuser.getText(),tpassword.getText(),0,false);
				}
				if (us != null && rdbtnDriver.isSelected()) {
					JFrame a = new MainDriverGUI((Driver)us);
					a.setVisible(true);
					dispose();
				}else if(us != null){
					JFrame a = new MainPassengerGUI((Passenger)us);
					a.setVisible(true);
					dispose();
				}else {
					lblerror.setVisible(true);
				}
			}
		});
		
		JButton btnLogin = new JButton("LOGIN");
		btnLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Login"));
		btnLogin.setBounds(10, 397, 142, 40);
		btnLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new LoginGUI();
				a.setVisible(true);
				dispose();
			}
		});
		
		JButton btnQueryRides = new JButton("QUERY RIDES");
		btnQueryRides.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		btnQueryRides.setBounds(534, 397, 142, 40);
		btnQueryRides.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI(0);
				a.setVisible(true);
				dispose();
			}
		});
		
		temail = new JTextField();
		temail.setBounds(337, 78, 137, 20);
		temail.setColumns(10);
		
		tuser = new JTextField();
		tuser.setBounds(337, 133, 137, 20);
		tuser.setColumns(10);
		
		tpassword = new JTextField();
		tpassword.setBounds(337, 187, 137, 20);
		tpassword.setColumns(10);
		
		
		getContentPane().add(rdbtnDriver);
		getContentPane().add(rdbtnPassenger);
		getContentPane().add(temail);
		getContentPane().add(tuser);
		getContentPane().add(tpassword);
		getContentPane().add(btnQueryRides);
		getContentPane().add(btnLogin);
		getContentPane().add(btnSingUp);
		
		JLabel lblMail = new JLabel();
		lblMail.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.Email"));
		lblMail.setBounds(161, 78, 100, 20);
		getContentPane().add(lblMail);
		
		JLabel lblType = new JLabel();
		lblType.setText(ResourceBundle.getBundle("Etiquetas").getString("SingUpGUI.Type"));
		lblType.setBounds(161, 238, 100, 20);
		getContentPane().add(lblType);
				
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
		}
}
