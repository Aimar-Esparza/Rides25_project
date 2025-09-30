package gui;

import javax.swing.JFrame;
import javax.swing.JTextField;

import businessLogic.BLFacade;

import domain.*;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;


public class ReclamationAdminGUI extends JFrame {
	protected JLabel jLabelSelectOption;
	private Reclamation reclamation;
	private Ride ride;
	private Driver driver;
	private Passenger passenger;
	DefaultComboBoxModel<String> val = new DefaultComboBoxModel<String>();
	private static BLFacade appFacadeInterface;
	private final ButtonGroup buttonGroup = new ButtonGroup();
		
	public ReclamationAdminGUI(Reclamation r) {
		reclamation = r;
		ride = r.getRide();
		driver = r.getDriver();
		passenger = r.getPassenger();

		
		this.setSize(new Dimension(700, 500));
		
		getContentPane().setForeground(new Color(255, 0, 128));
		getContentPane().setLayout(null);
		
		JLabel lblTittle = new JLabel();lblTittle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTittle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTittle.setText(ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.Reclamation"));
		lblTittle.setBounds(0, 0, 686, 43);
		getContentPane().add(lblTittle);
		
		JLabel lblCommentary = new JLabel();
		lblCommentary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCommentary.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Commentary"));
		lblCommentary.setBounds(27, 282, 120, 30);
		getContentPane().add(lblCommentary);
		
		JTextArea textAreaComment = new JTextArea();
		textAreaComment.setText(r.getComment());
		textAreaComment.setBounds(174, 287, 342, 100);
		getContentPane().add(textAreaComment);
		
		JButton btnCancel = new JButton();
		btnCancel.setText(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.Reject"));
		btnCancel.setBounds(10, 397, 142, 40);
		getContentPane().add(btnCancel);
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				MainDriverGUI.getBusinessLogic().completeReclamation(reclamation.getReclamationId(), passenger.getEmail(), driver.getEmail(), false);
				dispose();
			}
		});
		
		JButton btnSubmmit = new JButton();
		btnSubmmit.setText(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.Accept"));
		btnSubmmit.setBounds(534, 397, 142, 40);
		getContentPane().add(btnSubmmit);
		
		JLabel lblRide = new JLabel();
		lblRide.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeValorationGUI.Ride")); //$NON-NLS-1$ //$NON-NLS-2$
		lblRide.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRide.setBounds(27, 53, 120, 30);
		getContentPane().add(lblRide);
		
		JLabel lblRideInfo = new JLabel();
		lblRideInfo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRideInfo.setText(ride.toString());
		lblRideInfo.setBounds(174, 53, 476, 30);
		getContentPane().add(lblRideInfo);
		
		JLabel lblDriver = new JLabel();
		lblDriver.setText(ResourceBundle.getBundle("Etiquetas").getString("FindRidesGUI.Driver")); 
		lblDriver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDriver.setBounds(27, 119, 120, 30);
		getContentPane().add(lblDriver);
		
		JLabel lblDriverName = new JLabel(); 
		lblDriverName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDriverName.setText(ResourceBundle.getBundle("Etiquetas").getString("SingUpGUI.Username"));
		lblDriverName.setBounds(174, 119, 81, 30);
		getContentPane().add(lblDriverName);
		
		JLabel lblDriverName_1 = new JLabel();
		lblDriverName_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDriverName_1.setText(driver.getUsername());
		lblDriverName_1.setBounds(265, 119, 81, 30);
		getContentPane().add(lblDriverName_1);
		
		JLabel lblDriverValoration = new JLabel();
		lblDriverValoration.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDriverValoration.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Valoration"));
		lblDriverValoration.setBounds(396, 119, 81, 30);
		getContentPane().add(lblDriverValoration);
		
		JLabel lblDriverValoration_1 = new JLabel();
		lblDriverValoration_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDriverValoration_1.setText(Integer.toString((int) driver.getVal()));
		lblDriverValoration_1.setBounds(488, 119, 81, 30);
		getContentPane().add(lblDriverValoration_1);
		
		JLabel lblPassengerValoration_1 = new JLabel();
		lblPassengerValoration_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassengerValoration_1.setText(Integer.toString((int) passenger.getVal()));
		lblPassengerValoration_1.setBounds(488, 204, 81, 30);
		getContentPane().add(lblPassengerValoration_1);
		
		JLabel lblPassengerValoration = new JLabel();
		lblPassengerValoration.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassengerValoration.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Valoration"));
		lblPassengerValoration.setBounds(396, 204, 81, 30);
		getContentPane().add(lblPassengerValoration);
		
		JLabel lblPassengerName_1 = new JLabel();
		lblPassengerName_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassengerName_1.setText(passenger.getUsername());
		lblPassengerName_1.setBounds(265, 204, 81, 30);
		getContentPane().add(lblPassengerName_1);
		
		JLabel lblPassengerName = new JLabel();
		lblPassengerName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPassengerName.setText(ResourceBundle.getBundle("Etiquetas").getString("SingUpGUI.Username"));
		lblPassengerName.setBounds(174, 204, 81, 30);
		getContentPane().add(lblPassengerName);
		
		JLabel lblPassenger = new JLabel();
		lblPassenger.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassenger.setText(ResourceBundle.getBundle("Etiquetas").getString("SingUpGUI.Passenger"));
		lblPassenger.setBounds(27, 204, 120, 30);
		getContentPane().add(lblPassenger);
		btnSubmmit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				MainDriverGUI.getBusinessLogic().completeReclamation(reclamation.getReclamationId(), passenger.getEmail(), driver.getEmail(), true);
				dispose();
			}
		});
	}
}
