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
import java.awt.Button;


public class WithdrawMoneyGUI extends JFrame {
	
    private Driver driver;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private JButton jButtonAimar = null;

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption_withdraw_title;
	private JPanel jContentPane_1;
	private JPanel jContentPane_2;
	private JLabel lblNewLabel_bank_withdraw;
	private JTextField textField_bank_withdraw;
	private JPanel jContentPane_3;
	private JLabel lblNewLabel_money_withdraw;
	private JTextField textField_money_withdraw;
	private JPanel jContentPane_4;
	private JLabel lblNewLabel_current_withdraw;
	private JTextArea textArea_curreny_withdraw;
	private JPanel jContentPane_5;
	private JButton jbutton_withdraw;
	private JButton jbutton_exit_withdraw;
	
	/**
	 * This is the default constructor
	 */
	public WithdrawMoneyGUI(Driver d) {
		super();

		driver=d;
		
		this.setSize(new Dimension(700, 500));
		jLabelSelectOption_withdraw_title = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.SelectOption"));
		jLabelSelectOption_withdraw_title.setFont(new Font("Tahoma", Font.BOLD, 13));
		jLabelSelectOption_withdraw_title.setForeground(Color.BLACK);
		jLabelSelectOption_withdraw_title.setHorizontalAlignment(SwingConstants.CENTER);
		
		jContentPane = new JPanel();
		jContentPane.setLayout(new GridLayout(3, 1, 0, 0));
		jContentPane.add(jLabelSelectOption_withdraw_title);
		
		
		setContentPane(jContentPane);
		
		jContentPane_1 = new JPanel();
		jContentPane.add(jContentPane_1);
		jContentPane_1.setLayout(new GridLayout(5, 1, 0, 0));
		
		jContentPane_2 = new JPanel();
		jContentPane_1.add(jContentPane_2);
		jContentPane_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel_bank_withdraw = new JLabel((String) null);
		jContentPane_2.add(lblNewLabel_bank_withdraw);
		
		textField_bank_withdraw = new JTextField();
		textField_bank_withdraw.setText(ResourceBundle.getBundle("Etiquetas").getString("WithdrawMoneyGUI.Bank")); //$NON-NLS-1$ //$NON-NLS-2$
		jContentPane_2.add(textField_bank_withdraw);
		textField_bank_withdraw.setColumns(10);
		
		jContentPane_3 = new JPanel();
		jContentPane_1.add(jContentPane_3);
		jContentPane_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel_money_withdraw = new JLabel((String) null);
		jContentPane_3.add(lblNewLabel_money_withdraw);
		
		textField_money_withdraw = new JTextField();
		textField_money_withdraw.setText((String) null);
		textField_money_withdraw.setColumns(10);
		jContentPane_3.add(textField_money_withdraw);
		
		jContentPane_4 = new JPanel();
		jContentPane_1.add(jContentPane_4);
		jContentPane_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel_current_withdraw = new JLabel((String) null);
		jContentPane_4.add(lblNewLabel_current_withdraw);
		
		textArea_curreny_withdraw = new JTextArea();
		textArea_curreny_withdraw.setEnabled(false);
		textArea_curreny_withdraw.setText(String.valueOf(driver.getMoney()));
		jContentPane_4.add(textArea_curreny_withdraw);
		
		jContentPane_5 = new JPanel();
		jContentPane.add(jContentPane_5);
		jContentPane_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		jbutton_withdraw = new JButton();
		jbutton_withdraw.setText(ResourceBundle.getBundle("Etiquetas").getString("PutMoneyGUI.RemoveMoney"));
		jbutton_withdraw.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					double money = Integer.parseInt(textField_money_withdraw.getText());
					 if (money < 0) {
						 	textField_money_withdraw.setText(ResourceBundle.getBundle("Etiquetas").getString("PutMoneyGUI.Positive"));
			            } else {
			            	MainDriverGUI.getBusinessLogic().removeMoney(driver.getEmail(),money, "Dirua kontutik atera", null);
			            	driver = MainDriverGUI.getDriver();
			            	textArea_curreny_withdraw.setText(String.valueOf(driver.getMoney()));
			            }
				} catch (NumberFormatException ex) {
					textField_money_withdraw.setText(ResourceBundle.getBundle("Etiquetas").getString("PutMoneyGUI.Enter"));
                } 
				
			}
		});
		
		jContentPane_5.add(jbutton_withdraw);
		
		JButton jButtonBack = new JButton();
        jButtonBack.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
        
        jContentPane_5.add(jButtonBack);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.MainTitle") + " - driver :"+driver.getUsername());
		
	}
	
} // @jve:decl-index=0:visual-constraint="0,0"

