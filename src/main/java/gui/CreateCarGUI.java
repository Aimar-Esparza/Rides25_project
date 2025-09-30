package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import businessLogic.BLFacade;

import domain.*;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;


public class CreateCarGUI extends JFrame {
	private JTextField textFieldMatricula;
	private Driver driver;
	protected JLabel jLabelSelectOption;
	private User us;
	
	private static BLFacade appFacadeInterface;
	private JTextField textFieldSeats;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
		
	public CreateCarGUI(Driver d) {
		this.driver = d;
		BLFacade facade = MainDriverGUI.getBusinessLogic();
		this.setSize(new Dimension(700, 500));
		
		getContentPane().setForeground(new Color(255, 0, 128));
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel();lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCarGUI.createCar"));
		lblNewLabel_1.setBounds(0, 0, 686, 43);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabelMatricula = new JLabel();
		lblNewLabelMatricula.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabelMatricula.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCarGUI.Matricula"));
		lblNewLabelMatricula.setBounds(25, 91, 120, 30);
		getContentPane().add(lblNewLabelMatricula);
		
		JLabel lblerror = new JLabel(); 
		lblerror.setHorizontalAlignment(SwingConstants.CENTER);
		lblerror.setVisible(false);
		lblerror.setForeground(new Color(255, 0, 0));
		lblerror.setLocation(208, 235);
		lblerror.setSize(262, 20);
		lblerror.setText(ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.error"));
		
		getContentPane().add(lblerror);
		
		
		JButton btnLogin = new JButton();
		btnLogin.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateCarGUI.Create"));
		btnLogin.setBackground(new Color(240, 240, 240));
		btnLogin.setForeground(new Color(0, 0, 0));
		btnLogin.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnLogin.setBounds(248, 276, 196, 43);
		getContentPane().add(btnLogin);
		btnLogin.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					int inputSeats = Integer.parseInt(textFieldSeats.getText());
					Car c = facade.addCar(textFieldMatricula.getText(), inputSeats, driver.getEmail());
					if(c == null) {
						lblerror.setVisible(true);
					}
				}catch(NumberFormatException ee){
					lblerror.setVisible(true);
				}
			}
		});
		
		
		
		JButton btnBack = new JButton();
		btnBack.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));
		btnBack.setBounds(10, 397, 142, 40);
		getContentPane().add(btnBack);
		btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
		
		//	JButton btnQueryRides = new JButton("QUERY RIDES");
		//btnQueryRides.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		//btnQueryRides.setBounds(534, 397, 142, 40);
		//getContentPane().add(btnQueryRides);
		//btnQueryRides.addActionListener(new java.awt.event.ActionListener() {
			//public void actionPerformed(java.awt.event.ActionEvent e) {
				//JFrame a = new FindRidesGUI(0);
				//a.setVisible(true);
				//dispose();
			//}
		//});
		
		textFieldMatricula = new JTextField();
		textFieldMatricula.setBounds(268, 94, 142, 30);
		getContentPane().add(textFieldMatricula);
		textFieldMatricula.setColumns(10);
		
		JLabel lblSeats = new JLabel();
		lblSeats.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.NumberOfSeats"));
		lblSeats.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSeats.setBounds(25, 154, 120, 30);
		getContentPane().add(lblSeats);
		
		textFieldSeats = new JTextField();
		textFieldSeats.setColumns(10);
		textFieldSeats.setBounds(268, 157, 142, 30);
		getContentPane().add(textFieldSeats);
		
	}
}
