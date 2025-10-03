package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Admin;
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


public class MainAdminGUI extends JFrame {
	
    private static Admin admin;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonReclamation;
	private JButton jButtonLogOut = null;
	
	private JLabel lblNotifications = null;
	
	public static Admin getAdmin() {
		return admin;
	}
	
	public static void setAdmin(Admin ad) {
		admin = ad;
	}
	
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton1;
	private JRadioButton rdbtnNewRadioButton2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonTransaction2;
	
	/**
	 * This is the default constructor
	 */
	public MainAdminGUI(Admin ad) {
		super();

		admin=ad;
		
		// this.setSize(271, 295);
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - admin :"+admin.getUsername());
		
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
		
		rdbtnNewRadioButton1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		rdbtnNewRadioButton1.setLocation(128, 400);
		rdbtnNewRadioButton1.setSize(160, 40);
		rdbtnNewRadioButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				}
		});
		buttonGroup.add(rdbtnNewRadioButton1);
		
		rdbtnNewRadioButton2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton2.setFont(new Font("Tahoma", Font.PLAIN, 17));
		rdbtnNewRadioButton2.setLocation(288, 400);
		rdbtnNewRadioButton2.setSize(170, 40);
		rdbtnNewRadioButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton2);
		
		jButtonReclamation = new JButton();
		jButtonReclamation.setBounds(210, 121, 270, 94);
		jButtonReclamation.setText(ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.Reclamations"));
		jButtonReclamation.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				if(MainDriverGUI.getBusinessLogic().getNextReclamation() != null) {
					JFrame a = new ReclamationAdminGUI(MainDriverGUI.getBusinessLogic().getNextReclamation());
					a.setVisible(true);
				}
			}
		});
		
		jButtonLogOut = new JButton();
		jButtonLogOut.setBounds(210, 308, 270, 50);
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
		jContentPane.add(jButtonReclamation);
		jContentPane.add(jButtonLogOut);
		jContentPane.add(rdbtnNewRadioButton);
		jContentPane.add(rdbtnNewRadioButton1);
		jContentPane.add(rdbtnNewRadioButton2);
		
		setContentPane(jContentPane);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	private void paintAgain() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jButtonReclamation.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonLogOut.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.LogOut"));
		lblNotifications.setText(MainDriverGUI.getBusinessLogic().generateNotification(admin.getEmail()) +" "+ ResourceBundle.getBundle("Etiquetas").getString("MainGUI.HaveNotifications"));
		jButtonTransaction2.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRequest"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - passenger :"+admin.getUsername());
	}
} // @jve:decl-index=0:visual-constraint="0,0"

