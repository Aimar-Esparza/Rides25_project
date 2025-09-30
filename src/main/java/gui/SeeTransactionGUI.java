package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Ride;
import domain.User;
import domain.Booking;
import domain.Driver;
import domain.MoneyTransaction;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class SeeTransactionGUI extends JFrame {
    private JTable tableRides;
    private DefaultTableModel tableModelRides;
    private String[] columnNamesRides = { 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeTransactionGUI.Amount"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeTransactionGUI.Action"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeTransactionGUI.Date"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeTransactionGUI.Reason"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeTransactionGUI.MoneyTransaction")
    };
    private JLabel jLabelEvents;
    private User user;

    public SeeTransactionGUI(User us) {
    	this.user=us;
    	
    	
        setTitle(ResourceBundle.getBundle("Etiquetas").getString("SeeTransactionGUI.Title"));
        this.setSize(new Dimension(700, 500));
        setLayout(new BorderLayout());

        tableModelRides = new DefaultTableModel(null, columnNamesRides);
        tableRides = new JTable(tableModelRides);
        tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(4));

        add(new JScrollPane(tableRides), BorderLayout.CENTER);
        jLabelEvents = new JLabel();
        jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeTransactionGUI.Explain"));
        add(jLabelEvents, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        
        JButton jButtonBack = new JButton();
        jButtonBack.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
        
        buttonPanel.add(jButtonBack);
        add(buttonPanel, BorderLayout.SOUTH);
        
        refresh();
    }

    public void refresh() {
        try {
            tableModelRides.setRowCount(0);
            BLFacade facade = MainDriverGUI.getBusinessLogic();
            List<MoneyTransaction> mt = facade.getTransactions(user);

            if (mt.isEmpty()) {
                jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.NotFound"));//////////////////////////////////////////////////////////////////
            } else {
                jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.Avaliable"));//////////////////////////////////////////////////////////////////
            }
            for (MoneyTransaction m : mt) {
        		Vector<Object> row = new Vector<>();
                row.add(m.getAmount());
                row.add(m.isAction());
                row.add(m.getDate());
                row.add(m.getReason());
                row.add(m);
                tableModelRides.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
