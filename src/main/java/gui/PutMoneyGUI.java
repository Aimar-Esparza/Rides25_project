package gui;


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
import java.awt.Button;


public class PutMoneyGUI extends JFrame {
	
    private Passenger passenger;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption_put_title;
	private JPanel jContentPane_1;
	private JPanel jContentPane_2;
	private JLabel lblNewLabel_bank_put;
	private JTextField textField_bank_put;
	private JPanel jContentPane_3;
	private JLabel lblNewLabel_money_put;
	private JTextField textField_money_put;
	private JPanel jContentPane_4;
	private JLabel lblNewLabel_current_put;
	private JTextArea textArea_curreny_put;
	private JPanel jContentPane_5;
	private JButton jbutton_put;
	private JButton jbutton_exit_put;
	
	/**
	 * This is the default constructor
	 */
	public PutMoneyGUI(Passenger p) {
		super();

		passenger = p;
		
		this.setSize(new Dimension(700, 500));
		jLabelSelectOption_put_title = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption_put_title.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption_put_title.setForeground(Color.BLACK);
		jLabelSelectOption_put_title.setHorizontalAlignment(SwingConstants.CENTER);
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(3, 1, 0, 0));
		jContentPane.add(jLabelSelectOption_put_title);
		
		
		setContentPane(jContentPane);
		
		jContentPane_1 = new JPanel();
		jContentPane.add(jContentPane_1);
		jContentPane_1.setLayout(new GridLayout(5, 1, 0, 0));
		
		jContentPane_2 = new JPanel();
		jContentPane_1.add(jContentPane_2);
		jContentPane_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel_bank_put = new JLabel((String) null);
		jContentPane_2.add(lblNewLabel_bank_put);
		
		textField_bank_put = new JTextField();
		textField_bank_put.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.Bank")); //$NON-NLS-1$ //$NON-NLS-2$
		jContentPane_2.add(textField_bank_put);
		textField_bank_put.setColumns(10);
		
		jContentPane_3 = new JPanel();
		jContentPane_1.add(jContentPane_3);
		jContentPane_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel_money_put = new JLabel((String) null);
		jContentPane_3.add(lblNewLabel_money_put);
		
		textField_money_put = new JTextField();
		textField_money_put.setText((String) null);
		textField_money_put.setColumns(10);
		jContentPane_3.add(textField_money_put);
		
		jContentPane_4 = new JPanel();
		jContentPane_1.add(jContentPane_4);
		jContentPane_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel_current_put = new JLabel((String) null);
		jContentPane_4.add(lblNewLabel_current_put);
		
		textArea_curreny_put = new JTextArea();
		textArea_curreny_put.setEnabled(false);
		textArea_curreny_put.setText(String.valueOf(passenger.getMoney()));
		jContentPane_4.add(textArea_curreny_put);
		
		jContentPane_5 = new JPanel();
		jContentPane.add(jContentPane_5);
		jContentPane_5.setLayout(new GridLayout(0, 2, 0, 0));
		 
		jbutton_exit_put = new JButton();
		jbutton_exit_put.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));
		jbutton_exit_put.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
		jContentPane_5.add(jbutton_exit_put);
		
		jbutton_put = new JButton();
		jbutton_put.setText(ResourceBundle.getBundle("Etiquetas").getString("PutMoneyGUI.AddMoney"));
		jbutton_put.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					double money = Integer.parseInt(textField_money_put.getText());
					if (money < 0) {
						textField_money_put.setText(ResourceBundle.getBundle("Etiquetas").getString("PutMoneyGUI.Positive"));
			        } else {
			            MainDriverGUI.getBusinessLogic().addMoney(passenger.getEmail(),money, "Dirua kontura sartu", null);
			            
			            passenger = (Passenger) MainDriverGUI.getBusinessLogic().login(passenger.getEmail(), passenger.getPassword());
			            textArea_curreny_put.setText(String.valueOf(passenger.getMoney()));
			        }
				} catch (NumberFormatException ex) {
					textField_money_put.setText(ResourceBundle.getBundle("Etiquetas").getString("PutMoneyGUI.Enter"));
                }
				
			}
		});
		
		jContentPane_5.add(jbutton_put);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") + " - driver :"+passenger.getUsername());
		
	} 
	
	private void paintAgain() {
		jLabelSelectOption_put_title.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jbutton_exit_put.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.QueryRides"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.CreateRide"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle")+ " - driver :"+passenger.getUsername());
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"

