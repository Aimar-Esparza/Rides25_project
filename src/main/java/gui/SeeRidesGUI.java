package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.Ride;
import domain.Booking;
import domain.Driver;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class SeeRidesGUI extends JFrame {
    private JTable tableRides;
    private DefaultTableModel tableModelRides;
    private String[] columnNamesRides = { 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.From"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.To"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Date"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Seats"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Car"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Booking")
    };
    private JLabel jLabelEvents;
    private JButton btnReject;
    private Driver driver;

    public SeeRidesGUI(Driver d) {
    	driver=d;
    	
    	
    	
        setTitle(ResourceBundle.getBundle("Etiquetas").getString("SeeRidesGUI.Title"));
        this.setSize(new Dimension(700, 500));
        setLayout(new BorderLayout());

        tableModelRides = new DefaultTableModel(null, columnNamesRides);
        tableRides = new JTable(tableModelRides);
        tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(5));

        add(new JScrollPane(tableRides), BorderLayout.CENTER);
        jLabelEvents = new JLabel();
        jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeRidesGUI.Loading"));
        add(jLabelEvents, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        
        JButton jButtonBack = new JButton();
        jButtonBack.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));
        jButtonBack.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				dispose();
			}
		});
        
        
        
        btnReject = new JButton();
        btnReject.setText(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.Reject"));
        btnReject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(tableRides.getSelectedRow() != -1) {
            		removeRide((Ride) tableModelRides.getValueAt(tableRides.getSelectedRow(), 5)); 
            		refresh();
            	}else {
            		error();
            	}
            }
        });
        
        buttonPanel.add(jButtonBack);
        buttonPanel.add(btnReject);
        add(buttonPanel, BorderLayout.SOUTH);
        
        refresh();
    }

    public void error() {
    	JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.NotSelected"));
    }
    
    public void refresh() {
        try {
            tableModelRides.setRowCount(0);
            BLFacade facade = MainDriverGUI.getBusinessLogic();
            List<Ride> rides = facade.getAllRides(driver);

            if (rides.isEmpty()) {
                jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.NotFound"));//////////////////////////////////////////////////////////////////
            } else {
                jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.Avaliable"));//////////////////////////////////////////////////////////////////
            }
            for (Ride ride : rides) {
        		Vector<Object> row = new Vector<>();
                row.add(ride.getFrom());
                row.add(ride.getTo());
                row.add(ride.getDate());
                row.add(ride.getnPlaces());
                row.add(ride.getCar());
                row.add(ride);
                tableModelRides.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private void removeRide(Ride r) {
        int selectedRow = tableRides.getSelectedRow();
        MainDriverGUI.getBusinessLogic().removeRide(r.getRideNumber());
        if (selectedRow != -1) {
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("SeeRidesGUI.RideDeleted"));
        } else {
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.ChooseOne"));
        }
    }
}
