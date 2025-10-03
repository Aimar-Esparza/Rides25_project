package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class LanguageGUI  extends JFrame{
	
	private JPanel jContentPane = null;
	
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnNewButton_1;
	private JLabel lblNewLabel;
	private int where;
	
	public LanguageGUI(int where) {
		super(); 
		this.where = where;
		Locale.setDefault(new Locale("en"));
		this.setSize(new Dimension(700, 500));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.MainTitle"));
		jContentPane = new JPanel();
		
		rdbtnNewRadioButton = new JRadioButton("English");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtnNewRadioButton.setLocation(496, 165);
		rdbtnNewRadioButton.setSize(148, 97);
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("en"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtnNewRadioButton_1.setLocation(29, 153);
		rdbtnNewRadioButton_1.setSize(162, 120);
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Locale.setDefault(new Locale("eus"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();				
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
		rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.BOLD, 25));
		rdbtnNewRadioButton_2.setLocation(259, 165);
		rdbtnNewRadioButton_2.setSize(162, 97);
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Locale.setDefault(new Locale("es"));
				System.out.println("Locale: "+Locale.getDefault());
				paintAgain();
			}
		});
		buttonGroup.add(rdbtnNewRadioButton_2);
		
		
		lblNewLabel = new JLabel();
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.Choose"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblNewLabel.setBounds(0, 30, 686, 43);
		jContentPane.add(lblNewLabel);
		
		btnNewButton_1 = new JButton();
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton_1.setText(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.Accept"));
		btnNewButton_1.setBounds(259, 314, 162, 89);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(where == 0) {
					JFrame a = new FindRidesGUI(0);
					a.setVisible(true);
					dispose();
				}else if (where == 1){
					dispose();
				}
			}
		});
		jContentPane.add(btnNewButton_1);
		
		
		
		
		jContentPane.setLayout(null);
		jContentPane.add(rdbtnNewRadioButton);
		jContentPane.add(rdbtnNewRadioButton_1);
		jContentPane.add(rdbtnNewRadioButton_2);
		
		setContentPane(jContentPane);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(1);
			}
		});
	}
	private void paintAgain() {
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.MainTitle"));
		btnNewButton_1.setText(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.Accept"));
		lblNewLabel.setText(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.Choose"));
	}
}
