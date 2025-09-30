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

public class SeeValorations extends JFrame {
    private JTable tableRides;
    private DefaultTableModel tableModelRides;
    private String[] columnNamesRides = { 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeValorationGUI.User"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeValorationGUI.Completed"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeValorationGUI.Rate"),
    		ResourceBundle.getBundle("Etiquetas").getString("SeeValorationGUI.Comment"), 
    		ResourceBundle.getBundle("Etiquetas").getString("SeeValorationGUI.Comment")
    };
    private JLabel jLabelEvents;
    private JButton btnBack;
    private User user;

    public SeeValorations(User us) {
    	user = us;
    	
    	
    	
        setTitle("See Valorations");
        this.setSize(new Dimension(700, 500));
        getContentPane().setLayout(new BorderLayout());

        tableModelRides = new DefaultTableModel(null, columnNamesRides);
        tableRides = new JTable(tableModelRides);
        tableRides.getColumnModel().removeColumn(tableRides.getColumnModel().getColumn(4));

        JScrollPane scrollPane = new JScrollPane(tableRides);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        jLabelEvents = new JLabel();
        jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Explain"));
        getContentPane().add(jLabelEvents, BorderLayout.NORTH);
 
        JPanel buttonPanel = new JPanel();
        btnBack = new JButton();
        btnBack.setText(ResourceBundle.getBundle("Etiquetas").getString("SeeBookingGUI.Back"));
        
        buttonPanel.add(btnBack);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        //getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{scrollPane, tableRides, jLabelEvents, buttonPanel, btnBack, btnReject}));

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        refresh();
    }

    public void refresh() {
        try {
            tableModelRides.setRowCount(0);
            BLFacade facade = MainDriverGUI.getBusinessLogic();
            List<Valoration> val = facade.getValorations(user.getEmail());

            if (val.isEmpty()) {
                //jLabelEvents.setText("No bookings found.");
            } else {
                //jLabelEvents.setText("Bookings available.");
            }
            for (Valoration v : val) {
        		Vector<Object> row = new Vector<>();
        		row.add(user.getUsername());
        		row.add(v.getDone());
        		row.add(v.getValoration());
        		row.add(v.getComment());
        		row.add(v);
        		tableModelRides.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
