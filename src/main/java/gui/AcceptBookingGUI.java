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
//Proba berria
public class AcceptBookingGUI extends JFrame {
    private JTable tableRides;
    private DefaultTableModel tableModelRides;
    private String[] columnNamesRides = { 
    		ResourceBundle.getBundle("Etiquetas").getString("SingUpGUI.Passenger"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.From"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.To"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Date"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Booking")
    };
    private JLabel jLabelEvents;
    private JButton btnAccept;
    private JButton btnReject;
    private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));
    private Driver driver;

    public AcceptBookingGUI(Driver d) {
    	driver=d;
    	
    	
    	
    	setTitle(ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.MainTitle") + " - driver :"+driver.getUsername());
    	this.setSize(new Dimension(700, 500));
        setLayout(new BorderLayout());

        tableModelRides = new DefaultTableModel(null, columnNamesRides);
        tableRides = new JTable(tableModelRides);
        tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(4));

        add(new JScrollPane(tableRides), BorderLayout.CENTER);
        jLabelEvents = new JLabel("Loading bookings...");
        add(jLabelEvents, BorderLayout.NORTH);
 
        JPanel buttonPanel = new JPanel();
        btnAccept = new JButton();
        btnAccept.setText(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.Accept"));
        btnReject = new JButton();
        btnReject.setText(ResourceBundle.getBundle("Etiquetas").getString("LanguajeGUI.Reject"));

        jButtonClose.setBounds(new Rectangle(6, 419, 130, 30));

		jButtonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			} 
		});
        
		buttonPanel.add(jButtonClose);
        buttonPanel.add(btnAccept);
        buttonPanel.add(btnReject);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAccept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(tableRides.getSelectedRow() != -1) {
            		acceptBooking((Booking) tableModelRides.getValueAt(tableRides.getSelectedRow(), 4));	
                	refresh();
            	}else {
            		error();
            	}
            }
        });

        btnReject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(tableRides.getSelectedRow() != -1) {
	                rejectBooking((Booking) tableModelRides.getValueAt(tableRides.getSelectedRow(), 4)); 
	                refresh();
            	}else {
            		error();
            	}
            }
        });
        
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
                jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.NotFound"));
            } else {
                jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.Avaliable"));
            }
            for (Ride ride : rides) {
                for (Booking booking : ride.getBookings()) {
                	if(!booking.isAccept()) {
                		Vector<Object> row = new Vector<>();
                        row.add(booking.getPassenger().getUsername());
                        row.add(ride.getFrom());
                        row.add(ride.getTo());
                        row.add(ride.getDate());
                        row.add(booking);
                        tableModelRides.addRow(row);
                	}
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private void acceptBooking(Booking b) {
        int selectedRow = tableRides.getSelectedRow();
        MainDriverGUI.getBusinessLogic().acceptBooking(b.getBookingId());
        if (selectedRow != -1) {
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.BookingAccepted"));
        } else {
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.ChooseOne"));
        }
    }

    private void rejectBooking(Booking b) {
        int selectedRow = tableRides.getSelectedRow();
        MainDriverGUI.getBusinessLogic().rejectBooking(b.getBookingId());
        if (selectedRow != -1) {
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.BookingRejected"));
        } else {
            JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("AcceptBookingGUI.ChooseOne"));
        }
    }
}
