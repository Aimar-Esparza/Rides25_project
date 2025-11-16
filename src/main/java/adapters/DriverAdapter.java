package adapters;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Driver;
import domain.Ride;

public class DriverAdapter extends AbstractTableModel {
    private Driver driver;
    private List<Ride> rides; 
    private String[] columnNames = {
        "Origin",
        "Destination",
        "Date",
        "Price",
        "Seats"
    };
    public DriverAdapter(Driver driver) {
        this.driver = driver;
        this.rides = driver.getRides();
        System.out.println("Rides size = " + driver.getRides().size());
    }
    @Override
    public int getRowCount() {
        return rides != null ? rides.size() : 0;
    }
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    @Override
    public Object getValueAt(int row, int col) {
        Ride r = rides.get(row);

        switch (col) {
            case 0: return r.getFrom();
            case 1: return r.getTo();
            case 2: return r.getDate();
            case 3: return r.getPrice();
            case 4: return r.getnPlaces();
            default: return null;
        }
    }
}