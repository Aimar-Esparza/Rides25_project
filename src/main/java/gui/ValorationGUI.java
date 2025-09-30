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


public class ValorationGUI extends JFrame {
	protected JLabel jLabelSelectOption;
	private User us;
	DefaultComboBoxModel<String> val = new DefaultComboBoxModel<String>();
	private static BLFacade appFacadeInterface;
	private final ButtonGroup buttonGroup = new ButtonGroup();

		
	public ValorationGUI(Booking booking, User us) {
		User user = us;
		this.setSize(new Dimension(700, 500));
		
		getContentPane().setForeground(new Color(255, 0, 128));
		getContentPane().setLayout(null);
		
		JLabel lblValoration = new JLabel();
		lblValoration.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblValoration.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.rate"));
		lblValoration.setBounds(27, 152, 120, 30);
		getContentPane().add(lblValoration);
		
		JLabel lblTittle = new JLabel();lblTittle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTittle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTittle.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Valoration"));
		lblTittle.setBounds(0, 0, 686, 43);
		getContentPane().add(lblTittle);
		
		JLabel lblCommentary = new JLabel();
		lblCommentary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCommentary.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Commentary"));
		lblCommentary.setBounds(27, 209, 120, 30);
		getContentPane().add(lblCommentary);
		
		JComboBox comboBoxValoration = new JComboBox();
		comboBoxValoration.setModel(val);
		for(int i = 0; i <= 10; i++) {
			val.addElement(Integer.toString(i));
		}
		comboBoxValoration.setSelectedIndex(-1);
		comboBoxValoration.setBounds(178, 159, 50, 21);
		getContentPane().add(comboBoxValoration);
		
		JTextArea textAreaComment = new JTextArea();
		textAreaComment.setBounds(179, 214, 342, 88);
		getContentPane().add(textAreaComment);
		
		JLabel lblDone = new JLabel();
		lblDone.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Done"));
		lblDone.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDone.setBounds(27, 86, 120, 30);
		getContentPane().add(lblDone);
		
		JRadioButton rdbtnYES = new JRadioButton();
		buttonGroup.add(rdbtnYES);
		rdbtnYES.setBounds(207, 86, 120, 30);
		rdbtnYES.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Yes"));
		getContentPane().add(rdbtnYES);
		
		JRadioButton rdbtnNO = new JRadioButton();
		buttonGroup.add(rdbtnNO);
		rdbtnNO.setBounds(354, 86, 120, 30);
		rdbtnNO.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.No"));
		getContentPane().add(rdbtnNO);
		
		JButton btnCancel = new JButton("SING UP");
		btnCancel.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Cancel"));
		btnCancel.setBounds(10, 397, 142, 40);
		getContentPane().add(btnCancel);
		btnCancel.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFrame a = new SeeBookingsGUI(MainPassengerGUI.getPassenger());
				a.setVisible(true);
				dispose();
			} 
		});
		
		JButton btnSubmmit = new JButton("QUERY RIDES");
		btnSubmmit.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Submit"));
		btnSubmmit.setBounds(534, 397, 142, 40);
		getContentPane().add(btnSubmmit);
		btnSubmmit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				String com = textAreaComment.getText();
				boolean error = false;
				if(com == null) {
					com = "";
				}
				if(rdbtnNO.isSelected()) {
					MainDriverGUI.getBusinessLogic().addValoration(booking.getBookingId(), false, 0, com, user.getEmail());
				}else if (rdbtnYES.isSelected()){
					if(comboBoxValoration.getSelectedIndex() != -1) {
						MainDriverGUI.getBusinessLogic().addValoration(booking.getBookingId(), true, Integer.parseInt((String)comboBoxValoration.getSelectedItem()), com, user.getEmail());
					}else {
						//Error, por favor selecciona una valoración
						JOptionPane.showMessageDialog(btnSubmmit, ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.ErrorVal"));
						error = true;
					}
				}else {
					//Error, por favor marca si has podido completar el viaje o no
					JOptionPane.showMessageDialog(btnSubmmit, ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.ErrorDone"));
					error = true;
				}
				if(!error ) {
					if(user instanceof Passenger) {
						JFrame a = new SeeBookingsGUI(MainPassengerGUI.getPassenger());
						a.setVisible(true);
						if(rdbtnNO.isSelected()) {
							JFrame b = new ReclamationGUI(booking);
							//b.setVisible(true);
						}
					}else {
						JFrame a = new SeeBookingsGUI(MainDriverGUI.getDriver());
						a.setVisible(true);
					}
					dispose();
					
				}
			}
		});
		
	}
}
