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

public class SeeRequestsGUI extends JFrame {
    private JTable tableRides;
    private DefaultTableModel tableModelRides;
    private String[] columnNamesRides = { 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeRequest.Origin"), 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeRequest.Destination"), 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeRequest.Kant"), 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeRequest.Valoration"), 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeRequest.Date"), 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeRequest.AutoBuy"), 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeValorationGUI.Comment")
    };
    private JLabel jLabelEvents;
    private JButton btnBack;
    private JButton btnDelete;
    private Passenger passenger;

    public SeeRequestsGUI(Passenger p) {
    	passenger = p;
    	
    	
    	
        setTitle("See Request");
        this.setSize(new Dimension(700, 500));
        getContentPane().setLayout(new BorderLayout());

        tableModelRides = new DefaultTableModel(null, columnNamesRides);
        tableRides = new JTable(tableModelRides);
        tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(6));

        JScrollPane scrollPane = new JScrollPane(tableRides);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        jLabelEvents = new JLabel();
        getContentPane().add(jLabelEvents, BorderLayout.NORTH);
 
        JPanel buttonPanel = new JPanel();
        btnBack = new JButton();
        btnBack.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));
        
        btnDelete = new JButton();
        btnDelete.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Delete"));
        
        buttonPanel.add(btnBack);
        buttonPanel.add(btnDelete);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        //getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{scrollPane, tableRides, jLabelEvents, buttonPanel, btnBack, btnReject}));

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if(tableRides.getSelectedRow() != -1) {
            		MainDriverGUI.getBusinessLogic().deleteRequest(((Request) tableModelRides.getValueAt(tableRides.getSelectedRow(), 6)).getRequestId());
            	}else {
            		error();
            	}
            	refresh();
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
            List<Request> val = facade.getRequest(passenger.getEmail());

            if (val.isEmpty()) {
                //jLabelEvents.setText("No bookings found.");
            } else {
                //jLabelEvents.setText("Bookings available.");
            }
            for (Request v : val) {
        		Vector<Object> row = new Vector<>();
        		row.add(v.getOrigin());
        		row.add(v.getDestination());
        		row.add(v.getKant());
        		row.add(v.getValoration());
        		row.add(v.getRideDate());
        		row.add(v.isAutoBuy());
        		row.add(v);
        		tableModelRides.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
