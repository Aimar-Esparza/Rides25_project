package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Driver;
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


public class MainDriverGUI extends JFrame {
	
    private static Driver driver;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonLogOut = null;
	private JButton jButtonProfile = null;
	private JButton jButtonWithdrawEarnings = null;
	private JButton jButtonAcceptRides = null;
	private JButton jButtonSeeRides = null;
	private JButton jButtonCreateCar = null;
	private JButton jButtonTransactions = null;
	private JButton jButtonDeleteUser = null;

    private static BLFacade appFacadeInterface;
	
    public static Driver getDriver() {
    	return driver;
    }
    
    public static void setDriver(Driver d) {
    	driver = d;
    }
    
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	
	/**
	 * This is the default constructor
	 */
	public MainDriverGUI(Driver d) {
		super();

		setDriver(d);
		
		// this.setSize(271, 295);
		this.setSize(new Dimension(700, 500));
		jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption.setBounds(116, 0, 481, 50);
		jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption.setForeground(Color.BLACK);
		jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		
		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
		rdbtnNewRadioButton.setLocation(280, 417);
		rdbtnNewRadioButton.setSize(107, 40);
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
		rdbtnNewRadioButton_1.setLocation(28, 417);
		rdbtnNewRadioButton_1.setSize(119, 40);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		rdbtnNewRadioButton_2.setLocation(149, 417);
		rdbtnNewRadioButton_2.setSize(129, 40);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);
		
		
		
		jButtonCreateQuery = new JButton();
		jButtonCreateQuery.setBounds(28, 45, 270, 50);
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateRideGUI(driver);
				a.setVisible(true);
			}
		});
		
		jButtonQueryQueries = new JButton();
		jButtonQueryQueries.setBounds(394, 45, 270, 50);
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new FindRidesGUI(1);
				a.setVisible(true);
			}
		});
		
		jButtonLogOut = new JButton();
		jButtonLogOut.setBounds(394, 339, 270, 50);
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
		jContentPane.add(jButtonCreateQuery);
		jContentPane.add(jButtonQueryQueries);
		jContentPane.add(jButtonLogOut);
		jContentPane.add(rdbtnNewRadioButton);
		jContentPane.add(rdbtnNewRadioButton_1);
		jContentPane.add(rdbtnNewRadioButton_2);
		
		jButtonAcceptRides = new JButton(); 
		jButtonAcceptRides.setBounds(394, 119, 270, 50);
		jButtonAcceptRides.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AcceptRides"));
		jButtonAcceptRides.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new AcceptBookingGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonAcceptRides);
		
		jButtonWithdrawEarnings = new JButton();
		jButtonWithdrawEarnings.setBounds(28, 268, 270, 50);
		jButtonWithdrawEarnings.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.WithdrawEarnings"));
		jButtonWithdrawEarnings.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new WithdrawMoneyGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonWithdrawEarnings);
		
		
		jButtonSeeRides = new JButton();
		jButtonSeeRides.setBounds(28, 119, 270, 50);
		jButtonSeeRides.setText("See Rides");
		jButtonSeeRides.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SeeRidesGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonSeeRides);
		
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") + " - driver :"+driver.getUsername());
		
		
		setContentPane(jContentPane);
		
		jButtonTransactions = new JButton();
		jButtonTransactions.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Transactions"));
		jButtonTransactions.setBounds(28, 196, 270, 50);
		jButtonTransactions.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SeeTransactionGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonTransactions);
		
		jButtonCreateCar = new JButton();
		jButtonCreateCar.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateCar"));
		jButtonCreateCar.setBounds(394, 196, 270, 50);
		jButtonCreateCar.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new CreateCarGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonCreateCar);
		
		JButton jButtonSeeBookings = new JButton();
		jButtonSeeBookings.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SeeBookings"));
		jButtonSeeBookings.setBounds(394, 268, 270, 50);
		jButtonSeeBookings.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SeeBookingsGUI(driver);
				a.setVisible(true);
			}
		});
		jContentPane.add(jButtonSeeBookings);
		
		jButtonDeleteUser = new JButton();
		jButtonDeleteUser.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.DeleteUser"));
		jButtonDeleteUser.setBounds(28, 339, 270, 50);
		jButtonDeleteUser.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int aukera = JOptionPane.showConfirmDialog(jButtonDeleteUser,
						ResourceBundle.getBundle("Etiquetas").getString("MainGUI.DeleteUser"), 
						ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AreYouSure"), 	
						JOptionPane.YES_NO_OPTION);
				if(aukera == JOptionPane.YES_OPTION) {
					getBusinessLogic().deleteUser(driver.getEmail());
				}
				JFrame a = new LoginGUI();
				a.setVisible(true);			
				dispose();
			}
		});
		jContentPane.add(jButtonDeleteUser);
		
		
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
		jButtonCreateCar.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateCar"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		jButtonWithdrawEarnings.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.WithdrawEarnings"));
		jButtonAcceptRides.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.AcceptRides"));
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.LogOut"));
		jButtonSeeRides.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SeeBookings"));
		jButtonTransactions.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.Transactions"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - driver :"+driver.getUsername());
	}
} // @jve:decl-index=0:visual-constraint="0,0"

