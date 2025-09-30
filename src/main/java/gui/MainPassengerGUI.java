package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Passenger;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainPassengerGUI extends JFrame {
	
    private static Passenger passenger;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonLogOut = null;
	private JButton jButtonAddFunds = null;
	private JButton jButtonSeeRides = null;
	private JButton jButtonTransaction = null;
	private JButton jButtonRequest = null;
	private JButton jButtonNotifications = null;
	
	private JLabel lblNotifications = null;
	
	public static Passenger getPassenger() {
		return passenger;
	}
	
	public static void setPassenger(Passenger p) {
		passenger = p;
	}
	
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonValoration;
	private JButton jButtonTransaction_2;
	private JButton jButtonDeleteUser;
	
	/**
	 * This is the default constructor
	 */
	public MainPassengerGUI(Passenger p) {
		super();

		passenger=p;
		
		// this.setSize(271, 295);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - passenger :"+passenger.getUsername());
		
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setBounds(116, 0, 481, 50);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		rdbtnNewRadioButton.setLocation(483, 400);
		rdbtnNewRadioButton.setSize(170, 40);
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		rdbtnNewRadioButton_1.setLocation(128, 400);
		rdbtnNewRadioButton_1.setSize(160, 40);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		rdbtnNewRadioButton_2.setLocation(288, 400);
		rdbtnNewRadioButton_2.setSize(170, 40);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);
		
		jButtonSeeRides = new JButton();
		jButtonSeeRides.setBounds(28, 47, 270, 50);
		jButtonSeeRides.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SeeBookings"));
		jButtonSeeRides.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SeeBookingsGUI(passenger);
				a.setVisible(true);
			}
		});
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setBounds(394, 47, 270, 50);
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI(2);
				a.setVisible(true);
			}
		});
		
		jButtonLogOut = new JButton();
		jButtonLogOut.setBounds(394, 332, 270, 50);
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.LogOut"));
		jButtonLogOut.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new LoginGUI();
				a.setVisible(true);
				dispose();
			}
		});
		
		jContentPane = new JPanel();
		jContentPane.setLayout(null);
		jContentPane.add(jLabelSelectOption);
		jContentPane.add(jButtonQueryQueries);
		jContentPane.add(jButtonLogOut);
		jContentPane.add(jButtonSeeRides);
		jContentPane.add(rdbtnNewRadioButton);
		jContentPane.add(rdbtnNewRadioButton_1);
		jContentPane.add(rdbtnNewRadioButton_2);
		
		jButtonAddFunds = new JButton(); 
		jButtonAddFunds.setBounds(394, 117, 270, 50);
		jButtonAddFunds.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AddFunds"));
		jButtonAddFunds.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new PutMoneyGUI(passenger);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonAddFunds);
		setContentPane(jContentPane);
		
		jButtonTransaction = new JButton();
		jButtonTransaction.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Transactions"));
		jButtonTransaction.setBounds(28, 117, 270, 50);
		jButtonTransaction.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SeeTransactionGUI(passenger);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonTransaction);
		
		jButtonValoration = new JButton();
		jButtonValoration.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SeeValoration"));
		jButtonValoration.setBounds(394, 188, 270, 50);
		jButtonValoration.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SeeValorations(passenger);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonValoration);
		
		jButtonTransaction_2 = new JButton();
		jButtonTransaction_2.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRequest"));
		jButtonTransaction_2.setBounds(28, 188, 270, 50);
		jButtonTransaction_2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateRequestGUI(passenger);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonTransaction_2);
		
		jButtonDeleteUser = new JButton();
		jButtonDeleteUser.setBounds(28, 332, 270, 50);
		jButtonDeleteUser.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.DeleteUser"));
		jButtonDeleteUser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int aukera = JOptionPane.showConfirmDialog(jButtonDeleteUser,
						ResourceBundle.getBundle("Etiquetas").getString("MainGUI.DeleteUser"), 
						ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AreYouSure"), 	
						JOptionPane.YES_NO_OPTION);
				if(aukera == JOptionPane.YES_OPTION) {
					MainDriverGUI.getBusinessLogic().deleteUser(passenger.getEmail());
					JFrame a = new LoginGUI();
					a.setVisible(true);
					dispose();
				}
			}
		});
		jContentPane.add(jButtonDeleteUser);
		
		jButtonNotifications = new JButton();
		jButtonNotifications.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Notifications"));
		jButtonNotifications.setBounds(28, 260, 270, 50);
		jButtonNotifications.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SeeNotificationsGUI(passenger);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonNotifications);
		
		jButtonRequest = new JButton();
		jButtonRequest.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Request"));
		jButtonRequest.setBounds(394, 260, 270, 50);
		jButtonRequest.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SeeRequestsGUI(passenger);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonRequest);
		
		//lblNotifications = new JLabel();
		//lblNotifications.setForeground(new Color(255, 0, 0));
		//lblNotifications.setBounds(28, 309, 270, 13);
		//lblNotifications.setText(MainDriverGUI.getBusinessLogic().generateNotification(passenger.getEmail()) +" "+ ResourceBundle.getBundle("Etiquetas").getString("MainGUI.HaveNotifications"));
		//jContentPane.add(lblNotifications);
	
		JOptionPane.showMessageDialog(this, MainDriverGUI.getBusinessLogic().generateNotification(passenger.getEmail()) +" "+ ResourceBundle.getBundle("Etiquetas").getString("MainGUI.HaveNotifications"));
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.LogOut"));
		jButtonSeeRides.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SeeBookings"));
		jButtonTransaction.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Transactions"));
		jButtonAddFunds.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AddFunds"));
		jButtonRequest.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Request"));
		lblNotifications.setText(MainDriverGUI.getBusinessLogic().generateNotification(passenger.getEmail()) +" "+ ResourceBundle.getBundle("Etiquetas").getString("MainGUI.HaveNotifications"));
		jButtonNotifications.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Notifications"));
		jButtonDeleteUser.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.DeleteUser"));
		jButtonTransaction_2.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRequest"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - passenger :"+passenger.getUsername());
	}
} // @jve:decl-index=0:visual-constraint="0,0"

