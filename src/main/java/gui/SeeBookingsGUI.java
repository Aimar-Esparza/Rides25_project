package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import businessLogic.BLFacade;
import domain.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

public class SeeBookingsGUI extends JFrame {
    private JTable tableRides;
    private DefaultTableModel tableModelRides;
    private String[] columnNamesRides = { 
    		ResourceBundle.getBundle("Etiquetas").getString("SingUpGUI.Passenger"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.From"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.To"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Date"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Status"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Booking")
    };
    private JLabel jLabelEvents;
    private JButton btnBack;
    private JButton btnComplete;
    private JButton bntDelete;
    private User user;

    public SeeBookingsGUI(User us) {
    	user=us;
    	
    	
    	
        setTitle("Accept Bookings");
        this.setSize(new Dimension(700, 500));
        getContentPane().setLayout(new BorderLayout());

        tableModelRides = new DefaultTableModel(null, columnNamesRides);
        tableRides = new JTable(tableModelRides);
        tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(5));

        JScrollPane scrollPane = new JScrollPane(tableRides);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        jLabelEvents = new JLabel();
        jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Explain"));
        getContentPane().add(jLabelEvents, BorderLayout.NORTH);
 
        JPanel buttonPanel = new JPanel();
        btnBack = new JButton();
        btnBack.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));
        btnComplete = new JButton();
        btnComplete.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Complete"));
        bntDelete = new JButton();
        bntDelete.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Delete"));
        bntDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(tableRides.getSelectedRow() != -1) {
            		MainDriverGUI.getBusinessLogic().rejectBooking(((Booking) tableModelRides.getValueAt(tableRides.getSelectedRow(), 5)).getBookingId());
            	}
            	refresh();
            }
        });    

        buttonPanel.add(btnBack);
        buttonPanel.add(bntDelete);
        buttonPanel.add(btnComplete);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        //getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{scrollPane, tableRides, jLabelEvents, buttonPanel, btnBack, btnReject}));

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btnComplete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(tableRides.getSelectedRow() != -1) {
            		Booking b = (Booking) tableModelRides.getValueAt(tableRides.getSelectedRow(), 5);
            		if(b.isAccept() && b.getRide().getDate().before(new Date())) {
            			JFrame a = new ValorationGUI(b, user);
            			a.setVisible(true);
            			dispose();
            		}
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
            List<Booking> books = facade.getBookings(user.getEmail());

            if (books.isEmpty()) {
                //jLabelEvents.setText("No bookings found.");
            } else {
                //jLabelEvents.setText("Bookings available.");
            }
            for (Booking book : books) {
            	if((user instanceof Passenger && !book.isPassengerDone()) || (user instanceof Driver && !book.isDriverDone())) {
            		Vector<Object> row = new Vector<>();
            		row.add(book.getPassenger().getUsername());
            		row.add(book.getRide().getFrom());
            		row.add(book.getRide().getTo());
            		row.add(book.getRide().getDate());
            		row.add(book.isAccept());
            		row.add(book);
            		tableModelRides.addRow(row);
            	}
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    //public void finishBook(Booking b) {
    	//JFrame a = new ValorationGUI(b);
		//a.setVisible(true);
    	//if(b.isAccept() & b.getRide().getDate().before(new Date())) {
    		//BLFacade facade = MainDriverGUI.getBusinessLogic();
        	//facade.completeBooking(b.getBookingId(), ok);
        	//JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.BookingFinish"));
    	//}else {
    		//JOptionPane.showMessageDialog(this, ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.BookingNotFinish"));
    	//}
    //}
    
    private void deleteBooking(Booking b) {
        MainDriverGUI.getBusinessLogic().rejectBooking(b.getBookingId());
    }
}
