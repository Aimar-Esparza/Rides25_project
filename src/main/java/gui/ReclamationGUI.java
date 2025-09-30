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


public class ReclamationGUI extends JFrame {
	protected JLabel jLabelSelectOption;
	private User us;
	DefaultComboBoxModel<String> val = new DefaultComboBoxModel<String>();
	private static BLFacade appFacadeInterface;
	private final ButtonGroup buttonGroup = new ButtonGroup();
		
	public ReclamationGUI(Booking booking) {
		this.setSize(new Dimension(700, 500));
		
		getContentPane().setForeground(new Color(255, 0, 128));
		getContentPane().setLayout(null);
		
		JLabel lblTittle = new JLabel();lblTittle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTittle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTittle.setText(ResourceBundle.getBundle("Etiquetas").getString("ReclamationAdminGUI.Reclamation"));
		lblTittle.setBounds(0, 0, 686, 43);
		getContentPane().add(lblTittle);
		
		JLabel lblCommentary = new JLabel();
		lblCommentary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblCommentary.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Commentary"));
		lblCommentary.setBounds(32, 90, 120, 30);
		getContentPane().add(lblCommentary);
		
		JTextArea textAreaComment = new JTextArea();
		textAreaComment.setBounds(174, 95, 342, 245);
		getContentPane().add(textAreaComment);
		
		JButton btnCancel = new JButton();
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
		
		JButton btnSubmmit = new JButton();
		btnSubmmit.setText(ResourceBundle.getBundle("Etiquetas").getString("ValorationGUI.Submit"));
		btnSubmmit.setBounds(534, 397, 142, 40);
		getContentPane().add(btnSubmmit);
		btnSubmmit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				MainDriverGUI.getBusinessLogic().createReclamation(booking.getBookingId(), textAreaComment.getText());
				JFrame a = new SeeBookingsGUI(MainPassengerGUI.getPassenger());
				a.setVisible(true);
				dispose();
			}
		});
		int aukera = JOptionPane.showConfirmDialog(this,
				ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.Reclamation"), 
				ResourceBundle.getBundle("Etiquetas").getString("ReclamationGUI.WantReclamataion"), 	
				JOptionPane.YES_NO_OPTION);
		setVisible(true);
		if(aukera != JOptionPane.YES_OPTION) {
			dispose();
			setVisible(false);
		}
	}
}
